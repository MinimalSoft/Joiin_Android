package com.MinimalSoft.Joiin.Start;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Facebook.LoginCallback;
import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Main.MainActivity;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.UserResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Viewer.FormViewerActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements View.OnClickListener, Callback<UserResponse> {
    private CallbackManager facebookCallbackManager;
    private ProgressDialog progressDialog;
    private EditText passwordField;
    private EditText emailField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.login_accessButton);
        Button registerButton = (Button) findViewById(R.id.login_registerButton);
        Button facebookButton = (Button) findViewById(R.id.login_facebookButton);

        passwordField = (EditText) findViewById(R.id.login_passwordField);
        emailField = (EditText) findViewById(R.id.login_emailField);

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(true);

        registerButton.setOnClickListener(this);
        facebookButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        // Creating a callback manager to handle login responses.
        facebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new LoginCallback(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*----OnClickListener methods----*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_facebookButton:
                List<String> facebookPermissions = Arrays.asList("public_profile", "email", "user_birthday");
                LoginManager.getInstance().logInWithReadPermissions(this, facebookPermissions);
                break;

            case R.id.login_registerButton:
                Intent intent = new Intent(this, FormViewerActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.login_accessButton:
                loginAttempt();
                break;
        }
    }

    /*----Retrofit methods----*/
    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        progressDialog.dismiss();

        if (response.isSuccessful()) {
            if (response.body().getResponse().equals("success")) {
                String fullName = response.body().getData().getName() + ' ' + response.body().getData().getLastName();

                SharedPreferences.Editor preferencesEditor = getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE).edit();
                preferencesEditor.putInt(Joiin.USER_ID, response.body().getData().getIdUser());
                preferencesEditor.putString(Joiin.USER_EMAIL, emailField.getText().toString());
                preferencesEditor.putString(Joiin.USER_NAME, fullName);
                preferencesEditor.apply();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("Error: " + response.body().getMessage()
                                + "\nCorreo y/o contraseña incorrectos.")
                        .setTitle("Verifique los datos")
                        .setPositiveButton("Ok", null)
                        .create().show();

                passwordField.setText("");
            }
        } else {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }

    private void loginAttempt() {
        if (entriesValid()) {
            progressDialog.show();
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
            api.logIn("login", email, password, "", "").enqueue(this);
        }
    }

    private boolean entriesValid() {
        View field = null;
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (password.isEmpty()) {
            passwordField.setError("Inserte la contraseña");
            field = passwordField;
        }

        if (email.isEmpty()) {
            emailField.setError("Inserte el correo");
            field = emailField;
        }

        if (field == null) {
            if (password.length() < Joiin.MIN_PASSWORD_LENGHT) {
                passwordField.setError("Contraseña incorrecta");
                field = passwordField;
            }

            if (!email.matches(Patterns.EMAIL_ADDRESS.toString())) {
                emailField.setError("Correo electrónico incorrecto");
                field = emailField;
            }

            if (field == null) {
                passwordField.setText(password);
                emailField.setText(email);
                return true;
            }
        }

        field.requestFocus();
        return false;
    }
}