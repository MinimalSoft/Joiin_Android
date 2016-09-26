package com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

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
            holder.userID = userId;
            holder.postID = postList.get(position).postID;
            holder.loadImage(postList.get(position).imageURL);
            holder.setStars(postList.get(position).userRating);
            holder.reviewText.setText(postList.get(position).review);
            holder.userNameText.setText(postList.get(position).userName);
            holder.dateTimeText.setText(postList.get(position).dateTime);
            holder.placeNameText.setText(postList.get(position).placeName);
            holder.likesText.setText(String.valueOf(postList.get(position).likesCount));
            holder.dislikesText.setText(String.valueOf(postList.get(position).dislikesCount));
            setBottomLineColor(holder.bottomLine, postList.get(position).typeID);
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

    private void setBottomLineColor(final View line, int placeType) {
        switch (placeType) {
            case 1:
                line.setBackgroundResource(R.color.featured);
                break;
            case 2:
                line.setBackgroundResource(R.color.bars);
                break;
            case 3:
                line.setBackgroundResource(R.color.food);
                break;
            case 4:
                line.setBackgroundResource(R.color.gyms);
                break;
            case 5:
                line.setBackgroundResource(R.color.supplies);
                break;
            case 6:
                line.setBackgroundResource(R.color.residences);
                break;
            case 7:
                line.setBackgroundResource(R.color.jobs);
                break;
        }
    }
}