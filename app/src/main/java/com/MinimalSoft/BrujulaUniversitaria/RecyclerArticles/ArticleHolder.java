package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import android.support.v7.widget.RecyclerView;
import com.MinimalSoft.BrujulaUniversitaria.R;

public class ArticleHolder extends RecyclerView.ViewHolder {
    public final ImageView image;
    public final TextView title;
    public final Button button;

    public ArticleHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.entry_image);
        button = (Button) itemView.findViewById(R.id.entry_button);
        title = (TextView) itemView.findViewById(R.id.entry_text);
    }
}