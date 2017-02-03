package com.MinimalSoft.BUniversitaria.PlacesList;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Models.PlaceData;
import com.MinimalSoft.BUniversitaria.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class PlaceListAdapter extends ArrayAdapter<PlaceData> {
    private List<PlaceData> placeList;
    private final String serverURL;

    PlaceListAdapter(Context context, int resource, List<PlaceData> objects) {
        super(context, resource, objects);
        serverURL = context.getString(R.string.server_api) + "/imagenes/";
        placeList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);

            TextView placeAddress = (TextView) convertView.findViewById(R.id.place_addressText);
            TextView ratingName = (TextView) convertView.findViewById(R.id.place_ratingText);
            TextView placeName = (TextView) convertView.findViewById(R.id.place_nameText);
            ImageView image = (ImageView) convertView.findViewById(R.id.place_image);

            String url = serverURL + placeList.get(position).getImage();
            String name = placeList.get(position).getPlaceName();
            String rating = placeList.get(position).getStars();
            String address = placeList.get(position).getStreet() +
                    " # " + placeList.get(position).getNumber() +
                    ", " + placeList.get(position).getNeighborhood();

            Picasso.with(convertView.getContext()).load(Uri.parse(url)).placeholder(R.drawable.default_image).into(image);
            ratingName.setText(String.valueOf(rating));
            placeAddress.setText(address);
            placeName.setText(name);
        }

        return convertView;
    }

    @Nullable
    @Override
    public PlaceData getItem(int position) {
        return placeList.get(position);
    }
}