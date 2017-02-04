package com.MinimalSoft.BUniversitaria.Details;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

class ReviewHolder extends RecyclerView.ViewHolder {
    private final int STARS_COUNT;
    private CircleImageView imageView;
    private TextView dateTimeText;
    private TextView reviewText;
    private TextView nameText;
    private ImageView stars[];
    private Context context;

    public ReviewHolder(View itemView) {
        super(itemView);

        STARS_COUNT = 5;
        context = itemView.getContext();
        stars = new ImageView[STARS_COUNT];
        stars[0] = (ImageView) itemView.findViewById(R.id.star_1);
        stars[1] = (ImageView) itemView.findViewById(R.id.star_2);
        stars[2] = (ImageView) itemView.findViewById(R.id.star_3);
        stars[3] = (ImageView) itemView.findViewById(R.id.star_4);
        stars[4] = (ImageView) itemView.findViewById(R.id.star_5);

        nameText = (TextView) itemView.findViewById(R.id.review_textName);
        reviewText = (TextView) itemView.findViewById(R.id.review_textReview);
        imageView = (CircleImageView) itemView.findViewById(R.id.review_image);
        dateTimeText = (TextView) itemView.findViewById(R.id.review_textDateTime);
    }

    void setStars(int rating) {
        for (int i = 0; i < STARS_COUNT; i++) {
            if (i <= (rating - 1)) {
                stars[i].setImageResource(R.drawable.star_on);
            } else {
                stars[i].setImageResource(R.drawable.star_off);
            }
        }
    }

    void loadImage(String url) {
        Picasso.with(context).load(Uri.parse(url)).placeholder(R.drawable.default_profile).into(imageView);
    }

    protected void setDate(String date) {
        dateTimeText.setText(date.replace(" ", " a las "));
    }

    protected void setReview(String text) {
        reviewText.setText(text);
    }

    protected void setName(String name) {
        nameText.setText(name);
    }
}