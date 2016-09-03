package com.MinimalSoft.BrujulaUniversitaria.Facebook;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;

import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;
import com.MinimalSoft.BrujulaUniversitaria.Models.Response_Start;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.MinimalSoft.BrujulaUniversitaria.Start.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacebookDataCollector implements GraphRequest.GraphJSONObjectCallback, FacebookCallback<LoginResult>, Runnable, Callback<Response_Start> {
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
        logActivity.logIn();
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

        //parameters.putString("fields", "name,email");
        parameters.putString("fields", "id,name,first_name,last_name,gender,birthday,email,picture.type(square)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
        Log.i(this.getClass().getSimpleName(), graphRequest.toString());
    }

    /* GraphJSONObjectCallback implemented methods */

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        JSONObject json = response.getJSONObject();

        try {
            Log.i(this.getClass().getSimpleName(), json.toString());

            String firstName = json.getString("first_name");
            String lastName = json.getString("last_name");
            String gender = json.getString("gender");
            String email = json.getString("email");
            String name = json.getString("name");
            String id = json.getString("id");
            String url = json.getJSONObject("picture").getJSONObject("data").getString("url");

            editor.putBoolean("USER_PICS", false);
            editor.putString("USER_EMAIL", email);
            editor.putString("USER_NAME", name);
            editor.commit();

            Log.i(this.getClass().getSimpleName(), "URL: " + url);
            String BASE_URL = "http://ec2-52-38-75-156.us-west-2.compute.amazonaws.com";
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            Interfaces interfaces = retrofit.create(Interfaces.class);
            //Call<Response_Start> call = interfaces.registerUser("register", firstName, lastName, String.valueOf(gender.charAt(0)).toUpperCase(), "", "", email, "", url, id, "");
            //call.enqueue(this);

        } catch (NullPointerException exc) {
            exc.printStackTrace();
        } catch (JSONException exc) {
            exc.printStackTrace();
        }
    }

    /*----Retrofit Methods----*/

    @Override
    public void onResponse(Call<Response_Start> call, Response<Response_Start> response) {
        Log.i(this.getClass().getSimpleName(), "Message: " + response.body().getMessage());
    }

    @Override
    public void onFailure(Call<Response_Start> call, Throwable t) {
        Log.e(this.getClass().getSimpleName(), "Message: " + t.getMessage());
        t.printStackTrace();
    }
}