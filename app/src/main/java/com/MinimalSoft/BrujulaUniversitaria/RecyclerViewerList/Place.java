package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewerList;

public class Place {
    private String titulo;
    private String subtitulo;
    private int idImagen;


    public Place(String titulo, String subtitulo, int idImage) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.idImagen = idImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return titulo;
    }

    public void setSubTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

}
