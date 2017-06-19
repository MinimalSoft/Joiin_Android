package com.MinimalSoft.JOIIN.Start;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MinimalSoft.JOIIN.BU;
import com.MinimalSoft.JOIIN.Facebook.LoginCallback;
import com.MinimalSoft.JOIIN.Main.MainActivity;
import com.MinimalSoft.JOIIN.R;
import com.MinimalSoft.JOIIN.Responses.UserResponse;
import com.MinimalSoft.JOIIN.Services.MinimalSoftServices;
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean b = super.onKeyUp(keyCode, event);

        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            loginRequest();
        }

        return b;
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
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                //finish();
                break;

            case R.id.login_accessButton:
                loginRequest();
                break;
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
            if (response.body().getResponse().equals("alert")) {
                new AlertDialog.Builder(this)
                        .setMessage("Correo y/o contraseña incorrectos.")
                        .setTitle("Verifique los datos.")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();

                passwordField.setText("");
            } else {
                String fullName = response.body().getData().getName() + ' ' + response.body().getData().getLastName();

                SharedPreferences.Editor preferencesEditor = getSharedPreferences(BU.PREFERENCES, Context.MODE_PRIVATE).edit();
                preferencesEditor.putInt(BU.USER_ID, response.body().getData().getIdUser());
                preferencesEditor.putString(BU.USER_EMAIL, emailField.getText().toString());
                preferencesEditor.putString(BU.USER_NAME, fullName);
                preferencesEditor.apply();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }

    private void loginRequest() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();

        if (email.isEmpty()) {
            emailField.setError("Inserte el correo");
        } else if (password.isEmpty()) {
            passwordField.setError("Inserte la contraseña");
        } else {
            progressDialog.show();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
            api.logIn("login", email, password, "", "").enqueue(this);
        }
    }
}