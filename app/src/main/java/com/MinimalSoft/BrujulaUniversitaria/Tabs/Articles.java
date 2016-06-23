package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.WebActivity;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles.EntryAdapter;

public class Articles extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View inflatedView;

    private EntryAdapter entryAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_articles, container, false);

            recyclerView = (RecyclerView) inflatedView.findViewById(R.id.articles_recycler);
            swipeRefreshLayout = (SwipeRefreshLayout) inflatedView.findViewById(R.id.articles_swipe_refresh);

            entryAdapter = new EntryAdapter(this, swipeRefreshLayout);
            layoutManager = new LinearLayoutManager(inflatedView.getContext());

            recyclerView.setAdapter(entryAdapter);
            recyclerView.setLayoutManager(layoutManager);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeResources(R.color.s1, R.color.s2, R.color.s3);

            entryAdapter.updateData();
        }

        return inflatedView;
    }

    public void showArticle (String title, String link) {
        Intent intent = new Intent(this.getActivity(), WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("LINK", link);
        this.startActivity(intent);
        //this.startActivity(new Intent(this.getActivity(), WebActivity.class));
        //Log.i(this.getClass().getSimpleName(), "count: " + )
        //Toast.makeText(this.getContext(), title, Toast.LENGTH_SHORT).show();
    }

    /* SwipeRefreshLayout implemented methods */

    @Override
    public void onRefresh() {
        entryAdapter.updateData();
    }
}

/*import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerEntrys.Article;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerEntrys.ConjuntoArticles;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerEntrys.EntryAdapter;

import java.util.List;


public class Articles extends Fragment {

    private RecyclerView recycler;
    private EntryAdapter adapter;
    private RecyclerView.LayoutManager lManager;

    private SwipeRefreshLayout refreshLayout;

    private static final int CANTIDAD_ITEMS = 8;

    public static Articles newInstance() {
        Articles fragment = new Articles();
        return fragment;
    }

    public Articles() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_articles, container, false);

        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new EntryAdapter(ConjuntoArticles.randomList(CANTIDAD_ITEMS));
        recycler.setAdapter(adapter);

        // Obtener el refreshLayout
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);

        // Seteamos los colores que se usarán a lo largo de la animación
        refreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s3
        );

        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );

        return rootView;
    }

    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<Article>> {

        //Code to download JSON BU WP

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List<Article> doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return ConjuntoArticles.randomList(CANTIDAD_ITEMS);
        }

        @Override
        protected void onPostExecute(List<Article> result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adapter.clear();

            // Añadir elementos nuevos
            adapter.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }
}*/
