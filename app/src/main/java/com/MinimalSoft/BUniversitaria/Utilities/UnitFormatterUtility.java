package com.MinimalSoft.BUniversitaria.Utilities;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class UnitFormatterUtility {
    public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    public static final Locale MEXICAN_LOCALE = new Locale("ES", "MX");
    public static final TimeZone MEXICAN_ZONE = TimeZone.getTimeZone("America/Mexico_City");

    static public String getTimeDescription(String dateTime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN, MEXICAN_LOCALE);

        try {
            DateTime currentDateTime = new DateTime(Calendar.getInstance(MEXICAN_ZONE, MEXICAN_LOCALE).getTime());
            DateTime eventDateTime = new DateTime(dateFormatter.parse(dateTime));
            Period period = new Period(eventDateTime, currentDateTime);

            if (period.getYears() > 1) {
                return "Hace " + period.getYears() + " años.";
            } else if (period.getYears() > 0) {
                return "Hace un año.";
            } else if (period.getMonths() > 1) {
                return "Hace " + period.getMonths() + " meses.";
            } else if (period.getMonths() > 0) {
                return "Hace un mes.";
            } else if (period.getWeeks() > 1) {
                return "Hace " + period.getWeeks() + " semanas.";
            } else if (period.getWeeks() > 0) {
                return "Hace una semana.";
            } else if (period.getDays() > 1) {
                return "Hace " + period.getDays() + " días.";
            } else if (period.getDays() > 0) {
                return "Ayer.";
            } else if (period.getHours() > 0) {
                return "Hoy.";
            } else if (period.getMinutes() > 0) {
                return "Hace unos momentos.";
            } else {
                return "Hace un momento.";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime.replace(" ", " a las ");
        }
    }

    static public String[] getFormattedDistance(float meters) {
        final float kilometer = 1000f;
        String formattedAmount;
        String label;

        if (meters >= kilometer) {
            label = "Km";
            float kilometers = meters / kilometer;
            formattedAmount = String.format(UnitFormatterUtility.MEXICAN_LOCALE, "%.1f", kilometers);
        } else {
            label = "Mts";
            formattedAmount = String.valueOf(Math.round(meters));
        }

        return new String[]{formattedAmount, label};
    }
}