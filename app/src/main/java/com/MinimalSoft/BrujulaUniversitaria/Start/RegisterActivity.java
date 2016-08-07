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

import com.MinimalSoft.BrujulaUniversitaria.R;

/**
 * Created by Jair-Jacobo on 05/08/2016.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

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

        String message;
        String name = nameField.getText().toString();
        String lastName = lastField.getText().toString();
        String telephone = phoneField.getText().toString();
        String day = dayField.getText().toString();
        String year = yearField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String passwordConfirm = confirmField.getText().toString();

        int monthNumber = monthSpinner.getSelectedItemPosition();
        int genderId = genderSpinner.getSelectedItemPosition();

        if (name.length() == 0) {
            message = "Escriba el nombre del usuario";
            Toast.makeText(this.getBaseContext(), "Escriba el nombre del usuario", Toast.LENGTH_SHORT);
        } else if(lastName.length() == 0) {
            message = "El campo de apellido no puede quedar vacio";
            //Toast.makeText(this, "El campo de apellido no puede quedar vacio", Toast.LENGTH_SHORT);
        } else if(telephone.length() == 0) {
            message = "Inserte un numero de telefono";
            //Toast.makeText(this, "Inserte un numero de telefono", Toast.LENGTH_SHORT);
        } else if (genderId == 0) {
            message = "Seleccione el genero";
            //Toast.makeText(this, "Seleccione el genero", Toast.LENGTH_SHORT);
        } else if (day.length() == 0 || year.length() == 0 || monthNumber == 0) {
            message = "Seleccione la fecha de nacimiento";
            //Toast.makeText(this, "Seleccione la fecha de nacimiento", Toast.LENGTH_SHORT);
        } else if (email.length() == 0) {
            message = "Inserte su direccion de correo electronico";
            //Toast.makeText(this, "Inserte su direccion de correo electronico", Toast.LENGTH_SHORT);
        } else if (password.length() < 4) {
            message = "Escriba una contraseña mayor a 4 caracteres";
            //Toast.makeText(this, "Escriba una contraseña mayor a 4 caracteres", Toast.LENGTH_SHORT);
        } else if (!passwordConfirm.equals(password)) {
            message = "La contraseña de confirmacion no coincide";
            //Toast.makeText(this, "La contraseñas no coinciden", Toast.LENGTH_SHORT);
        } else {
            String gender = genderId == 1 ? "F" : "M";
            String birthday = "" + year + '-' + monthNumber + '-' + day;
        }

        //Log.d(this.getClass().getSimpleName(), message);
    }
}