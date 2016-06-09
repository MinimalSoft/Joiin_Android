package com.MinimalSoft.BrujulaUniversitaria.Facebook;

import android.os.Bundle;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import com.MinimalSoft.BrujulaUniversitaria.Start.LoginActivity;

public class FacebookDataCollector implements GraphRequest.GraphJSONObjectCallback, FacebookCallback<LoginResult>, Runnable {
    private SharedPreferences.Editor editor;
    private SharedPreferences settings;
    private LoginActivity logActivity;

    public FacebookDataCollector(final LoginActivity login) {
        this.logActivity = login;
    }

    /* FacebookCallback implemented methods */

    @Override
    public void onSuccess(LoginResult loginResult) {
        settings = logActivity.getSharedPreferences("FACEBOOK_PREF", Context.MODE_PRIVATE);
        editor = settings.edit();

        Thread requestThread = new Thread(this);

        editor.putString("FACEBOOK_ID", loginResult.getAccessToken().getUserId());
        editor.putString("FACEBOOK_TOKEN", loginResult.getAccessToken().getToken());
        editor.commit();

        requestThread.start();
        logActivity.logInWithFacebook();
    }

    @Override
    public void onError(FacebookException error) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(logActivity);
        alertDialog.setPositiveButton("Aceptar", null);
        alertDialog.setMessage(error.getMessage());
        alertDialog.setTitle("¡Ups!");
        alertDialog.show();
    }

    @Override
    public void onCancel() {

    }

    /* Runnable implemented methods */
    @Override
    public void run() {
        Bundle parameters = new Bundle();
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);

        parameters.putString("fields", "name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    /* GraphJSONObjectCallback implemented methods */

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        JSONObject json = response.getJSONObject();

        try {
            String name = json.getString("name");
            String email = json.getString("email");

            editor.putString("USER_EMAIL", email);
            editor.putString("USER_NAME", name);
            editor.commit();
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        } catch (JSONException exc) {
            exc.printStackTrace();
        }
    }
}