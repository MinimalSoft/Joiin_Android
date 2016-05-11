package com.MinimalSoft.BrujulaUniversitaria.RecyclerEntrys;

public class Article {
    private String titulo;
    private int idImagen;

    public Article(String titulo, int idImagen) {
        this.titulo = titulo;
        this.idImagen = idImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }
}
