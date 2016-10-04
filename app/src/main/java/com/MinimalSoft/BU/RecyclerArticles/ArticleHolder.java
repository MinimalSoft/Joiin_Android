package com.MinimalSoft.BU.RecyclerArticles;

import com.MinimalSoft.BU.Web.WebActivity;
import com.MinimalSoft.BU.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;

public class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected Activity activity;
    protected Context context;
    protected ImageView image;
    protected TextView title;
    protected Button button;

    protected String pageTitle;
    protected String link;

    public ArticleHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.article_image);
        button = (Button) itemView.findViewById(R.id.article_button);
        title = (TextView) itemView.findViewById(R.id.article_text);
        context = itemView.getContext();
        button.setOnClickListener(this);
    }

    protected void loadImage(String url) {
        Picasso.with(context).load(Uri.parse(url)).error(R.drawable.default_image).into(image);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("TITLE", pageTitle);
        intent.putExtra("LINK", link);
        activity.startActivity(intent);
    }
}