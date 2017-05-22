package com.MinimalSoft.BUniversitaria.Articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.R;

public class ArticlesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArticlesAdapter articlesAdapter = new ArticlesAdapter();
    private SwipeRefreshLayout swipeRefresh;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.widget_list, container, false);

        textView = (TextView) inflatedView.findViewById(R.id.list_textView);
        swipeRefresh = (SwipeRefreshLayout) inflatedView.findViewById(R.id.list_swipeRefresh);
        RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.list_recyclerView);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflatedView.getContext());

        swipeRefresh.setColorSchemeResources(R.color.red, R.color.brown, R.color.green, R.color.orange);
        swipeRefresh.setOnRefreshListener(this);

        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(articlesAdapter);
        recyclerView.setHasFixedSize(true);

        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        new ArticlesCollector(articlesAdapter, swipeRefresh, textView).execute();
    }
}