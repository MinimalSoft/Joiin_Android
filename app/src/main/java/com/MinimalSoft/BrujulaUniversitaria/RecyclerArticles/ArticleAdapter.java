package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import java.util.List;
import java.util.ArrayList;
import android.widget.Toast;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Articles;

public class ArticleAdapter extends RecyclerView.Adapter <ArticleHolder> {
    private SwipeRefreshLayout refreshAnimation;
    private List <Article> items;
    private Articles articlesTab;
    private boolean flag;

    public ArticleAdapter(final Articles articles, final SwipeRefreshLayout swipeRefreshLayout) {
        refreshAnimation = swipeRefreshLayout;
        items = new ArrayList<>();
        articlesTab = articles;
        flag = false;
    }

    protected void addNew(List <Article> items) {
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

    protected void removeOld() {
        items.clear();
        this.notifyDataSetChanged();
    }

    public void updateData () {
        if (!flag) {
            flag = true;
            new ArticlesDataCollector(this).execute();
        }
    }

    public void stopRefreshingAnimation(Boolean bool) {
        if (!bool) {
            Toast.makeText(articlesTab.getContext(), "Error al cargar los artículos.", Toast.LENGTH_SHORT).show();
        }

        refreshAnimation.setRefreshing(false);
        flag = false;
    }

    /* Adapter implemented methods*/

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleHolder holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.image.setImageBitmap(items.get(position).getImage());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = items.get(position).getTitle();
                String link = items.get(position).getLink();
                articlesTab.showArticle(title, link);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

/*import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.List;

/**
 * Creado por Hermosa Programacin
 */
/*public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ArticleHolder> {

    private List<Article> items;

    public static class ArticleHolder extends RecyclerView.ViewHolder {
        // Campos de la lista
        public ImageView imagen;
        public TextView titulo;

        public ArticleHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.entry_foto);
            titulo = (TextView) v.findViewById(R.id.entry_titulo);

        }
    }

    public EntryAdapter(List<Article> items) {
        this.items = items;
    }

    /*
    Aade una lista completa de items
     */
    /*public void addAll(List<Article> article){
        items.addAll(article);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    /*public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_article, viewGroup, false);
        return new ArticleHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getIdImagen());
        viewHolder.titulo.setText(items.get(i).getTitulo());
    }
}*/