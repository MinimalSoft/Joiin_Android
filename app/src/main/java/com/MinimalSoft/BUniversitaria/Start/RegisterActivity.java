package com.MinimalSoft.BUniversitaria.Start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.MinimalSoft.BUniversitaria.R;

public class RegisterActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private Spinner genderSpinner;
    private EditText lastNameField;
    private EditText passwordField;
    private EditText confirmField;
    private EditText emailField;
    private EditText phoneField;
    private EditText nameField;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = (Button) findViewById(R.id.register_button);
        nameField = (EditText) findViewById(R.id.register_nameField);
        phoneField = (EditText) findViewById(R.id.register_phoneField);
        emailField = (EditText) findViewById(R.id.register_emailField);
        passwordField = (EditText) findViewById(R.id.register_passwordField);
        lastNameField = (EditText) findViewById(R.id.register_lastNameField);
        confirmField = (EditText) findViewById(R.id.register_confirmationField);
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        datePicker = (DatePicker) findViewById(R.id.register_datePicker);
        genderSpinner = (Spinner) findViewById(R.id.register_genderSpinner);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}