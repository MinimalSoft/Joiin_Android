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
    public void onBindViewHolder(PostHolder holder, final int position) {
        if (flag) {
            /*Uri uri = Uri.parse(postList.get(position).url);
            Picasso.with(context).load(uri).into(holder.image);
            holder.title.setText(postList.get(position).title);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, WebActivity.class);
                    String title = postList.get(position).title;
                    String link = postList.get(position).link;
                    intent.putExtra("TITLE", title);
                    intent.putExtra("LINK", link);
                    activity.startActivity(intent);
                }
            });*/
        } else {
            //holder.title.setText(" Loading...");
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    protected void updateArticles(List<Post> articles) {
        flag = true;
        postList.clear();
        postList.addAll(postList);
        this.notifyDataSetChanged();
    }
}