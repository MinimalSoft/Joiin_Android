package com.MinimalSoft.BrujulaUniversitaria.Start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.MinimalSoft.BrujulaUniversitaria.Models.Response_General;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jair-Jacobo on 05/08/2016.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Callback <Response_General>{

    private String message;
    private Button button;
    private EditText dayField;
    private EditText yearField;
    private EditText nameField;
    private EditText phoneField;
    private EditText emailField;
    private EditText lastField;
    private EditText confirmField;
    private EditText passwordField;
    private Spinner monthSpinner;
    private Spinner genderSpinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.register_toolbar);

        button = (Button) this.findViewById(R.id.register_button);
        dayField = (EditText) this.findViewById(R.id.register_dayField);
        yearField = (EditText) this.findViewById(R.id.register_yearField);
        nameField = (EditText) this.findViewById(R.id.register_nameField);
        phoneField = (EditText) this.findViewById(R.id.register_phoneField);
        emailField = (EditText) this.findViewById(R.id.register_emailField);
        lastField = (EditText) this.findViewById(R.id.register_lastNameField);
        confirmField = (EditText) this.findViewById(R.id.register_confirmField);
        passwordField = (EditText) this.findViewById(R.id.register_passwordField);
        monthSpinner = (Spinner) this.findViewById(R.id.register_monthSpinner);
        genderSpinner = (Spinner) this.findViewById(R.id.register_genderSpinner);

        button.setOnClickListener(this);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (!super.onOptionsItemSelected(item)) {
                    NavUtils.navigateUpFromSameTask(this);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String name = nameField.getText().toString().trim();
        String lastName = lastField.getText().toString().trim();
        String telephone = phoneField.getText().toString().trim();
        String day = dayField.getText().toString().trim();
        String year = yearField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();
        String passwordConfirm = confirmField.getText().toString();

        int monthNumber = monthSpinner.getSelectedItemPosition();
        int genderId = genderSpinner.getSelectedItemPosition();

        if (!this.isNameValid(name)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if(!this.isNameValid(lastName)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if(!this.isNumberValid(telephone)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (genderId == 0) {
            Toast.makeText(this, "Seleccione el Genero", Toast.LENGTH_LONG).show();
        } else if (day.length() == 0 || year.length() == 0 || monthNumber == 0) {
            Toast.makeText(this, "Seleccione la Fecha de nacimiento", Toast.LENGTH_LONG).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (password.length() < 8) {;
            Toast.makeText(this, "La contraseña debe contener al menos 8 caracteres", Toast.LENGTH_LONG).show();
        } else if (!passwordConfirm.equals(password)) {
            Toast.makeText(this, "La contraseña de confirmacion no coincide", Toast.LENGTH_LONG).show();
        } else {
            message = "connecting...";
            String gender = genderId == 1 ? "F" : "M";
            String birthday = "" + year + '-' + monthNumber + '-' + day;

            String BASE_URL = "http://ec2-54-210-116-247.compute-1.amazonaws.com";
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            Interfaces interfaces = retrofit.create(Interfaces.class);
            Call <Response_General> call = interfaces.registerUser("register", name, lastName, gender, telephone , email, password, "0", birthday, "0");
            call.enqueue(this);
        }
    }

    /*----Retrofit Methods----*/

    @Override
    public void onResponse(Call<Response_General> call, Response<Response_General> response) {
        int code = response.code();
        //String message = response.body().getResponse();
        Log.d(this.getClass().getSimpleName(), "Code: " + code);
    }

    @Override
    public void onFailure(Call<Response_General> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(this.getClass().getSimpleName(), "Message: " + t.getMessage());
        t.printStackTrace();
    }

    private boolean isNameValid (String name) {
        boolean flag = false;
        char c;

        for (short i = 0; i < name.length(); i++) {
            c = name.charAt(i);

            if((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && c!= ' ' && c != '\u00F1' && c != '\u00D1') {
                Log.e(this.getClass().getSimpleName(), "Character not accepted: " + c);
                flag = true;
                break;
            }
        }

        if (name.length() == 0) {
            message = "El campo Nombre o Apellido esta vacio";
        } else if (name.length() < 3) {
            message = "Escriba un nombre y/o appellido validos";
        } else if (flag) {
            message = "El nombre y el apellido solo puede contener letras";
        } else {
            return true;
        }

        return false;
    }

    private boolean isNumberValid (String number) {
        boolean flag = false;
        char c;

        for (short i = 0; i < number.length(); i++) {
            c = number.charAt(i);

            if(c < '0' || c > '9') {
                Log.e(this.getClass().getSimpleName(), "Character not accepted: " + c);
                flag = true;
                break;
            }
        }

        if (number.length() == 0) {
            message = "El campo Telefono esta vacio";
        } else if (number.length() < 8 || number.length() > 10 || flag) {
            message = "Escriba un numero de telefono valido";
        }  else {
            return true;
        }

        return false;
    }

    private boolean isEmailValid (String email) {
        boolean flag = false;
        char c;
        int i;

        for (i = 0; i < email.length(); i++) {
            c = email.charAt(i);

            if(c == '\u0040') {
                flag = true;
                break;
            }
        }

        if (email.length() == 0) {
            message = "El campo Correo esta vacio";
        } else if (email.length() < 3 || !flag) {
            message = "Correo electronico no valido";
        } else {
            String username = email.substring(0, i);
            String domain = email.substring(i + 1);

            if (username.length() == 0) {
                message = "Direccion de correo no aceptado";
            } else if (domain.length() == 0) {
                message = "El correo no tiene dominio";
            } else {
                return true;
            }

            Log.d(this.getClass().getSimpleName(), "Username: " + username);
            Log.d(this.getClass().getSimpleName(), "Domain: " + domain);
        }

        return false;
    }
}