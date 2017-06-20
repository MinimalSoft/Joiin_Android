package com.MinimalSoft.Joiin.Reviews;

import android.widget.TextView;

import com.MinimalSoft.Joiin.BU;
import com.MinimalSoft.Joiin.Responses.ReactionResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ReactionsHandler implements OnLikeListener, Callback<ReactionResponse> {
    private final int NO_REACTION = 0; // Default value.

    private LikeButton[] buttons;
    private TextView[] textViews;
    private MinimalSoftServices api;
    private boolean flag;
    private int reviewID;
    private int userID;
    private int index;

    ReactionsHandler(LikeButton[] buttons, TextView[] textViews) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(MinimalSoftServices.class);

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
        api.reaction("setReaction", String.valueOf(userID), String.valueOf(reviewID), String.valueOf(index + 1)).enqueue(this);
        count = Integer.parseInt(String.valueOf(textViews[index].getText()));
        textViews[index].setText(String.valueOf(++count));
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        flag = false;
        disableButtons();
        index = findSource(likeButton);
        api.reaction("setReaction", String.valueOf(userID), String.valueOf(reviewID), String.valueOf(NO_REACTION)).enqueue(this);
        int count = Integer.parseInt(String.valueOf(textViews[index].getText()));
        textViews[index].setText(String.valueOf(--count));
    }

        /*------------------------------- Callback Methods -------------------------------*/

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<ReactionResponse> call, Response<ReactionResponse> response) {
        if (!response.isSuccessful()) {
            onFailure(call, null);
        }

        enableButtons();
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
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

    protected void setData(int userID, int reviewID, int reaction, List<Integer> amounts) {
        for (int i = 0; i < amounts.size(); i++) {
            if (reaction == (i + 1)) {
                index = i;
                flag = true;
                buttons[i].setLiked(true);
            } else {
                buttons[i].setLiked(false);
            }

            buttons[i].setOnLikeListener(this);
            textViews[i].setText(String.valueOf(amounts.get(i)));
        }

        this.reviewID = reviewID;
        this.userID = userID;
    }

    private int findSource(LikeButton likeButton) {
        for (int i = 0; i < buttons.length; i++) {
            if (likeButton == buttons[i]) {
                return i;
            }
        }

        return NO_REACTION;
    }

    private void disableButtons() {
        for (LikeButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private void enableButtons() {
        for (LikeButton button : buttons) {
            button.setEnabled(true);
        }
    }
}