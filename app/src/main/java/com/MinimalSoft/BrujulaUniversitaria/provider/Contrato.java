package com.MinimalSoft.BrujulaUniversitaria.provider;

import android.net.Uri;

import java.util.UUID;

/**
 * Contrato con la estructura de la base de datos y forma de las URIs
 */
public class Contrato {

    interface ColumnasEntrys {

        String ID_ENTRY = "idEntry";
        String TITLE = "title";
        String CONTENT = "content";
        String A_DESCRIPTION = "aDescription";
        String A_NAME = "aName";
        String URL_ENTRY = "urlEntry";
        String URL_IMAGE = "urlImage";

    }


    // Autoridad del Content Provider
    public final static String AUTORIDAD = "com.MinimalSoft.BrujulaUniversitaria";

    // Uri base
    public final static Uri URI_CONTENIDO_BASE = Uri.parse("content://" + AUTORIDAD);


    /**
     * Controlador de la tabla "entrys"
     */
    public static class Entrys implements ColumnasEntrys {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_ENTRYS).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_ENTRYS;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_ENTRYS;


        /**
         * Construye una {@link Uri} para el {@link #ID_ENTRY} solicitado.
         */
        public static Uri construirUriEntrys(String idEntry) {
            return URI_CONTENIDO.buildUpon().appendPath(idEntry).build();
        }

        public static String generarIdAlquiler() {
            return "E-" + UUID.randomUUID();
        }

        public static String obtenerIdAlquiler(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    // Recursos
    public final static String RECURSO_ENTRYS = "entrys";

}
