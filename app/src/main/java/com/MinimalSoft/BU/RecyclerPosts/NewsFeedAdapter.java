package com.MinimalSoft.BU.RecyclerPosts;

import com.MinimalSoft.BU.R;
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
    private List<Post> postList;
    private Context context;
    private boolean flag;

    public NewsFeedAdapter(Fragment fragment) {
        userId = fragment.getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).getInt("USER_ID", 0);
        postList = new ArrayList<>();
        postList.add(new Post());
        postList.add(new Post());
        postList.add(new Post());
        postList.add(new Post());
        flag = false;
    }

    /*----Adapter Methods----*/

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflatedView = layoutInflater.inflate(R.layout.item_post, parent, false);
        return new PostHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        if (flag) {
            holder.setLikes();
            holder.loadImage(postList.get(position).imageURL);
            holder.setStars(postList.get(position).userRating);
            holder.setTypeColors(postList.get(position).typeID);
            holder.reviewText.setText(postList.get(position).review);
            holder.userNameText.setText(postList.get(position).userName);
            holder.dateTimeText.setText(postList.get(position).dateTime);
            holder.placeNameText.setText(postList.get(position).placeName);
            holder.likesText.setText(String.valueOf(postList.get(position).likesCount));
            holder.dislikesText.setText(String.valueOf(postList.get(position).dislikesCount));

            final LikeCallback likeCallback = new LikeCallback(holder.dislikeButton, holder.likeButton, holder.likesText, postList.get(position).postID, userId);
            final LikeCallback dislikeCallback = new LikeCallback(holder.likeButton, holder.dislikeButton, holder.dislikesText, postList.get(position).postID, userId);

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

    public void updatePosts(List<Post> posts) {
        flag = true;
        postList.clear();
        postList.addAll(posts);
        this.notifyDataSetChanged();
    }
}