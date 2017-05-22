package com.MinimalSoft.BUniversitaria.Start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.MinimalSoft.BUniversitaria.Facebook.LoginCallback;
import com.MinimalSoft.BUniversitaria.R;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {
    private CallbackManager facebookCallbackManager;
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
                //Intent intent = new Intent(this, RegisterActivity.class);
                //startActivity(intent);
                break;

            case R.id.login_accessButton:
                //loginRequest();
                break;
        }
    }
}