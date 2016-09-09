package com.MinimalSoft.BrujulaUniversitaria.Start;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BrujulaUniversitaria.Models.Response_General;
import com.MinimalSoft.BrujulaUniversitaria.Facebook.FacebookDataCollector;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import android.app.ProgressDialog;
import android.app.Activity;

import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements View.OnClickListener, Callback<Response_General> {
    private CallbackManager facebookCallbackManager;
    private LoginButton facebookLoginButton;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private EditText passwordField;
    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        this.setContentView(R.layout.activity_login);

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

        progressDialog.setMessage("Cargando. Espere...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setPositiveButton("OK", null);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        fakeFacebookButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

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
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString();

                if (email.length() == 0) {
                    Toast.makeText(this, "Inserte el correo", Toast.LENGTH_LONG).show();
                } else if (password.length() == 0) {
                    Toast.makeText(this, "Inserte la contraseña", Toast.LENGTH_LONG).show();
                } else {
                    String BASE_URL = "http://ec2-54-210-116-247.compute-1.amazonaws.com";
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                    Interfaces interfaces = retrofit.create(Interfaces.class);
                    Call<Response_General> call = interfaces.logInUser("login", email, password, "0", "0");
                    call.enqueue(this);
                }

                break;
        }
    }

    /*----Retrofit Methods----*/

    @Override
    public void onResponse(Call<Response_General> call, Response<Response_General> response) {
        if (response.code() == 404) {
            Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
        } else if (!response.body().getResponse().equals("success")) {
            Toast.makeText(this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            this.logIn();
        }
    }

    @Override
    public void onFailure(Call<Response_General> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
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