package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BrujulaUniversitaria.Models.LikesResponse;
import com.MinimalSoft.BrujulaUniversitaria.R;

import com.squareup.picasso.Picasso;
import com.like.OnLikeListener;
import com.like.LikeButton;

import android.net.Uri;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class PostHolder extends RecyclerView.ViewHolder implements Callback<LikesResponse>, View.OnClickListener, OnLikeListener {
    private final int STARS_COUNT;
    protected int postID;
    protected int userID;
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
    private Interfaces minimalSoftAPI;
    private boolean dislikeOff;
    private boolean dislikeOn;
    private boolean likeOff;
    private boolean likeOn;
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

        String urlAPI = context.getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        minimalSoftAPI = retrofit.create(Interfaces.class);

        reviewLayout.setOnClickListener(this);
        dislikeButton.setOnLikeListener(this);
        likeButton.setOnLikeListener(this);
    }

    /*----Callback Methods----*/

    @Override
    public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {
        if (response.isSuccessful()) {
            onLikeSucceed();
        } else {
            onLikeFailed();
        }
    }

    @Override
    public void onFailure(Call<LikesResponse> call, Throwable t) {
        onLikeFailed();
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
                if (disliked) {
                    dislikeOff = true;
                    minimalSoftAPI.like("removeDislike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
                }

                likeOn = true;
                minimalSoftAPI.like("like", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
                break;

            case R.id.post_dislikeButton:
                if (liked) {
                    likeOff = true;
                    minimalSoftAPI.like("removeLike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
                }

                dislikeOn = true;
                minimalSoftAPI.like("dislike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
                break;
        }
    }

    @Override
    public void unLiked(LikeButton button) {
        switch (button.getId()) {
            case R.id.post_likeButton:
                likeOff = true;
                minimalSoftAPI.like("removeLike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
                break;

            case R.id.post_dislikeButton:
                dislikeOff = true;
                minimalSoftAPI.like("removeDislike", String.valueOf(userID), String.valueOf(postID)).enqueue(this);
                break;
        }
    }

    protected void setTypeColors(int placeType) {
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

    protected void setStars(int rating) {
        for (int i = 0; i < STARS_COUNT; i++) {
            if (i <= (rating - 1)) {
                stars[i].setImageResource(R.drawable.star_on);
            } else {
                stars[i].setImageResource(R.drawable.star_off);
            }
        }
    }

    protected void loadImage(String url) {
        Picasso.with(context).load(Uri.parse(url)).error(R.drawable.default_profile).into(profileImage);
    }

    private void onLikeSucceed() {
        int count;

        if (dislikeOff) {
            dislikeOff = false;
            count = Integer.parseInt((String) dislikesText.getText());
            dislikesText.setText(String.valueOf(count - 1));
            dislikeButton.setLiked(false);
            disliked = false;
        } else if (dislikeOn) {
            dislikeOn = false;
            count = Integer.parseInt((String) dislikesText.getText());
            dislikesText.setText(String.valueOf(count + 1));
            dislikeButton.setLiked(true);
            disliked = true;
        } else if (likeOff) {
            likeOff = false;
            count = Integer.parseInt((String) likesText.getText());
            likesText.setText(String.valueOf(count - 1));
            likeButton.setLiked(false);
            liked = false;
        } else if (likeOn) {
            likeOn = false;
            count = Integer.parseInt((String) likesText.getText());
            likesText.setText(String.valueOf(count + 1));
            likeButton.setLiked(true);
            liked = true;
        }
    }

    private void onLikeFailed() {
        if (dislikeOff) {
            dislikeOff = false;
            dislikeButton.setLiked(true);
        } else if (dislikeOn) {
            dislikeOn = false;
            dislikeButton.setLiked(false);
        } else if (likeOff) {
            likeOff = false;
            likeButton.setLiked(true);
        } else if (likeOn) {
            likeOn = false;
            likeButton.setLiked(false);
        }
    }
}