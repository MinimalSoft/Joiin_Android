package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerNews.ConjuntoNews;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerNews.New;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerNews.NewAdapter;

import java.util.List;


public class NewsFeed extends Fragment {

    private RecyclerView recycler;
    private NewAdapter adapter;
    private RecyclerView.LayoutManager lManager;

    private SwipeRefreshLayout refreshLayout;

    private static final int CANTIDAD_ITEMS = 8;

    public static NewsFeed newInstance() {
        NewsFeed fragment = new NewsFeed();
        return fragment;
    }

    public NewsFeed() {
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
        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new NewAdapter(ConjuntoNews.randomList(CANTIDAD_ITEMS));
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

    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<New>> {

        //Code to download JSON BU WP

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List<New> doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return ConjuntoNews.randomList(CANTIDAD_ITEMS);
        }

        @Override
        protected void onPostExecute(List<New> result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adapter.clear();

            // Añadir elementos nuevos
            adapter.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);

        }

    }
}
