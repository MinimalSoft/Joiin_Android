package com.MinimalSoft.BrujulaUniversitaria.RecyclerEntrys;

import android.support.v7.widget.RecyclerView;
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
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ArticleHolder> {

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
    public void addAll(List<Article> article){
        items.addAll(article);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
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
                .inflate(R.layout.item_entry, viewGroup, false);
        return new ArticleHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getIdImagen());
        viewHolder.titulo.setText(items.get(i).getTitulo());
    }
}