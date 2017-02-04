package com.MinimalSoft.BUniversitaria.Places;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BUniversitaria.Models.PlaceData;
import com.MinimalSoft.BUniversitaria.R;

class PlacesListAdapter extends RecyclerView.Adapter<PlaceHolder> {
    private PlaceData[] places;

    public PlacesListAdapter(PlaceData[] places) {
        this.places = places;
    }

    /*----------------------- Adapter Methods ------------------------*/
    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_place, parent, false);
        return new PlaceHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        String address = places[position].getStreet() + " # " + places[position].getNumber() + ", " + places[position].getNeighborhood();

        holder.setPlaceImage(places[position].getImage());
        holder.setPlaceName(places[position].getPlaceName());
        holder.setPlaceRating(places[position].getStars());
        holder.setPlaceID(places[position].getIdPlace());
        holder.setPlaceAddress(address);
    }

    @Override
    public int getItemCount() {
        return places.length;
    }
}