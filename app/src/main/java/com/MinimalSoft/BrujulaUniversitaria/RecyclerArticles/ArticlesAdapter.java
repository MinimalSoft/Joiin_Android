package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import com.MinimalSoft.BrujulaUniversitaria.Web.WebActivity;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.ArrayList;

import android.net.Uri;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticleHolder> {
    private List<Article> articleList;
    private Activity activity;
    private Context context;
    private boolean flag;


    public ArticlesAdapter(Fragment fragment) {
        activity = fragment.getActivity();
        articleList = new ArrayList<>();
        articleList.add(new Article());
        articleList.add(new Article());
        articleList.add(new Article());
        flag = false;
    }

    /*----Adapter Methods----*/

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflatedView = layoutInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, final int position) {
        if (flag) {
            Uri uri = Uri.parse(articleList.get(position).url);
            Picasso.with(context).load(uri).into(holder.image);
            holder.title.setText(articleList.get(position).title);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, WebActivity.class);
                    String title = articleList.get(position).title;
                    String link = articleList.get(position).link;
                    intent.putExtra("TITLE", title);
                    intent.putExtra("LINK", link);
                    activity.startActivity(intent);
                }
            });
        } else {
            holder.title.setText(" Cargando...");
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void updateArticles(List<Article> articles) {
        flag = true;
        articleList.clear();
        articleList.addAll(articles);
        this.notifyDataSetChanged();
    }
}