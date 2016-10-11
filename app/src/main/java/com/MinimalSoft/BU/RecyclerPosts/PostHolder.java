package com.MinimalSoft.BU.RecyclerPosts;

import com.MinimalSoft.BU.Maps.DetailsActivity;
import com.MinimalSoft.BU.R;

import com.squareup.picasso.Picasso;
import com.like.LikeButton;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final int STARS_COUNT;
    protected CircleImageView profileImage;
    protected LinearLayout reviewLayout;
    protected LikeButton dislikeButton;
    protected LikeButton likeButton;
    protected TextView placeNameText;
    protected TextView userNameText;
    protected TextView dateTimeText;
    protected TextView dislikesText;
    protected TextView reviewText;
    protected TextView likesText;
    protected ImageView stars[];
    protected Context context;
    protected View bottomLine;

    PostHolder(View itemView) {
        super(itemView);

        STARS_COUNT = 5;
        context = itemView.getContext();
        stars = new ImageView[STARS_COUNT];
        stars[0] = (ImageView) itemView.findViewById(R.id.star_1);
        stars[1] = (ImageView) itemView.findViewById(R.id.star_2);
        stars[2] = (ImageView) itemView.findViewById(R.id.star_3);
        stars[3] = (ImageView) itemView.findViewById(R.id.star_4);
        stars[4] = (ImageView) itemView.findViewById(R.id.star_5);

        bottomLine = itemView.findViewById(R.id.post_line);
        likesText = (TextView) itemView.findViewById(R.id.post_textLikes);
        reviewText = (TextView) itemView.findViewById(R.id.post_textReview);
        userNameText = (TextView) itemView.findViewById(R.id.post_textName);
        placeNameText = (TextView) itemView.findViewById(R.id.post_textPlace);
        likeButton = (LikeButton) itemView.findViewById(R.id.post_likeButton);
        dislikesText = (TextView) itemView.findViewById(R.id.post_textDislikes);
        dateTimeText = (TextView) itemView.findViewById(R.id.post_textDateTime);
        profileImage = (CircleImageView) itemView.findViewById(R.id.post_image);
        dislikeButton = (LikeButton) itemView.findViewById(R.id.post_dislikeButton);
        reviewLayout = (LinearLayout) itemView.findViewById(R.id.post_reviewLayout);

        reviewLayout.setOnClickListener(this);
    }

    /*----OnClickListener methods----*/

    @Override
    public void onClick(View v) {
        //Todo: Place proper action...
    }

    void setTypeColors(int placeType) {
        int color;

        switch (placeType) {
            case 1:
                color = ContextCompat.getColor(context, R.color.featured);
                break;
            case 2:
                color = ContextCompat.getColor(context, R.color.bars);
                break;
            case 3:
                color = ContextCompat.getColor(context, R.color.food);
                break;
            case 4:
                color = ContextCompat.getColor(context, R.color.gyms);
                break;
            case 5:
                color = ContextCompat.getColor(context, R.color.supplies);
                break;
            case 6:
                color = ContextCompat.getColor(context, R.color.residences);
                break;
            case 7:
                color = ContextCompat.getColor(context, R.color.jobs);
                break;

            default:
                color = ContextCompat.getColor(context, R.color.iron);
        }

        bottomLine.setBackgroundColor(color);
        placeNameText.setTextColor(color);
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
        Picasso.with(context).load(Uri.parse(url)).placeholder(R.drawable.default_profile).into(profileImage);
    }

    void setLikes() {
        // Todo: Place code for the user likes
        dislikeButton.setLiked(false);
        likeButton.setLiked(false);
    }
}