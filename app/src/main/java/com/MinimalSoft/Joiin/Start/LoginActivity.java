package com.MinimalSoft.Joiin.Start;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Main.MainActivity;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.FacebookData;
import com.MinimalSoft.Joiin.Responses.UserResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Viewer.FormViewerActivity;
import com.MinimalSoft.Joiin.Widgets.SingleInputAlertDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements FacebookCallback<LoginResult>, Callback<UserResponse>,
        GraphRequest.GraphJSONObjectCallback, SingleInputAlertDialog.OnInputEventListener {

    private CallbackManager facebookCallbackManager = CallbackManager.Factory.create();
    private ProgressDialog progressDialog;
    private MinimalSoftServices api;
    private FacebookData facebookData;

    private EditText passwordField;
    private EditText emailField;

    private WebService ws;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginManager.getInstance().registerCallback(facebookCallbackManager, this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(MinimalSoftServices.class);

        emailField = (EditText) findViewById(R.id.login_emailField);
        passwordField = (EditText) findViewById(R.id.login_passwordField);

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginActionFired(View view) {
        switch (view.getId()) {
            case R.id.login_facebookButton:
                LoginManager.getInstance().logInWithReadPermissions(this, Joiin.LOGIN_PERMISSIONS);
                break;

            case R.id.login_accessButton:
                if (entriesValid()) {
                    progressDialog.show();
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();

                    ws = WebService.EMAIL_LOGIN;
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    api.logIn("login", email, password, null, deviceToken).enqueue(this);
                }
                break;
            case R.id.login_registerButton:
                Intent intent = new Intent(this, FormViewerActivity.class);
                intent.putExtra(FormViewerActivity.FORM_TYPE_KEY,
                        FormViewerActivity.FormType.USER_REGISTRATION);
                //intent.putExtra(Joiin.USER_DATA_KEY, facebookData);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    /*------- FacebookCallback methods -----------------------------------------------------------*/
    @Override
    public void onSuccess(LoginResult loginResult) {
        progressDialog.show();

        Set<String> permissions = loginResult.getRecentlyGrantedPermissions();
        String facebookApiFields = "name,first_name,last_name,gender";

        if (permissions.contains("user_birthday")) {
            facebookApiFields += ",birthday";
        }

        if (permissions.contains("email")) {
            facebookApiFields += ",email";
        }

        Bundle parameters = new Bundle();
        parameters.putString("fields", facebookApiFields);

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();
        error.printStackTrace();
    }

    @Override
    public void onCancel() {

    }

    /*------- GraphJSONObjectCallback methods ----------------------------------------------------*/
    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        if (response.getError() == null) {
            facebookData = new Gson().fromJson(object.toString(), FacebookData.class);

            if (facebookData.getEmail() == null) {
                String message = "Este campo es necesario para que puedas iniciar sesión en " + getString(R.string.app_name)
                        + ".\n\nPor favor ingresa una dirección de correo electrónico.";

                SingleInputAlertDialog inputDialog = new SingleInputAlertDialog(this, "Correo electrónico requerido",
                        message, SingleInputAlertDialog.EMAIL_INPUT, Patterns.EMAIL_ADDRESS.toString());
                inputDialog.addOnInputDialogListener(this);

                progressDialog.hide();
                inputDialog.show();
            } else {
                onInputRetrieved(facebookData.getEmail());
            }
        } else {
            Toast.makeText(this, response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
            progressDialog.hide();
        }
    }

    /*------- OnInputEventListener methods -------------------------------------------------------*/
    @Override
    public void onInputRetrieved(String input) {
        progressDialog.show();
        facebookData.setEmail(input);

        ws = WebService.FACEBOOK_LOGIN;
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        api.logIn("login", input, null, facebookData.getId(), deviceToken).enqueue(this);
    }

    @Override
    public void onInputDialogCanceled() {
        LoginManager.getInstance().logOut();
    }

    /*------- Callback methods -------------------------------------------------------------------*/
    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        if (response.isSuccessful()) {
            UserResponse userResponse = response.body();

            if (userResponse.getResponse().equals("success")) {
                SharedPreferences.Editor prefsEditor = getSharedPreferences(Joiin.USER_PREFERENCES, MODE_PRIVATE).edit();
                prefsEditor.putInt(Joiin.USER_ID, userResponse.getData().getIdUser());

                switch (ws) {
                    case FACEBOOK_REGISTRATION:
                    case FACEBOOK_LOGIN:
                        prefsEditor.putString(Joiin.USER_EMAIL, facebookData.getEmail());
                        prefsEditor.putString(Joiin.USER_NAME, facebookData.getFullName());
                        break;

                    case EMAIL_LOGIN:
                        String fullName = userResponse.getData().getName() + ' ' + userResponse.getData().getLastName();
                        prefsEditor.putString(Joiin.USER_EMAIL, emailField.getText().toString());
                        prefsEditor.putString(Joiin.USER_NAME, fullName);
                        break;
                }

                prefsEditor.apply();
                progressDialog.dismiss();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                onFailureResponse(response.body());
            }
        } else {
            LoginManager.getInstance().logOut();
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        progressDialog.dismiss();
        LoginManager.getInstance().logOut();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }

    private void onFailureResponse(UserResponse bodyResponse) {
        switch (ws) {
            case FACEBOOK_LOGIN:
                String pictureURL = "http://graph.facebook.com/" + facebookData.getId() + "/picture";
                String birthday = (facebookData.getBirthday() != null) ? (facebookData.getBirthday()) : "";
                String gender = (facebookData.getGender() != null) ? (facebookData.getGender().substring(0, 1)) : "";

                // Todo: Fix API bug - Column 'phone' cannot be null
                ws = WebService.FACEBOOK_REGISTRATION;
                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                api.registerUser("register", facebookData.getFirstName(), facebookData.getLastName(), gender.toUpperCase(),
                        birthday, "", facebookData.getEmail(), null, pictureURL, facebookData.getId(), deviceToken).enqueue(this);
                break;

            case FACEBOOK_REGISTRATION:
                LoginManager.getInstance().logOut();

                String message = "Se encontró un problema al intentar iniciar sesión.\n\n Detalles:"
                        + "\nemail: " + facebookData.getEmail()
                        + "\nfacebook ID: " + facebookData.getId()
                        + "\nAPI response: " + bodyResponse.getResponse() + " - " + bodyResponse.getMessage();

                progressDialog.hide();
                showAlertMessage("Error", message);
                break;

            case EMAIL_LOGIN:
                message = "Error: " + bodyResponse.getMessage() + "\nCorreo y/o contraseña incorrectos.";

                progressDialog.hide();
                showAlertMessage("Verifique los datos", message);
                break;
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
            if (password.length() < Joiin.MIN_PASSWORD_LENGTH) {
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

    private void showAlertMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null)
                .setMessage(message)
                .setTitle(title)
                .create()
                .show();
    }

    private enum WebService {
        FACEBOOK_REGISTRATION, FACEBOOK_LOGIN, EMAIL_LOGIN, REGISTRATION
    }
}