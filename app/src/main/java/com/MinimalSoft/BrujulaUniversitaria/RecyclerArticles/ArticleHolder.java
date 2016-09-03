package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import com.MinimalSoft.BrujulaUniversitaria.R;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

public class ArticleHolder extends RecyclerView.ViewHolder {
    protected ImageView image;
    protected TextView title;
    protected Button button;

    public ArticleHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.article_image);
        button = (Button) itemView.findViewById(R.id.article_button);
        title = (TextView) itemView.findViewById(R.id.article_text);
    }
}