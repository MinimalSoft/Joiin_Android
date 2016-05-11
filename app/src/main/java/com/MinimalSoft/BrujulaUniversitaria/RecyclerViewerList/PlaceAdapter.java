package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewerList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.List;



public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {

    private List<Place> items;

    public static class PlaceHolder extends RecyclerView.ViewHolder {
        // Campos de la lista
        public ImageView imagen;
        public TextView titulo;
        public TextView subtitulo;

        public PlaceHolder(View v) {
            super(v);
            titulo = (TextView) v.findViewById(R.id.place_titulo);
            subtitulo = (TextView) v.findViewById(R.id.place_subtitulo);
            imagen = (ImageView) v.findViewById(R.id.place_foto);
        }
    }

    public PlaceAdapter(List<Place> items) {
        this.items = items;
    }

    /*
    Aade una lista completa de items
     */
    public void addAll(List<Place> places){
        items.addAll(places);
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
    public PlaceHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_place, viewGroup, false);
        return new PlaceHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getIdImagen());
        viewHolder.titulo.setText(items.get(i).getTitulo());
        viewHolder.subtitulo.setText(items.get(i).getSubTitulo());
    }
}