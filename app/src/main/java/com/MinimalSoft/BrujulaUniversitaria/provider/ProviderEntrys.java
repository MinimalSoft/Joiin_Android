package com.MinimalSoft.BrujulaUniversitaria.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.MinimalSoft.BrujulaUniversitaria.provider.BaseDatos.Tablas;
import com.MinimalSoft.BrujulaUniversitaria.provider.Contrato.Entrys;


/**
 * {@link ContentProvider} que encapsula el acceso a la base de datos de ENTRYS
 */
public class ProviderEntrys extends ContentProvider {

    // Comparador de URIs
    public static final UriMatcher uriMatcher;

    // Casos
    public static final int ENTRYS = 100;
    public static final int ENTRYS_ID = 101;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contrato.AUTORIDAD, "alquileres", ENTRYS);
        uriMatcher.addURI(Contrato.AUTORIDAD, "alquileres/*", ENTRYS_ID);
    }

    private BaseDatos bd;
    private ContentResolver resolver;


    @Override
    public boolean onCreate() {
        bd = new BaseDatos(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ENTRYS:
                return Entrys.MIME_COLECCION;
            case ENTRYS_ID:
                return Entrys.MIME_RECURSO;
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase db = bd.getWritableDatabase();
        // Comparar Uri
        int match = uriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case ENTRYS:
                // Consultando todos los registros
                c = db.query(Tablas.ENTRYS, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(resolver, Entrys.URI_CONTENIDO);
                break;
            case ENTRYS_ID:
                // Consultando un solo registro basado en el Id del Uri
                String idEntrys = Entrys.obtenerIdAlquiler(uri);
                c = db.query(Tablas.ENTRYS, projection,
                        Entrys.ID_ENTRY + "=" + "\'" + idEntrys + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(resolver, uri);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
