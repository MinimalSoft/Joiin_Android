package com.MinimalSoft.BUniversitaria.RecyclerPosts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BUniversitaria.Models.ReviewsData;
import com.MinimalSoft.BUniversitaria.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<PostHolder> {
    private List<ReviewsData> postList = new ArrayList<>();
    private final int userID;
    private boolean flag;

    public NewsFeedAdapter(int userID) {
        postList.add(new ReviewsData());
        postList.add(new ReviewsData());
        postList.add(new ReviewsData());
        postList.add(new ReviewsData());
        this.userID = userID;
        flag = false;
    }

    /*----------------------- Adapter Methods ------------------------*/
    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_post, parent, false);
        return new PostHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        if (flag) {
            holder.setData(postList.get(position), userID);
        } else {
            holder.setText("Cargando...");
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