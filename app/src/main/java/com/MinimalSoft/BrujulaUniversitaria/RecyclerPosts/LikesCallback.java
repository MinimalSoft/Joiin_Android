package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.Models.LikesResponse;
import com.like.LikeButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LikesCallback implements Callback<LikesResponse> {
    private final LikeButton likeButton;
    private final TextView textView;
    private final boolean likeEvent;

    /**
     * @param likeEvent Set to true if it's the Add Like event or false otherwise.
     */
    public LikesCallback(final boolean likeEvent, final LikeButton likeButton, final TextView textView) {
        this.likeButton = likeButton;
        this.likeEvent = likeEvent;
        this.textView = textView;
    }

    @Override
    public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {
        if (response.isSuccessful()) {
            int count = Integer.parseInt((String) textView.getText());

            if (likeEvent) {
                textView.setText(String.valueOf(count + 1));
                likeButton.setLiked(true);
            } else {
                textView.setText(String.valueOf(count - 1));
                likeButton.setLiked(false);
            }
        } else {
            onLikeFailed();
        }
    }

    @Override
    public void onFailure(Call<LikesResponse> call, Throwable t) {
        onLikeFailed();
    }

    private void onLikeFailed() {
        if (likeEvent) {
            likeButton.setLiked(false);
        } else {
            likeButton.setLiked(true);
        }
    }
}