package com.MinimalSoft.BrujulaUniversitaria.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.MinimalSoft.BrujulaUniversitaria.provider.Contrato.Entrys;

/**
 * Clase auxiliar para controlar accesos a la base de datos SQLite
 */
public class BaseDatos extends SQLiteOpenHelper {

    static final int VERSION = 1;

    static final String NAME_DB = "bu.db";


    interface Tablas {
        String ENTRYS = "wp_entrys";
    }

    public BaseDatos(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(
                "CREATE TABLE " + Tablas.ENTRYS + "("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Entrys.ID_ENTRY + " TEXT UNIQUE NOT NULL,"
                        + Entrys.TITLE + " TEXT NOT NULL,"
                        + Entrys.CONTENT + " TEXT NOT NULL,"
                        + Entrys.A_DESCRIPTION + " TEXT NOT NULL,"
                        + Entrys.A_NAME + " TEXT NOT NULL,"
                        + Entrys.URL_ENTRY + " TEXT NOT NULL,"
                        + Entrys.URL_IMAGE + " TEXT NOT NULL)");

        // Registro ejemplo #1
        ContentValues valores = new ContentValues();
        
        valores.put(Entrys.ID_ENTRY, Entrys.generarIdAlquiler());
        valores.put(Entrys.TITLE, "Inmejorable vivienda en Cali");
        valores.put(Entrys.A_DESCRIPTION, "Cali");
        valores.put(Entrys.CONTENT, "Apartamento amplio, cerca a centros comerciales, garaje");
        valores.put(Entrys.A_NAME, "David Morales");
        valores.put(Entrys.URL_ENTRY, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto1.jpg");
        valores.put(Entrys.URL_IMAGE, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto1.jpg");
        db.insertOrThrow(Tablas.ENTRYS, null, valores);

        // Registro ejemplo #2
        valores.put(Entrys.ID_ENTRY, Entrys.generarIdAlquiler());
        valores.put(Entrys.TITLE, "3 habitaciones en Villa Real");
        valores.put(Entrys.A_DESCRIPTION, "Barranquilla");
        valores.put(Entrys.CONTENT, "Casa buena, bonita y barata");
        valores.put(Entrys.A_NAME, "David Morales");
        valores.put(Entrys.URL_ENTRY, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto1.jpg");
        valores.put(Entrys.URL_IMAGE, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto2.jpg");
        db.insertOrThrow(Tablas.ENTRYS, null, valores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.ENTRYS);
        } catch (SQLiteException e) {
            // Manejo de excepciones
        }
        onCreate(db);
    }
}
