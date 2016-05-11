package com.MinimalSoft.BrujulaUniversitaria.RecyclerNews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.List;

/**
 * Creado por Hermosa Programacin
 */
public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewHolder> {

    private List<New> items;

    public static class NewHolder extends RecyclerView.ViewHolder {
        // Campos de la lista
        public TextView nombre;
        public TextView fecha;
        public TextView texto;

        public NewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.new_name);
            fecha = (TextView) v.findViewById(R.id.new_date);
            texto = (TextView) v.findViewById(R.id.new_text);
        }
    }

    public NewAdapter(List<New> items) {
        this.items = items;
    }

    /*
    Aade una lista completa de items
     */
    public void addAll(List<New> article){
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
    public NewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_new, viewGroup, false);
        return new NewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewHolder viewHolder, int i) {
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.fecha.setText(items.get(i).getFecha());
        viewHolder.texto.setText(items.get(i).getTexto());
    }
}