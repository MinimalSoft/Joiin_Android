package com.MinimalSoft.BU.RecyclerPosts;

import com.MinimalSoft.BU.Models.LikesResponse;
import com.MinimalSoft.BU.Utilities.Interfaces;
import com.MinimalSoft.BU.R;

import com.like.LikeButton;

import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class LikeCallback implements Callback<LikesResponse> {
    private Interfaces minimalSoftAPI;
    private final LikeButton otherButton;
    private final LikeButton likeButton;
    private final TextView textView;
    private final int postID;
    private final int userID;
    private boolean flag;
    private boolean on;

    LikeCallback(LikeButton otherButton, LikeButton likeButton, TextView textView, int postID, int userID) {
        String urlAPI = likeButton.getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        minimalSoftAPI = retrofit.create(Interfaces.class);

        this.otherButton = otherButton;
        this.likeButton = likeButton;
        this.textView = textView;
        this.postID = postID;
        this.userID = userID;
    }

    /*----Callback methods----*/

    @Override
    public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {
        likeButton.setEnabled(true);
        otherButton.setEnabled(true);

        if (response.isSuccessful()) {
            int count = Integer.parseInt((String) textView.getText());

            if (flag) {
                textView.setText(String.valueOf(count + 1));
                likeButton.setLiked(true);
                on = true;
            } else {
                textView.setText(String.valueOf(count - 1));
                likeButton.setLiked(false);
                on = false;
            }
        } else {
            onLikeFailed();
        }
    }

    @Override
    public void onFailure(Call<LikesResponse> call, Throwable t) {
        onLikeFailed();
    }

    void addLike(String action) {
        flag = true;
        likeButton.setEnabled(false);
        otherButton.setEnabled(false);
        minimalSoftAPI.like(action, String.valueOf(userID), String.valueOf(postID)).enqueue(this);
    }

    void removeLike(String action) {
        flag = false;
        likeButton.setEnabled(false);
        otherButton.setEnabled(false);
        minimalSoftAPI.like(action, String.valueOf(userID), String.valueOf(postID)).enqueue(this);
    }

    void onLikeFailed() {
        if (flag) {
            likeButton.setLiked(false);
        } else {
            likeButton.setLiked(true);
        }

        otherButton.setEnabled(true);
        likeButton.setEnabled(true);
    }

    public boolean isOn() {
        return on;
    }
}