package com.MinimalSoft.BrujulaUniversitaria.RecyclerEntrys;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Creado por Hermosa Programacin
 */
public class ConjuntoArticles {

    static final Article ARTICLES[] = {
            new Article("Las 10 mejores herramientas de analiticas", R.drawable.post1),
            new Article("Los mejores servicios de entrega en Cali", R.drawable.post2),
            new Article("Los navegadores mas rpidos", R.drawable.post3),
            new Article("Las aplicaciones mas populares de chat", R.drawable.post4),
            new Article("Lista de las joyas mas deseadas por las mujeres", R.drawable.post5),
            new Article("Los pases mas ricos del mundo", R.drawable.post6),
    };

    /**
     * Este mtodo retorna en una lista aleatoria basada en el
     * atributo LISTAS.
     *
     * El parmetro entero count es el tamao deseado de la lista
     * resultante
     */
    public static ArrayList<Article> randomList(int count) {
        Random random = new Random();
        HashSet<Article> items = new HashSet<Article>();

        // Restriccin de tamao
        count = Math.min(count, ARTICLES.length);

        while (items.size() < count) {
            items.add(ARTICLES[random.nextInt(ARTICLES.length)]);
        }

        return new ArrayList<Article>(items);
    }
}
