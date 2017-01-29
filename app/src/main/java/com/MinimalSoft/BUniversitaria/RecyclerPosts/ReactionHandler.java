package com.MinimalSoft.BUniversitaria.RecyclerPosts;

import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Models.ReactionResponse;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;
import com.like.LikeButton;
import com.like.OnLikeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ReactionHandler implements OnLikeListener, Callback<ReactionResponse> {
    private final Interfaces minimalSoftAPI;
    private final int REACTIONS_COUNT;
    private final int NO_REACTION = 0;

    private LikeButton[] buttons;
    private TextView[] textViews;

    private boolean flag;
    private int userID;
    private int postID;
    private int index;

    public ReactionHandler(LikeButton[] buttons, TextView[] textViews) {
        String urlAPI = buttons[0].getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        minimalSoftAPI = retrofit.create(Interfaces.class);

        this.REACTIONS_COUNT = buttons.length;
        this.textViews = textViews;
        this.buttons = buttons;
    }

    /*------ OnLikeListener Methods ------*/
    @Override
    public void liked(LikeButton likeButton) {
        int idx = findSource(likeButton);
        int count;

        if (flag && idx != index) {
            count = Integer.parseInt(String.valueOf(textViews[index].getText()));
            textViews[index].setText(String.valueOf(--count));
            buttons[index].setLiked(false);
        }

        flag = true;
        index = idx;
        disableButtons();
        minimalSoftAPI.reaction("setReaction", String.valueOf(userID), String.valueOf(postID), String.valueOf(index + 1)).enqueue(this);
        count = Integer.parseInt(String.valueOf(textViews[index].getText()));
        textViews[index].setText(String.valueOf(++count));
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        flag = false;
        disableButtons();
        index = findSource(likeButton);
        minimalSoftAPI.reaction("setReaction", String.valueOf(userID), String.valueOf(postID), String.valueOf(NO_REACTION)).enqueue(this);
        int count = Integer.parseInt(String.valueOf(textViews[index].getText()));
        textViews[index].setText(String.valueOf(--count));
    }

    /*------------------------------- Callback Methods -------------------------------*/
    @Override
    public void onResponse(Call<ReactionResponse> call, Response<ReactionResponse> response) {
        if (!response.isSuccessful()) {
            onFailure(call, null);
        }

        enableButtons();
    }

    @Override
    public void onFailure(Call<ReactionResponse> call, Throwable t) {
        int count = Integer.parseInt(String.valueOf(textViews[index].getText()));

        if (flag) {
            buttons[index].setLiked(false);
            textViews[index].setText(String.valueOf(--count));
        } else {
            buttons[index].setLiked(true);
            textViews[index].setText(String.valueOf(++count));
        }

        enableButtons();
    }

    protected void setData(int userID, int postID, int reaction, int amounts[]) {
        for (int i = 0; i < REACTIONS_COUNT; i++) {
            if (reaction == (i + 1)) {
                index = i;
                flag = true;
                buttons[i].setLiked(true);
            } else {
                buttons[i].setLiked(false);
            }

            buttons[i].setOnLikeListener(this);
            textViews[i].setText(String.valueOf(amounts[i]));
        }

        this.postID = postID;
        this.userID = userID;
    }

    private int findSource(LikeButton likeButton) {
        for (int i = 0; i < REACTIONS_COUNT; i++) {
            if (likeButton == buttons[i]) {
                return i;
            }
        }

        return NO_REACTION;
    }

    private void disableButtons() {
        for (int i = 0; i < REACTIONS_COUNT; i++) {
            buttons[i].setEnabled(false);
        }
    }

    private void enableButtons() {
        for (int i = 0; i < REACTIONS_COUNT; i++) {
            buttons[i].setEnabled(true);
        }
    }
}