package com.MinimalSoft.BrujulaUniversitaria.Start;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Web.WebActivity;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;
import com.MinimalSoft.BrujulaUniversitaria.Models.UserResponse;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.PromptedDataAnalyzer;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.EditText;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements DialogInterface.OnClickListener, Callback<UserResponse> {
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;

    private EditText passwordField;
    private EditText confirmField;
    private EditText emailField;
    private EditText phoneField;
    private EditText nameField;
    private EditText lastField;

    private Spinner daySpinner;
    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Spinner genderSpinner;

    private boolean isDataAccepted;
    private String telephone;
    private String lastName;
    private String password;
    private String birthday;
    private String gender;
    private String email;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.register_toolbar);

        nameField = (EditText) this.findViewById(R.id.register_nameField);
        phoneField = (EditText) this.findViewById(R.id.register_phoneField);
        emailField = (EditText) this.findViewById(R.id.register_emailField);
        lastField = (EditText) this.findViewById(R.id.register_lastNameField);
        confirmField = (EditText) this.findViewById(R.id.register_confirmField);
        passwordField = (EditText) this.findViewById(R.id.register_passwordField);
        genderSpinner = (Spinner) this.findViewById(R.id.register_genderSpinner);
        monthSpinner = (Spinner) this.findViewById(R.id.register_monthSpinner);
        yearSpinner = (Spinner) this.findViewById(R.id.register_yearSpinner);
        daySpinner = (Spinner) this.findViewById(R.id.register_daySpinner);

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        alertDialog = new AlertDialog.Builder(this, AlertDialog.BUTTON_NEUTRAL);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(true);

        isDataAccepted = false;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //Log.i(getClass().getSimpleName(), "keyCode;" + keyCode + "KeyEbent: " + event.toString());
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            verifyData();
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.options_check:
                // TODO: Verify data before sending to the Database
                /*if (!isDataAccepted) {
                    verifyData();
                }

                if (isDataAccepted) {
                    alertDialog.show();
                }*/

                alertDialog.setMessage("Al crear esta cuenta, estas aceptando los Términos de Privacidad.");
                alertDialog.setNegativeButton("Cancelar", null);
                alertDialog.setPositiveButton("Aceptar", this);
                alertDialog.setTitle("Advertencia");
                alertDialog.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*-----DialogInterface method----*/

    @Override
    public void onClick(DialogInterface dialog, int which) {
        progressDialog.show();
        String urlAPI = getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
        minimalSoftAPI.registerUser("register", name, lastName, gender, birthday, telephone, email, password, "", "", "").enqueue(this);
    }

    /*----Callback Methods----*/

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        progressDialog.hide();

        if (response.code() == 404) {
            alertDialog.setTitle("Error al conectar con el servidor");
            alertDialog.setMessage("¿Intentar de nuevo?");
        } else if (response.body().getResponse().equals("alert")) {
            alertDialog.setMessage("Ya existe un usuario con la misma información");
            alertDialog.setNegativeButton(null, null);
            alertDialog.setPositiveButton("Ok", null);
            alertDialog.setTitle("Verificar datos");
        } else {
            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
            Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

            preferencesEditor.putString("USER_NAME", name + ' ' + lastName);
            preferencesEditor.putString("USER_EMAIL", email);
            //preferencesEditor.putBoolean("USER_PICS", false);
            preferencesEditor.putBoolean("LOGGED_IN", true);
            preferencesEditor.apply();
            progressDialog.hide();
            startActivity(intent);
            finish();
            return;
        }

        alertDialog.show();
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        alertDialog.setNegativeButton(null, null);
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.setMessage(t.getMessage());
        alertDialog.setTitle("Failure");
        progressDialog.hide();
        alertDialog.show();
    }

    /*-----onClick Method----*/

    public void openLink(View v) {
        String link = getResources().getString(R.string.terms_url);
        Intent intent = new Intent(this, WebActivity.class);

        intent.putExtra("TITLE", "Aviso de privacidad");
        intent.putExtra("LINK", link);
        startActivity(intent);

        /*try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brujulauniversitaria.com.mx/aviso-de-privacidad"));
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request, please install a Web browser.", Toast.LENGTH_LONG).show();
        }*/
    }

    private void verifyData() {
        name = nameField.getText().toString().trim();
        password = passwordField.getText().toString();
        email = emailField.getText().toString().trim();
        lastName = lastField.getText().toString().trim();
        telephone = phoneField.getText().toString().trim();

        int day = daySpinner.getSelectedItemPosition();
        int year = yearSpinner.getSelectedItemPosition();
        int month = monthSpinner.getSelectedItemPosition();
        int genderId = genderSpinner.getSelectedItemPosition();
        String confirmPassword = confirmField.getText().toString();

        PromptedDataAnalyzer analyzer = new PromptedDataAnalyzer();
        //isDataAccepted = analyzer.isAcceptable(name, lastName, telephone, email, password, confirmPassword, genderId, day, month, year);

        //TODO: verify before accepting
        gender = genderId == 1 ? "F" : "M";
        birthday = "" + (year + 1989) + '-' + month + '-' + day;
    }
}