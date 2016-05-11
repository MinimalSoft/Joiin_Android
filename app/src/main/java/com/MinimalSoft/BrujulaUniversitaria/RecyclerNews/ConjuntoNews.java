package com.MinimalSoft.BrujulaUniversitaria.RecyclerNews;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Creado por Hermosa Programacin
 */
public class ConjuntoNews{

    static final New NEWS[] = {
            new New("Dave Morales","mar 23 a las 3:00 pm","Las 10 mejores herramientas de analiticas"),
            new New("Hugo Torres","jue 25 a las 7:00 pm","Los mejores servicios de entrega en Cali"),
            new New("Juan Perez","mar 23 a las 8:00 am","Los navegadores mas rpidos"),
            new New("Patricia Kaar","mar 23 a las 1:00 pm","Las aplicaciones mas populares de chat"),
            new New("Valentina Fuentes","mar 23 a las 8:00 am","Este es un ejemplo de post para el newsfeed de BU"),
            new New("Ricarddo Hernandez","mar 23 a las 12:00 pm","Los pases mas ricos del mundo"),
            new New("Carlos Campos","mar 23 a las 9:00 am","analiticas"),
            new New("Jose Miranda","mar 23 a las 6:00 pm","Las aplicaciones mas populares de chat"),
    };

    /**
     * Este mtodo retorna en una lista aleatoria basada en el
     * atributo LISTAS.
     *
     * El parmetro entero count es el tamao deseado de la lista
     * resultante
     */
    public static ArrayList<New> randomList(int count) {
        Random random = new Random();
        HashSet<New> items = new HashSet<New>();

        // Restriccin de tamao
        count = Math.min(count, NEWS.length);

        while (items.size() < count) {
            items.add(NEWS[random.nextInt(NEWS.length)]);
        }

        return new ArrayList<New>(items);
    }
}
