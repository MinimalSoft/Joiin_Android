package com.MinimalSoft.BU.Models;

/**
 * Created by David on 24/07/2016.
 * Esta clase contiene todos los campos de todas las posibles respuestas
 * JSON del API
 */
public class Data_Reviews {

    //TODO: Agregar los campos para el getPromos

    //Fields GetReviews
    private String name;
    private String text;
    private String stars;
    private String date;


    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The stars
     */
    public String getStars() {
        return stars;
    }

    /**
     * @param stars The stars
     */
    public void setStars(String stars) {
        this.stars = stars;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }


}

