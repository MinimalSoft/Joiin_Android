package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.Toast;
import android.widget.ProgressBar;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import android.support.annotation.Nullable;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Web.WebActivity;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles.ArticleAdapter;

public class Articles extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArticleAdapter articlesAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressRing;
    private View inflatedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_articles, container, false);
            progressRing = (ProgressBar) inflatedView.findViewById(R.id.articles_progress);
            swipeRefresh = (SwipeRefreshLayout) inflatedView.findViewById(R.id.articles_swipe_refresh);
            RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.articles_recycler);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflatedView.getContext());
            swipeRefresh.setColorSchemeResources(R.color.red, R.color.brown, R.color.green, R.color.orange);
            swipeRefresh.setOnRefreshListener(this);

            articlesAdapter = new ArticleAdapter(this);
            recyclerView.setAdapter(articlesAdapter);
            recyclerView.setLayoutManager(layoutManager);
        }

        return inflatedView;
    }

    public void showArticle (String title, String link) {
        Intent intent = new Intent(this.getActivity(), WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("LINK", link);
        this.startActivity(intent);
    }

    public void onPostUpdateFailed () {
        Toast.makeText(this.getContext(), "Error al cargar los artículos.", Toast.LENGTH_SHORT).show();
        progressRing.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    public void onPostUpdateSucceed () {
        progressRing.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        articlesAdapter.updateData();
    }

    @Override
    public void onRefresh() {
        articlesAdapter.updateData();
    }

    @Override
    public void onStop() {
        super.onStop();
        articlesAdapter.stopCollector();
    }
}