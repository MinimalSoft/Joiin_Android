package com.MinimalSoft.BUniversitaria.Utilities;

import org.apache.commons.validator.routines.EmailValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EntriesValidator {
    private final String PHONE_REGEX = "^(([(]?(\\d{2,4})[)]?)|(\\d{2,4})|([+1-9]+\\d{1,2}))?[-\\s]?(\\d{2,3})?[-\\s]?((\\d{7,8})|(\\d{3,4}[-\\s]\\d{3,4}))$";
    private final String NAME_REGEX = "^[\\p{L} .'-]+$";
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final int UNDEFINED = 0;
    private String message;

    public boolean isAcceptable(String name, String lastName, String phone, String email, String password, String confirmedPassword, int gender, String birthday) {
        boolean isEmailValid = EmailValidator.getInstance().isValid(email);
        boolean isLastNameValid = lastName.matches(NAME_REGEX);
        boolean isPhoneValid = phone.matches(PHONE_REGEX);
        boolean isDateValid = isBirthdayValid(birthday);
        boolean isNameValid = name.matches(NAME_REGEX);

        if (!isNameValid) {
            message = "Nombre inválido";
        } else if (!isLastNameValid) {
            message = "Apellido inválido";
        } else if (!isPhoneValid) {
            message = "Teléfono inválido";
        } else if (gender == UNDEFINED) {
            message = "Seleccione el género";
        } else if (!isDateValid) {
            message = "Seleccione una fecha válida";
        } else if (!isEmailValid) {
            message = "Correo electrónico inválido";
        } else if (password.length() < 8) {
            message = "La contraseña debe contener al menos 8 caracteres";
        } else if (!confirmedPassword.equals(password)) {
            message = "Las contraseñas no coinciden";
        } else {
            return true;
        }

        return false;
    }

    private boolean isBirthdayValid(String dateToValidate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        try {
            sdf.setLenient(false);
            sdf.parse(dateToValidate);
        } catch (NullPointerException e) {
            return false;
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public String getMessage() {
        return message;
    }
}