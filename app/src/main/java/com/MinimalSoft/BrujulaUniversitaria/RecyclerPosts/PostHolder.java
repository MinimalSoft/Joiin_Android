package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.like.OnLikeListener;
import com.like.LikeButton;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.v7.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnLikeListener {
    private final int STARS_COUNT;
    protected CircleImageView profileImage;
    protected RelativeLayout reviewLayout;
    protected LikeButton dislikeButton;
    protected LikeButton likeButton;
    protected TextView placeNameText;
    protected TextView userNameText;
    protected TextView dateTimeText;
    protected TextView dislikesText;
    protected TextView reviewText;
    protected TextView likesText;
    protected ImageView stars[];
    private boolean disliked;
    private boolean liked;

    public PostHolder(View itemView) {
        super(itemView);
        liked = false;
        disliked = false;
        STARS_COUNT = 5;

        stars = new ImageView[STARS_COUNT];
        stars[0] = (ImageView) itemView.findViewById(R.id.post_star_1);
        stars[1] = (ImageView) itemView.findViewById(R.id.post_star_2);
        stars[2] = (ImageView) itemView.findViewById(R.id.post_star_3);
        stars[3] = (ImageView) itemView.findViewById(R.id.post_star_4);
        stars[4] = (ImageView) itemView.findViewById(R.id.post_star_5);

        likesText = (TextView) itemView.findViewById(R.id.post_textLikes);
        reviewText = (TextView) itemView.findViewById(R.id.post_textReview);
        userNameText = (TextView) itemView.findViewById(R.id.post_textName);
        placeNameText = (TextView) itemView.findViewById(R.id.post_textPlace);
        likeButton = (LikeButton) itemView.findViewById(R.id.post_likeButton);
        dislikesText = (TextView) itemView.findViewById(R.id.post_textDislikes);
        dateTimeText = (TextView) itemView.findViewById(R.id.post_textDateTime);
        profileImage = (CircleImageView) itemView.findViewById(R.id.post_image);
        dislikeButton = (LikeButton) itemView.findViewById(R.id.post_dislikeButton);
        reviewLayout = (RelativeLayout) itemView.findViewById(R.id.post_reviewLayout);

        reviewLayout.setOnClickListener(this);
        dislikeButton.setOnLikeListener(this);
        likeButton.setOnLikeListener(this);
    }

    protected void setStars(int rating) {
        for (int i = 0; i < STARS_COUNT; i++) {
            if (i <= (rating - 1)) {
                stars[i].setImageResource(R.drawable.star_on);
            } else {
                stars[i].setImageResource(R.drawable.star_off);
            }
        }
    }

    /*----OnClickListener methods----*/

    @Override
    public void onClick(View v) {
        Log.i(getClass().getSimpleName(), "Looking up at the place on the map...");
    }

    /*----OnLikeListener methods----*/

    @Override
    public void liked(LikeButton button) {
        switch (button.getId()) {
            case R.id.post_likeButton:
                aLikeAdded(true);
                if (disliked) {
                    aLikeRemoved(false);
                    dislikeButton.setLiked(false);
                }
                break;

            case R.id.post_dislikeButton:
                aLikeAdded(false);
                if (liked) {
                    aLikeRemoved(true);
                    likeButton.setLiked(false);
                }
                break;
        }
    }

    @Override
    public void unLiked(LikeButton button) {
        switch (button.getId()) {
            case R.id.post_likeButton:
                aLikeRemoved(true);
                break;

            case R.id.post_dislikeButton:
                aLikeRemoved(false);
                break;
        }
    }

    private void aLikeAdded(boolean opc) {
        int count;

        if (opc) {
            liked = true;
            count = Integer.parseInt((String) likesText.getText());
            likesText.setText(String.valueOf(count + 1));
        } else {
            disliked = true;
            count = Integer.parseInt((String) dislikesText.getText());
            dislikesText.setText(String.valueOf(count + 1));
        }
    }

    private void aLikeRemoved(boolean opc) {
        int count;

        if (opc) {
            liked = false;
            count = Integer.parseInt((String) likesText.getText());
            likesText.setText(String.valueOf(count - 1));
        } else {
            disliked = false;
            count = Integer.parseInt((String) dislikesText.getText());
            dislikesText.setText(String.valueOf(count - 1));
        }
    }
}