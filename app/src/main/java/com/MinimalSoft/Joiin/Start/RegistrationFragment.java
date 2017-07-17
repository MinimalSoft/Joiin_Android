package com.MinimalSoft.Joiin.Start;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Main.MainActivity;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.UserResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.MinimalSoft.Joiin.Web.WebActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationFragment extends Fragment implements TextView.OnEditorActionListener,
        DialogInterface.OnClickListener, View.OnClickListener, Callback<UserResponse> {
    private ProgressDialog progressDialog;
    private DatePicker datePicker;
    private Spinner genderSpinner;

    private EditText lastNameField;
    private EditText passwordField;
    private EditText confirmField;
    private EditText emailField;
    private EditText phoneField;
    private EditText nameField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_form_registration, container, false);

        nameField = (EditText) inflatedView.findViewById(R.id.register_nameField);
        phoneField = (EditText) inflatedView.findViewById(R.id.register_phoneField);
        emailField = (EditText) inflatedView.findViewById(R.id.register_emailField);
        datePicker = (DatePicker) inflatedView.findViewById(R.id.register_datePicker);
        genderSpinner = (Spinner) inflatedView.findViewById(R.id.register_genderSpinner);
        passwordField = (EditText) inflatedView.findViewById(R.id.register_passwordField);
        lastNameField = (EditText) inflatedView.findViewById(R.id.register_lastNameField);
        confirmField = (EditText) inflatedView.findViewById(R.id.register_confirmationField);
        TextView textView = (TextView) inflatedView.findViewById(R.id.register_disclaimerButton);

        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        textView.setOnClickListener(this);
        confirmField.setOnEditorActionListener(this);

        setHasOptionsMenu(true);
        return inflatedView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.form_acceptItem) {
            confirmationPrompt();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        confirmationPrompt();
        return true;
    }

    @Override
    public void onClick(View v) {
        String link = Joiin.WP_URL + "/aviso-de-privacidad/";
        Intent intent = new Intent(getActivity(), WebActivity.class);

        intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Aviso de privacidad");
        intent.putExtra(Joiin.WP_URL, link);
        startActivity(intent);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                progressDialog.show();

                int year = datePicker.getYear();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int gen = genderSpinner.getSelectedItemPosition();

                String name = nameField.getText().toString();
                String phone = phoneField.getText().toString();
                String email = emailField.getText().toString();
                String lastName = lastNameField.getText().toString();
                String password = passwordField.getText().toString();

                String date = String.format(UnitFormatterUtility.MEXICAN_LOCALE, "%d-%d-%d", year, month, day);
                String gender = (gen != 0) ? (String.valueOf(genderSpinner.getSelectedItem()).substring(0, 1)) : "O";

                //TODO: Remove this code. Fix API bug:
                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
                api.registerUser("register", name, lastName, gender.toUpperCase(), date, phone, email, password, null, "", deviceToken).enqueue(this);
                break;

            case DialogInterface.BUTTON_NEUTRAL:
                onClick(null);
                break;
        }
    }

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        progressDialog.dismiss();

        if (response.isSuccessful()) {
            if (response.body().getResponse().equals("alert")) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Ya existe un usuario con la misma información.")
                        .setTitle("Verifique los datos.")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();
            } else {
                String fullName = nameField.getText().toString() + ' ' + lastNameField.getText().toString();

                SharedPreferences.Editor preferencesEditor = getActivity().getSharedPreferences(Joiin.USER_PREFERENCES, Context.MODE_PRIVATE).edit();
                preferencesEditor.putInt(Joiin.USER_ID, response.body().getData().getIdUser());
                preferencesEditor.putString(Joiin.USER_EMAIL, emailField.getText().toString());
                preferencesEditor.putString(Joiin.USER_NAME, fullName);
                preferencesEditor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        } else {
            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }

    private boolean verifyRequiredData() {
        String name = nameField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String confirmPass = confirmField.getText().toString().trim();

        if (name.isEmpty()) {
            nameField.setError("Este campo es requerido");
            nameField.requestFocus();
        } else if (!name.matches(Joiin.NAME_REGEX)) {
            nameField.setError("Nombre no aceptado");
            nameField.requestFocus();
        } else if (lastName.isEmpty()) {
            lastNameField.setError("Este campo es requerido");
            lastNameField.requestFocus();
        } else if (!lastName.matches(Joiin.NAME_REGEX)) {
            lastNameField.setError("Apellido no aceptado");
            lastNameField.requestFocus();
        } else if (!phone.isEmpty() && !phone.matches(Patterns.PHONE.toString())) {
            phoneField.setError("Número inválido");
            phoneField.requestFocus();
        } else if (email.isEmpty()) {
            emailField.setError("Este campo es obligatorio");
            emailField.requestFocus();
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toString())) {
            emailField.setError("Correo electrónico inválido");
            emailField.requestFocus();
        } else if (password.isEmpty()) {
            passwordField.setError("Ingrese una contraseña. (Debe tener mínimo " +
                    +Joiin.MIN_PASSWORD_LENGTH + " caracteres sin espacios en blanco)");
            passwordField.requestFocus();
        } else if (password.length() < Joiin.MIN_PASSWORD_LENGTH) {
            passwordField.setError("La contraseña debe contener al menos " +
                    +Joiin.MIN_PASSWORD_LENGTH + " caracteres sin espacios en blanco");
            passwordField.requestFocus();
        } else if (confirmPass.isEmpty()) {
            confirmField.setError("Confirme la contraseña");
            confirmField.requestFocus();
        } else if (!confirmPass.equals(password)) {
            confirmField.setError("Las contraseñas no coinciden");
            confirmField.requestFocus();
        } else {
            passwordField.setText(password);
            lastNameField.setText(lastName);
            emailField.setText(email);
            phoneField.setText(phone);
            nameField.setText(name);

            return true;
        }

        return false;
    }

    private void confirmationPrompt() {
        if (verifyRequiredData()) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Al crear esta cuenta, estas aceptando los Términos de Privacidad de "
                            + getResources().getString(R.string.app_name)
                            + ".\nRecomendamos leer dichos términos antes continuar.")
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Aceptar", this)
                    .setNeutralButton("Leer", this)
                    .setTitle("Algo importante")
                    .create()
                    .show();
        }
    }
}