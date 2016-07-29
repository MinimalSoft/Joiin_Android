package com.MinimalSoft.BrujulaUniversitaria.Transport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.MinimalSoft.BrujulaUniversitaria.Models.Transport_Stop;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DataBase (context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<Transport_Stop> getStops(String sentence) {
        List<Transport_Stop> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sentence, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transport_Stop stop = new Transport_Stop(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getString(8));

            list.add(stop);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getRoutes(String sentence) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sentence, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add("Línea "+cursor.getString(0)+ "\n" +cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

}

