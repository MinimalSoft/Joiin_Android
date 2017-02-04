package com.MinimalSoft.BUniversitaria.Places;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Details.DetailsActivity;
import com.MinimalSoft.BUniversitaria.R;
import com.squareup.picasso.Picasso;

class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView placeImage;
    private TextView placeAddress;
    private TextView placeRating;
    private TextView placeName;
    private Context context;
    private Button button;
    private int placeID;

    public PlaceHolder(View itemView) {
        super(itemView);

        placeAddress = (TextView) itemView.findViewById(R.id.place_addressText);
        placeRating = (TextView) itemView.findViewById(R.id.place_ratingText);
        placeName = (TextView) itemView.findViewById(R.id.place_nameText);
        placeImage = (ImageView) itemView.findViewById(R.id.place_image);
        button = (Button) itemView.findViewById(R.id.place_button);
        context = itemView.getContext();

        button.setOnClickListener(this);
    }

    /*----OnClickListener methods----*/
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("PLACE_NAME", placeName.getText());
        intent.putExtra("PLACE_ID", placeID);
        context.startActivity(intent);
    }

    void setPlaceImage(String image) {
        String url = context.getString(R.string.server_api) + "/imagenes/" + image;
        Picasso.with(context).load(Uri.parse(url)).placeholder(R.drawable.default_image).into(placeImage);
    }

    public void setPlaceAddress(String address) {
        placeAddress.setText(address);
    }

    public void setPlaceRating(float rating) {
        placeRating.setText(String.valueOf(rating));
    }

    protected void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public void setPlaceName(String name) {
        placeName.setText(name);
    }
}