package com.MinimalSoft.BUniversitaria.RecyclerPosts;

import com.MinimalSoft.BUniversitaria.Models.ReviewsData;
import com.MinimalSoft.BUniversitaria.R;
import com.like.OnLikeListener;
import com.like.LikeButton;

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public class NewsFeedAdapter extends RecyclerView.Adapter<PostHolder> {
    private final int userId;
    private List<ReviewsData> postList;
    private boolean flag;

    public NewsFeedAdapter(Fragment fragment) {
        userId = fragment.getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).getInt("USER_ID", 0);
        postList = new ArrayList<>();
        postList.add(new ReviewsData());
        postList.add(new ReviewsData());
        postList.add(new ReviewsData());
        postList.add(new ReviewsData());
        flag = false;
    }

    /*----Adapter Methods----*/

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_post, parent, false);
        return new PostHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        if (flag) {
            ReviewsData postData = postList.get(position);

            holder.setStars(postData.getStars());
            holder.loadImage(postData.getFbImage());
            holder.setTypeColors(postData.getIdType());
            holder.reviewText.setText(postData.getText());
            holder.userNameText.setText(postData.getName());
            holder.placeNameText.setText(postData.getPlaceName());
            holder.setLikes(postData.getReaction());
            holder.likesText.setText(String.valueOf(postData.getLikes()));
            holder.dislikesText.setText(String.valueOf(postData.getDislikes()));
            holder.dateTimeText.setText(postData.getDate().replace(" ", " a las "));

            final LikeCallback likeCallback = new LikeCallback(holder.dislikeButton, holder.likeButton, holder.likesText, postData.getIdReview(), userId, postData.getReaction());
            final LikeCallback dislikeCallback = new LikeCallback(holder.likeButton, holder.dislikeButton, holder.dislikesText, postData.getIdReview(), userId, postData.getReaction());

            holder.likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (dislikeCallback.isOn()) {
                        dislikeCallback.removeLike("removeDislike");
                    }

                    likeCallback.addLike("like");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    likeCallback.removeLike("removeLike");
                }
            });

            holder.dislikeButton.setOnLikeListener(new OnLikeListener() {
                public void liked(LikeButton likeButton) {
                    if (likeCallback.isOn()) {
                        likeCallback.removeLike("removeLike");
                    }

                    dislikeCallback.addLike("dislike");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    dislikeCallback.removeLike("removeDislike");
                }
            });
        } else {
            holder.placeNameText.setText("Cargando...");
            holder.userNameText.setText("Cargando...");
            holder.reviewText.setText("Cargando...");
            holder.dislikesText.setText("0");
            holder.likesText.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void updatePosts(List<ReviewsData> posts) {
        flag = true;
        postList.clear();
        postList.addAll(posts);
        this.notifyDataSetChanged();
    }
}