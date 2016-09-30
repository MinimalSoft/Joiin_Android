package com.MinimalSoft.BrujulaUniversitaria.Utilities;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PromptedDataAnalyzer {
    private String message;

    public boolean isAcceptable(String name, String lastName, String phone, String email, String password, String confirmedPassword, int gender, int day, int month, int year) {
        isDateValid(day, month, year + 1989);
        return false;
    }

    public String getMessage() {
        return message;
    }

    private boolean isNameValid(String name) {
        boolean flag = false;
        char c;

        for (short i = 0; i < name.length(); i++) {
            c = name.charAt(i);

            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && c != ' ' && c != '\u00F1' && c != '\u00D1') {
                flag = true;
                break;
            }
        }

        if (name.length() < 3) {
            message = "Escriba un nombre válido.";
        } else if (flag) {
            message = "El nombre sólo puede contener letras";
        } else {
            return true;
        }

        return false;
    }

    private boolean isPhoneNumberValid(String number) {
        boolean flag = false;
        char c;

        for (short i = 0; i < number.length(); i++) {
            c = number.charAt(i);

            if (c < '0' || c > '9') {
                flag = true;
                break;
            }
        }

        if (number.length() < 8 || number.length() > 10 || flag) {
            message = "Escriba un número de teléfono válido";
        } else {
            return true;
        }

        return false;
    }

    private boolean isDateValid(int day, int month, int year) {
        Date date = new Date(year, month, day);
        Calendar calendar = new GregorianCalendar(year, month, day);
        year = calendar.YEAR;
        month = calendar.MONTH;
        day = calendar.DAY_OF_MONTH;

        Log.i(getClass().getSimpleName(), calendar.toString());
        return false;
    }

    private boolean isEmailValid(String email) {
        boolean flag = false;
        char c;
        int i;

        for (i = 0; i < email.length(); i++) {
            c = email.charAt(i);

            if (c == '\u0040') { // at
                flag = true;
                break;
            }
        }

        if (email.length() < 3 || !flag) {
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
        }

        return false;
    }
}