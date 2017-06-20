package com.MinimalSoft.Joiin;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import java.util.HashMap;

public class BU {
    /* URL's for Wordpress and API servers. */
    public static final String WP_URL = "http://buniversitaria.com";
    public static final String API_URL = "http://api.buniversitaria.com";
    /* Name for the SQLite database. */
    public static final String TRANSPORT_DATABASE = "transporte.db";
    /* Shared preferences keys. */
    public static final String PREFERENCES = "BU_PREFERENCES";
    public static final String USER_PHOTO = "PROFILE IMAGE";
    public static final String USER_COVER = "COVER IMAGE";
    public static final String USER_EMAIL = "USER EMAIL";
    public static final String USER_NAME = "USER NAME";
    public static final String USER_ID = "USER ID";
    /* Activities extras and bundles keys. */
    public static final String PLACE_ID_KEY = "ID";
    public static final String JSON_DATA_KEY = "JSON";
    public static final String PLACE_TYPE_KEY = "PLACE";
    public static final String PLACE_NAME_KEY = "NAME";
    public static final String MAP_MARKER_KEY = "MARKER";
    public static final String RESOURCE_KEY = "RESOURCE";
    public static final String TRANSPORT_TYPE_KEY = "TRANSPORT";
    public static final String ACTIVITY_TITLE_KEY = "TITLE";
    /* Default id's for all categories. */
    public static final int FEATURED_ID = 1;
    public static final int BARS_ID = 2;
    public static final int FOOD_ID = 3;
    public static final int GYMS_ID = 4;
    public static final int SUPPLIES_ID = 5;
    public static final int RESIDENCES_ID = 6;
    public static final int JOBS_ID = 7;
    /* Default id's for all transport means. */
    public static final String METRO_ID = "METRO";
    public static final String METROBUS_ID = "MB";
    public static final String TREN_ID = "TL";
    public static final String TROLEBUS_ID = "TB";
    public static final String SUBURBANO_ID = "SUB";
    public static final String ECOBICI_ID = "ECO";
    /* Arbitrary values for multiple requests */
    public static final int NO_VALUE = 0;
    public static final int IMAGE_PICKER_REQUEST = 3;
    private static final SparseArray<String> CATEGORIES_NAMES = new SparseArray<>();
    private static final HashMap<String, String> TRANSPORT_NAMES = new HashMap<>();

    static {
        CATEGORIES_NAMES.append(BARS_ID, "Bares");
        CATEGORIES_NAMES.append(FOOD_ID, "Comida");
        CATEGORIES_NAMES.append(JOBS_ID, "Trabajos");
        CATEGORIES_NAMES.append(GYMS_ID, "Gimnasios");
        CATEGORIES_NAMES.append(SUPPLIES_ID, "Materiales");
        CATEGORIES_NAMES.append(FEATURED_ID, "Destacados");
        CATEGORIES_NAMES.append(RESIDENCES_ID, "Residencias");

        TRANSPORT_NAMES.put(METRO_ID, "Metro");
        TRANSPORT_NAMES.put(ECOBICI_ID, "Ecobici");
        TRANSPORT_NAMES.put(TREN_ID, "Tren Ligero");
        TRANSPORT_NAMES.put(TROLEBUS_ID, "Trolebus");
        TRANSPORT_NAMES.put(METROBUS_ID, "Metrobús");
        TRANSPORT_NAMES.put(SUBURBANO_ID, "Suburbano");
    }

    public static int getCategoryColor(Context context, int type) {
        switch (type) {
            case 1:
                return ContextCompat.getColor(context, R.color.featured);
            case 2:
                return ContextCompat.getColor(context, R.color.bars);
            case 3:
                return ContextCompat.getColor(context, R.color.food);
            case 4:
                return ContextCompat.getColor(context, R.color.gyms);
            case 5:
                return ContextCompat.getColor(context, R.color.supplies);
            case 6:
                return ContextCompat.getColor(context, R.color.residences);
            case 7:
                return ContextCompat.getColor(context, R.color.jobs);
            default:
                return ContextCompat.getColor(context, R.color.iron);
        }
    }

    public static String getCategoryName(int typeID) {
        return CATEGORIES_NAMES.get(typeID);
    }

    public static String getTransportName(String typeID) {
        return TRANSPORT_NAMES.get(typeID);
    }
}