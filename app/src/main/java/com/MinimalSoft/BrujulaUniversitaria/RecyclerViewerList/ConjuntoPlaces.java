package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewerList;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class ConjuntoPlaces {

    static final Place NEWS[] = {
            new Place("Chuchito Perez","500 metros de ti" , R.drawable.b1),
            new Place("Don quintin","310 metros de ti" , R.drawable.b2),
            new Place("Pata Negra","512 metros de ti" , R.drawable.b3),
            new Place("RoofTop","523 metros de ti" , R.drawable.b4),
            new Place("Choperia","665 metros de ti" , R.drawable.b5),
            new Place("Perla del Oriente ","876 metros de ti" , R.drawable.b6),

    };

    /**
     * Este mtodo retorna en una lista aleatoria basada en el
     * atributo LISTAS.
     *
     * El parmetro entero count es el tamao deseado de la lista
     * resultante
     */
    public static ArrayList<Place> randomList(int count) {
        Random random = new Random();
        HashSet<Place> items = new HashSet<Place>();

        // Restriccin de tamao
        count = Math.min(count, NEWS.length);

        while (items.size() < count) {
            items.add(NEWS[random.nextInt(NEWS.length)]);
        }

        return new ArrayList<Place>(items);
    }
}
