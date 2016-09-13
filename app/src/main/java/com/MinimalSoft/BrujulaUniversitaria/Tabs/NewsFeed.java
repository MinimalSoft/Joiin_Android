package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts.Post;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts.NewsFeedAdapter;

import java.util.List;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class NewsFeed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefresh;
    private NewsFeedAdapter newsFeedAdapter;
    private View inflatedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_refresher_list, container, false);
            swipeRefresh = (SwipeRefreshLayout) inflatedView.findViewById(R.id.refresher_swipeRefresh);
            RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.refresher_recyclerView);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflatedView.getContext());
            newsFeedAdapter = new NewsFeedAdapter(this);

            swipeRefresh.setColorSchemeResources(R.color.red_900, R.color.brown_500, R.color.green_900, R.color.orange_600);
            swipeRefresh.setOnRefreshListener(this);
            recyclerView.setAdapter(newsFeedAdapter);
            recyclerView.setLayoutManager(layoutManager);
            onRefresh();
        }

        return inflatedView;
    }

    /*----OnRefreshListener Methods*/
    @Override
    public void onRefresh() {
    }

    protected void onPostUpdateFailed(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        swipeRefresh.setRefreshing(false);
    }

    protected void onPostUpdateSucceed(List<Post> list) {
        swipeRefresh.setRefreshing(false);
    }
}