package com.MinimalSoft.BUniversitaria.RecyclerArticles;

import com.MinimalSoft.BUniversitaria.R;

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticleHolder> {
    private List<Article> articleList;
    private boolean flag;

    public ArticlesAdapter() {
        articleList = new ArrayList<>();
        articleList.add(new Article());
        articleList.add(new Article());
        articleList.add(new Article());
        flag = false;
    }

    /*----Adapter Methods----*/

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        if (flag) {
            holder.link = articleList.get(position).link;
            holder.pageTitle = articleList.get(position).title;
            holder.title.setText(articleList.get(position).title);
            holder.loadImage(articleList.get(position).url);
        } else {
            holder.link = holder.context.getResources().getString(R.string.bu_url);
            holder.pageTitle = "Página no disponible";
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