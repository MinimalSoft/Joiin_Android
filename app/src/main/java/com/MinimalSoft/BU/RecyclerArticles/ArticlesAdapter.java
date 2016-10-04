package com.MinimalSoft.BU.RecyclerArticles;

import com.MinimalSoft.BU.R;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticleHolder> {
    private List<Article> articleList;
    private Activity activity;
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
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflatedView = layoutInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        if (flag) {
            holder.activity = activity;
            holder.link = articleList.get(position).link;
            holder.pageTitle = articleList.get(position).title;
            holder.title.setText(articleList.get(position).title);
            holder.loadImage(articleList.get(position).url);
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