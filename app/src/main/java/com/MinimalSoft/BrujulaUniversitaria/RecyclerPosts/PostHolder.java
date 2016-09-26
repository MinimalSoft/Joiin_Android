package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import com.MinimalSoft.BrujulaUniversitaria.Models.LikesResponse;
import com.MinimalSoft.BrujulaUniversitaria.R;

import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.squareup.picasso.Picasso;
import com.like.OnLikeListener;
import com.like.LikeButton;

import android.net.Uri;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.v7.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostHolder extends RecyclerView.ViewHolder implements Callback<LikesResponse>, View.OnClickListener, OnLikeListener {
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
    protected Context context;
    protected int postID;
    protected int userID;
    private Interfaces minimalSoftAPI;
    private boolean disliked;
    private boolean liked;

    public PostHolder(View itemView) {
        super(itemView);

        STARS_COUNT = 5;
        liked = false;
        disliked = false;

        context = itemView.getContext();
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

        String urlAPI = context.getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        minimalSoftAPI = retrofit.create(Interfaces.class);

        reviewLayout.setOnClickListener(this);
        dislikeButton.setOnLikeListener(this);
        likeButton.setOnLikeListener(this);
    }

    protected void loadImage(String url) {
        Picasso.with(context).load(Uri.parse(url)).into(profileImage);
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

    /*----Callback Methods----*/

    @Override
    public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {

    }

    @Override
    public void onFailure(Call<LikesResponse> call, Throwable t) {

    }

    /*----OnClickListener methods----*/

    @Override
    public void onClick(View v) {
        //TODO: Place the proper action...
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
            minimalSoftAPI.like("like", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
            likesText.setText(String.valueOf(count + 1));
        } else {
            disliked = true;
            count = Integer.parseInt((String) dislikesText.getText());
            minimalSoftAPI.like("dislike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
            dislikesText.setText(String.valueOf(count + 1));
        }
    }

    private void aLikeRemoved(boolean opc) {
        int count;

        if (opc) {
            liked = false;
            count = Integer.parseInt((String) likesText.getText());
            minimalSoftAPI.like("removeLike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
            likesText.setText(String.valueOf(count - 1));
        } else {
            disliked = false;
            count = Integer.parseInt((String) dislikesText.getText());
            minimalSoftAPI.like("removeDislike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
            dislikesText.setText(String.valueOf(count - 1));
        }
    }
}