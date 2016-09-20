package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.List;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class NewsFeedAdapter extends RecyclerView.Adapter<PostHolder> {
    private List<Post> postList;
    private Activity activity;
    private Context context;
    private boolean flag;


    public NewsFeedAdapter(Fragment fragment) {
        activity = fragment.getActivity();
        postList = new ArrayList<>();
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
            holder.setStars(postList.get(position).userRating);
            holder.reviewText.setText(postList.get(position).review);
            holder.userNameText.setText(postList.get(position).userName);
            holder.dateTimeText.setText(postList.get(position).dateTime);
            holder.placeNameText.setText(postList.get(position).placeName);
            holder.likesText.setText(String.valueOf(postList.get(position).likesCount));
            holder.dislikesText.setText(String.valueOf(postList.get(position).dislikesCount));
        } else {
            holder.placeNameText.setText("Cargando...");
            holder.userNameText.setText("Cargando...");
            holder.reviewText.setText("Cargando...");
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