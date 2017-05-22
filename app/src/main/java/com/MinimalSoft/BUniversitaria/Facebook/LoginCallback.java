package com.MinimalSoft.BUniversitaria.Facebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Interfaces.MinimalSoftServices;
import com.MinimalSoft.BUniversitaria.Main.MainActivity;
import com.MinimalSoft.BUniversitaria.Responses.UserResponse;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginCallback implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback, Callback<UserResponse> {
    private ProgressDialog progressDialog;
    private FacebookData facebookData;
    private final Activity activity;

    public LoginCallback(Activity activity) {
        this.activity = activity;
    }

    /*----------------------------- FacebookCallback Methods -----------------------------*/
    @Override
    public void onSuccess(LoginResult loginResult) {
        String facebookApiFields = "name,first_name,last_name,gender";

        if (loginResult.getRecentlyGrantedPermissions().contains("user_birthday")) {
            facebookApiFields += ",birthday";
        }

        if (loginResult.getRecentlyGrantedPermissions().contains("email")) {
            facebookApiFields += ",email";
        }

        Bundle parameters = new Bundle();
        parameters.putString("fields", facebookApiFields);

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
        //LoginManager.getInstance().logOut();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();
        error.printStackTrace();
    }

    /*---------------- GraphJSONObjectCallback Methods ----------------*/

    /**
     * The method that will be called when the request completes.
     *
     * @param object   the GraphObject representing the returned object, or null
     * @param response the Response of this request, which may include error information if the
     */
    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        if (response.getError() == null) {
            progressDialog = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Autenticando con BU...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            Type type = new TypeToken<FacebookData>() {
            }.getType();
            facebookData = new Gson().fromJson(object.toString(), type);

            String picture = "http://graph.facebook.com/" + facebookData.getId() + "/picture";
            String email = (facebookData.getEmail() != null) ? (facebookData.getEmail()) : "";
            String birthday = (facebookData.getBirthday() != null) ? (facebookData.getBirthday()) : "";
            String gender = (facebookData.getGender() != null) ? (facebookData.getGender().substring(0, 1)) : "";

            Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
            api.registerUser("register", facebookData.getFirstName(), facebookData.getLastName(),
                    gender.toUpperCase(), birthday, "", email, "", picture, facebookData.getId(), "").enqueue(this);
        } else {
            Toast.makeText(activity, response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
        }
    }

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        progressDialog.dismiss();

        if (response.isSuccessful()) {
            SharedPreferences.Editor preferencesEditor = activity.getSharedPreferences(BU.PREFERENCES, Context.MODE_PRIVATE).edit();
            preferencesEditor.putString(BU.USER_EMAIL, facebookData.getEmail() != null ? facebookData.getEmail() : "");
            preferencesEditor.putInt(BU.USER_ID, response.body().getData().getIdUser());
            preferencesEditor.putString(BU.USER_NAME, facebookData.getFullName());
            //preferencesEditor.putString("FACEBOOK_ID", facebookData.getId());
            preferencesEditor.apply();

            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } else {
            Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
        }
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        progressDialog.dismiss();
        LoginManager.getInstance().logOut();
        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
}