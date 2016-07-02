package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Articles;

public class ArticleAdapter extends RecyclerView.Adapter <ArticleHolder> {
    private Articles articlesFragment;
    private List <Article> articleList;
    private boolean flag;

    public ArticleAdapter(final Articles articlesFragment) {
        this.articlesFragment = articlesFragment;
        articleList = new ArrayList<>();
        flag = false;
    }

    protected void addNew (List <Article> articles) {
        articleList.addAll(articles);
        this.notifyDataSetChanged();
        flag = false;
    }

    protected void removeOld () {
        articleList.clear();
        this.notifyDataSetChanged();
    }

    public void updateData () {
        if (!flag) {
            new ArticlesDataCollector (articlesFragment, this).execute();
            flag = true;
        }
    }

    public void stopCollector () {
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
    public void onBindViewHolder(ArticleHolder holder, final int position) {
        holder.title.setText(articleList.get(position).getTitle());
        holder.image.setImageBitmap(articleList.get(position).getImage());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = articleList.get(position).getTitle();
                String link = articleList.get(position).getLink();
                articlesFragment.showArticle(title, link);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}