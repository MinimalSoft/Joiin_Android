package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import com.MinimalSoft.BrujulaUniversitaria.R;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

public class PostHolder extends RecyclerView.ViewHolder {
    protected Button button;
    protected ImageView image;
    protected ImageView thumbnail;

    protected TextView date;
    protected TextView review;
    protected TextView userName;
    protected TextView placeName;
    protected TextView placeRating;
    protected TextView placeAddress;

    public PostHolder(View itemView) {
        super(itemView);

        button = (Button) itemView.findViewById(R.id.post_button);
        image = (ImageView) itemView.findViewById(R.id.post_image);
        thumbnail = (ImageView) itemView.findViewById(R.id.post_thumbnail);

        date = (TextView) itemView.findViewById(R.id.post_textDate);
        review = (TextView) itemView.findViewById(R.id.post_textReview);
        userName = (TextView) itemView.findViewById(R.id.post_textName);
        placeName = (TextView) itemView.findViewById(R.id.post_textPlace);
        placeRating = (TextView) itemView.findViewById(R.id.post_textRating);
        placeAddress = (TextView) itemView.findViewById(R.id.post_textAddress);
    }
}