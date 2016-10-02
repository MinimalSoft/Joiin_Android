package com.MinimalSoft.BrujulaUniversitaria.Facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.MinimalSoft.BrujulaUniversitaria.Models.UserResponse;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Start.LoginActivity;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacebookDataCollector implements GraphRequest.GraphJSONObjectCallback, FacebookCallback<LoginResult>, Callback<UserResponse> {
    private final String FACEBOOK_API_FIELDS;
    private final String API_URL;

    private LoginActivity loginActivity;
    private String facebookToken;
    private String idFacebook;
    private String email;
    private String name;

    public FacebookDataCollector(LoginActivity loginActivity) {
        FACEBOOK_API_FIELDS = "name,first_name,last_name,email,picture.type(square)";
        API_URL = loginActivity.getResources().getString(R.string.server_api);
        this.loginActivity = loginActivity;
    }

    /* FacebookCallback methods */

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
        Bundle parameters = new Bundle();

        idFacebook = loginResult.getAccessToken().getUserId();
        facebookToken = loginResult.getAccessToken().getToken();

        parameters.putString("fields", FACEBOOK_API_FIELDS);
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onError(FacebookException error) {
        loginActivity.displayError("Login error", error.getMessage());
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onCancel() {
        LoginManager.getInstance().logOut();
    }

    /* GraphJSONObjectCallback methods */

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        if (response.getError() == null) {
            JSONObject json = response.getJSONObject();

            try {
                name = json.getString("name");
                email = json.getString("email");
                String lastName = json.getString("last_name");
                String firstName = json.getString("first_name");
                String url = json.getJSONObject("picture").getJSONObject("data").getString("url");

                Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
                Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
                minimalSoftAPI.registerUser("register", firstName, lastName, "", "", "", email, "", url, idFacebook, "").enqueue(this);
            } catch (JSONException exc) {
                loginActivity.displayError("JSON error", exc.getMessage());
                LoginManager.getInstance().logOut();
            }
        } else {
            loginActivity.displayError("Error", response.getError().getErrorMessage());
            LoginManager.getInstance().logOut();
        }
    }

    /*----Callback Methods----*/

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        if (response.code() == 404) {
            LoginManager.getInstance().logOut();
            loginActivity.displayError("Server error", "Error al conectar con el servidor");
        } else {
            SharedPreferences.Editor preferencesEditor = loginActivity.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
            preferencesEditor.putInt("USER_ID", response.body().getData().getIdUser());
            //preferencesEditor.putString("FACEBOOK_TOKEN", facebookToken);
            preferencesEditor.putString("FACEBOOK_ID", idFacebook);
            preferencesEditor.putString("USER_EMAIL", email);
            preferencesEditor.putString("USER_NAME", name);
            preferencesEditor.putBoolean("LOGGED_IN", true);
            preferencesEditor.apply();
            loginActivity.logIn();
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        loginActivity.displayError("Failure", t.getMessage());
        LoginManager.getInstance().logOut();
    }
}