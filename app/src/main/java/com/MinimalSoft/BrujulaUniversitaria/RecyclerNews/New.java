package com.MinimalSoft.BrujulaUniversitaria.RecyclerNews;

public class New {
    private String nombre;
    private String fecha;
    private String texto;


    public New(String nombre,String fecha,String texto) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
