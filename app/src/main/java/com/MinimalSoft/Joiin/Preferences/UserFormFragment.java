package com.MinimalSoft.Joiin.Preferences;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Main.MainActivity;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.UserResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.MinimalSoft.Joiin.Web.WebActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFormFragment extends Fragment implements DialogInterface.OnClickListener, View.OnClickListener, Callback<UserResponse> {
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
        View inflatedView = inflater.inflate(R.layout.fragment_user_form, container, false);

        Button button = (Button) inflatedView.findViewById(R.id.form_button);
        datePicker = (DatePicker) inflatedView.findViewById(R.id.form_datePicker);
        genderSpinner = (Spinner) inflatedView.findViewById(R.id.form_genderSpinner);

        emailField = (EditText) inflatedView.findViewById(R.id.form_emailField);
        nameField = (EditText) inflatedView.findViewById(R.id.form_userNameField);
        phoneField = (EditText) inflatedView.findViewById(R.id.form_cellphoneField);
        confirmField = (EditText) inflatedView.findViewById(R.id.form_confirmField);
        passwordField = (EditText) inflatedView.findViewById(R.id.form_passwordField);
        lastNameField = (EditText) inflatedView.findViewById(R.id.form_lastNameField);

        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(true);

        button.setOnClickListener(this);

        return inflatedView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
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

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked (e.g.
     *               {@link DialogInterface#BUTTON1}) or the position
     */
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

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
                api.registerUser("register", name, lastName, gender.toUpperCase(), date, phone, email, password, "", "", "").enqueue(this);
                break;

            case DialogInterface.BUTTON_NEUTRAL:
                String link = Joiin.WP_URL + "/aviso-de-privacidad/";
                Intent intent = new Intent(getActivity(), WebActivity.class);

                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Aviso de privacidad");
                intent.putExtra(Joiin.WP_URL, link);
                startActivity(intent);
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
                new AlertDialog.Builder(getActivity())
                        .setMessage("Ya existe un usuario con la misma información.")
                        .setTitle("Verifique los datos.")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();
            } else {
                String fullName = nameField.getText().toString() + ' ' + lastNameField.getText().toString();

                SharedPreferences.Editor preferencesEditor = getActivity().getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE).edit();
                preferencesEditor.putInt(Joiin.USER_ID, response.body().getData().getIdUser());
                preferencesEditor.putString(Joiin.USER_EMAIL, emailField.getText().toString());
                preferencesEditor.putString(Joiin.USER_NAME, fullName);

                //preferencesEditor.putString("FACEBOOK_ID", facebookData.getId());
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
            nameField.setError("Este campo es obligatorio");
            nameField.requestFocus();
        } else if (!name.matches(Joiin.NAME_REGEX)) {
            nameField.setError("Nombre no aceptado");
            nameField.requestFocus();
        } else if (lastName.isEmpty()) {
            lastNameField.setError("Este campo es obligatorio");
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
        } else if (password.length() < 8) {
            passwordField.setError("La contraseña debe contener al menos 8 caracteres sin espacios en blanco");
            passwordField.requestFocus();
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
}