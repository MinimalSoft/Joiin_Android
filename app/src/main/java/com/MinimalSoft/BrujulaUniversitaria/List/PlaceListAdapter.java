package com.MinimalSoft.BrujulaUniversitaria.List;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

public class PlaceListAdapter extends ArrayAdapter<Place> {
    private List<Place> placeList;

    public PlaceListAdapter(Context context, int resource, List<Place> objects) {
        super(context, resource, objects);
        placeList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);

        TextView placeAddress = (TextView) convertView.findViewById(R.id.place_addressText);
        TextView ratingName = (TextView) convertView.findViewById(R.id.place_ratingText);
        TextView placeName = (TextView) convertView.findViewById(R.id.place_nameText);
        ImageView image = (ImageView) convertView.findViewById(R.id.place_image);

        Picasso.with(convertView.getContext()).load(placeList.get(position).getImageURL()).into(image);
        ratingName.setText(String.valueOf(placeList.get(position).getRating()));
        placeAddress.setText(placeList.get(position).getAddress());
        placeName.setText(placeList.get(position).getName());

        return convertView;
    }
}