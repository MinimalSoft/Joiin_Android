package com.MinimalSoft.BUniversitaria.Articles;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.R;
import com.bumptech.glide.Glide;

class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageButton imageButton;
    private ImageView imageView;
    private TextView textView;
    private Context context;
    private String link;

    ArticleHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        textView = (TextView) itemView.findViewById(R.id.article_textView);
        imageView = (ImageView) itemView.findViewById(R.id.article_imageView);
        imageButton = (ImageButton) itemView.findViewById(R.id.article_imageButton);
    }

    public void setData(Article article) {
        Glide.with(context).load(article.getImage()).placeholder(R.drawable.default_image).into(imageView);
        textView.setText(article.getTitle().getRendered());

        imageButton.setOnClickListener(this);
        link = article.getLink();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "No application can handle this request, please install a Web browser.", Toast.LENGTH_LONG).show();
        }
    }
}