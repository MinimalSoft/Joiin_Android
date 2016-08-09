package com.MinimalSoft.BrujulaUniversitaria.Start;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BrujulaUniversitaria.Models.Response_General;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jair-Jacobo on 05/08/2016.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener, Callback<Response_General> {
    private Intent intent;
    //private EditText dayField;
    //private EditText yearField;
    private EditText nameField;
    private EditText phoneField;
    private EditText emailField;
    private EditText lastField;
    private EditText confirmField;
    private EditText passwordField;
    private Spinner genderSpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Spinner daySpinner;

    private Date date;
    private int phone;
    private String name;
    private String email;
    private String gender;
    private String message;
    private String birthday;
    private String password;
    private String lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.register_toolbar);
        TextView link = (TextView) this.findViewById(R.id.register_link);
        Button button = (Button) this.findViewById(R.id.register_button);

        //dayField = (EditText) this.findViewById(R.id.register_dayField);
        //yearField = (EditText) this.findViewById(R.id.register_yearField);
        nameField = (EditText) this.findViewById(R.id.register_nameField);
        phoneField = (EditText) this.findViewById(R.id.register_phoneField);
        emailField = (EditText) this.findViewById(R.id.register_emailField);
        lastField = (EditText) this.findViewById(R.id.register_lastNameField);
        confirmField = (EditText) this.findViewById(R.id.register_confirmField);
        passwordField = (EditText) this.findViewById(R.id.register_passwordField);

        daySpinner = (Spinner) this.findViewById(R.id.register_daySpinner);
        yearSpinner = (Spinner) this.findViewById(R.id.register_yearSpinner);
        monthSpinner = (Spinner) this.findViewById(R.id.register_monthSpinner);
        genderSpinner = (Spinner) this.findViewById(R.id.register_genderSpinner);

        link.setOnClickListener(this);
        button.setOnClickListener(this);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getData() {
        name = nameField.getText().toString().trim();
        password = passwordField.getText().toString();
        email = emailField.getText().toString().trim();
        lastName = lastField.getText().toString().trim();
        //String day = dayField.getText().toString().trim();
        //String year = yearField.getText().toString().trim();
        String telephone = phoneField.getText().toString().trim();
        String passwordConfirm = confirmField.getText().toString();

        int day = daySpinner.getSelectedItemPosition();
        int year = yearSpinner.getSelectedItemPosition();
        int month = monthSpinner.getSelectedItemPosition();
        int genderId = genderSpinner.getSelectedItemPosition();

        if (!this.isNameValid(name)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (!this.isNameValid(lastName)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (!this.isPhoneValid(telephone)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (genderId == 0) {
            Toast.makeText(this, "Seleccione el Género", Toast.LENGTH_LONG).show();
        } else if (!this.isDateValid(day, month, year)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else if (password.length() < 8) {
            Toast.makeText(this, "La contraseña debe contener al menos 8 caracteres", Toast.LENGTH_LONG).show();
        } else if (!passwordConfirm.equals(password)) {
            Toast.makeText(this, "La contraseña de confirmación no coincide", Toast.LENGTH_LONG).show();
        } else {
            phone = Integer.valueOf(telephone);
            gender = (genderId == 1 ? "F" : "M");
            birthday = String.format("%s-%02d-%02d", yearSpinner.getSelectedItem().toString(), month, day);

            Log.i(this.getClass().getSimpleName(), "Data: " +
                    "\n Name:" + name +
                    "\n Last Name: " + lastName +
                    "\n Gender: " + gender +
                    "\n Phone: " + phone +
                    "\n Email: " + email +
                    "\n Password: " + password +
                    "\n Birthday: " + birthday);

            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
            confirmDialog.setMessage("Al crear esta cuenta, estas aceptando los Terminos de Privacidad.");
            confirmDialog.setNegativeButton("Cancelar", null);
            confirmDialog.setPositiveButton("Aceptar", this);
            confirmDialog.setTitle("Advertencia");
            confirmDialog.show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String BASE_URL = "http://ec2-54-210-116-247.compute-1.amazonaws.com";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces interfaces = retrofit.create(Interfaces.class);
        Call<Response_General> call = interfaces.registerUser("register", name, lastName, gender, phone, email, password, "", birthday, "");
        call.enqueue(this);
    }

    /*----Retrofit Methods----*/

    @Override
    public void onResponse(Call<Response_General> call, Response<Response_General> response) {
        if (response.code() == 404) {
            Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
        } else if (!response.body().getResponse().equals("success")) {
            Toast.makeText(this, response.body().getMessage(), Toast.LENGTH_LONG).show();
        } else {

        }
    }

    @Override
    public void onFailure(Call<Response_General> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(this.getClass().getSimpleName(), "Message: " + t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_link:
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://brujulauniversitaria.com.mx/aviso-de-privacidad"));
                    this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No application can handle this request, please install a Web browser.", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.register_button:
                this.getData();
                break;
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

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

    private boolean isNameValid(String name) {
        boolean flag = false;
        char c;

        for (short i = 0; i < name.length(); i++) {
            c = name.charAt(i);

            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && c != ' ' && c != '\u00F1' && c != '\u00D1') {
                Log.e(this.getClass().getSimpleName(), "Character not accepted: " + c);
                flag = true;
                break;
            }
        }

        if (name.length() == 0) {
            message = "El campo Nombre o Apellido esta vacío";
        } else if (name.length() < 3) {
            message = "Escriba un nombre y/o appellido válidos";
        } else if (flag) {
            message = "El nombre y el apellido solo puede contener letras";
        } else {
            return true;
        }

        return false;
    }

    private boolean isPhoneValid(String number) {
        boolean flag = false;
        char c;

        for (short i = 0; i < number.length(); i++) {
            c = number.charAt(i);

            if (c < '0' || c > '9') {
                Log.e(this.getClass().getSimpleName(), "Character not accepted: " + c);
                flag = true;
                break;
            }
        }

        if (number.length() == 0) {
            message = "El campo Teléfono esta vacío";
        } else if (number.length() < 8 || number.length() > 10 || flag) {
            message = "Escriba un numero de telefono válido";
        } else {
            return true;
        }

        return false;
    }

    private boolean isDateValid(int day, int month, int year) {
        if (day == 0) {
            message = "Seleccione el Dia de nacimiento";
        } else if (month == 0) {
            message = "Seleccione el Mes de nacimiento";
        } else if (year == 0) {
            message = "Seleccione el Año de nacimiento";
        } else {
            return true;
        }

        return false;
    }

    private boolean isEmailValid(String email) {
        boolean flag = false;
        char c;
        int i;

        for (i = 0; i < email.length(); i++) {
            c = email.charAt(i);

            if (c == '\u0040') {
                flag = true;
                break;
            }
        }

        if (email.length() == 0) {
            message = "El campo Correo esta vacío";
        } else if (email.length() < 3 || !flag) {
            message = "Correo electronico no válido";
        } else {
            String username = email.substring(0, i);
            String domain = email.substring(i + 1);

            if (username.length() == 0) {
                message = "Dirección de correo no aceptado";
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