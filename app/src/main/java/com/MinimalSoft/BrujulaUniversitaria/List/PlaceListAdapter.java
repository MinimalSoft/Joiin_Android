package com.MinimalSoft.BrujulaUniversitaria.List;

import com.MinimalSoft.BrujulaUniversitaria.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlaceListAdapter extends ArrayAdapter<Place> {
    private List<Place> placeList;

    public PlaceListAdapter(Context context, int resource, List<Place> objects) {
        super(context, resource, objects);
        placeList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_place, parent, false);
        TextView placeAddress = (TextView) convertView.findViewById(R.id.item_addressText);
        TextView placeName = (TextView) convertView.findViewById(R.id.item_nameText);
        placeAddress.setText(placeList.get(position).getAddress());
        placeName.setText(placeList.get(position).getName());
        return convertView;
    }
}
