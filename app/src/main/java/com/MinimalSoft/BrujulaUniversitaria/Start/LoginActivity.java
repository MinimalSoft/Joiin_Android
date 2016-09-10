package com.MinimalSoft.BrujulaUniversitaria.Start;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;
import com.MinimalSoft.BrujulaUniversitaria.Models.UserResponse;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BrujulaUniversitaria.Facebook.FacebookDataCollector;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements View.OnClickListener, Callback<UserResponse> {
    private CallbackManager facebookCallbackManager;
    private LoginButton facebookLoginButton;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private EditText passwordField;
    private EditText emailField;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        Button fakeFacebookButton = (Button) findViewById(R.id.login_fakeFacebookButton);
        Button registerButton = (Button) findViewById(R.id.login_registerButton);
        Button loginButton = (Button) findViewById(R.id.login_accessButton);

        emailField = (EditText) findViewById(R.id.login_emailField);
        passwordField = (EditText) findViewById(R.id.login_passwordField);
        facebookLoginButton = (LoginButton) findViewById(R.id.login_facebookButton);
        facebookCallbackManager = CallbackManager.Factory.create();

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        FacebookDataCollector dataCollector = new FacebookDataCollector(this);

        facebookLoginButton.registerCallback(facebookCallbackManager, dataCollector);
        facebookLoginButton.setReadPermissions("public_profile email");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(true);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setPositiveButton("OK", null);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        fakeFacebookButton.setOnClickListener(this);
    }

    /*----OnClickListener methods----*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_fakeFacebookButton:
                facebookLoginButton.performClick();
                break;

            case R.id.login_registerButton:
                Intent intent = new Intent(this.getApplicationContext(), RegisterActivity.class);
                this.startActivity(intent);
                break;

            case R.id.login_accessButton:
                loginRequest();
                break;
        }
    }

    /*----Callback methods----*/

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        progressDialog.hide();

        if (response.code() == 404) {
            Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
        } else if (response.body().getResponse().equals("alert")) {
            Toast.makeText(this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences.Editor preferencesEditor = getSharedPreferences("FACEBOOK_PREF", Context.MODE_PRIVATE).edit();
            String fullName = response.body().getData().getName() + ' ' + response.body().getData().getLastName();

            preferencesEditor.putString("USER_NAME", fullName);
            preferencesEditor.putString("USER_EMAIL", email);
            preferencesEditor.putBoolean("USER_PICS", false);
            preferencesEditor.apply();
            logIn();
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        progressDialog.hide();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            loginRequest();
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginRequest() {
        email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();

        if (email.length() == 0) {
            Toast.makeText(this, "Inserte el correo", Toast.LENGTH_LONG).show();
        } else if (password.length() == 0) {
            Toast.makeText(this, "Inserte la contraseña", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            String urlAPI = getResources().getString(R.string.server_api);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
            Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
            minimalSoftAPI.logInUser("login", email, password, "", "").enqueue(this);
        }
    }

    public void displayError(String src, String msg) {
        progressDialog.hide();

        alertDialog.setMessage(msg);
        alertDialog.setTitle(src);
        alertDialog.show();
    }

    public void logIn() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        progressDialog.hide();
        startActivity(intent);
        finish();
    }
}