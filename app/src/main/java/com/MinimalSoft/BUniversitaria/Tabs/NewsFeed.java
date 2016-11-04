package com.MinimalSoft.BUniversitaria.Tabs;

import com.MinimalSoft.BUniversitaria.RecyclerPosts.NewsFeedAdapter;
import com.MinimalSoft.BUniversitaria.Models.AllReviewsResponse;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BUniversitaria.R;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFeed extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<AllReviewsResponse> {
    private SwipeRefreshLayout swipeRefresh;
    private NewsFeedAdapter newsFeedAdapter;
    private View inflatedView;
    private int postCount;

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
            postCount = getResources().getInteger(R.integer.refresh_count);
            onRefresh();
        }

        return inflatedView;
    }

    /*----Callback methods----*/

    @Override
    public void onResponse(Call<AllReviewsResponse> call, Response<AllReviewsResponse> response) {
        if (response.code() == 404) {
            Toast.makeText(this.getContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
        } else if (!response.body().getResponse().equals("success")) {
            Toast.makeText(this.getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
        } else if (response.body().getData().size() > 0) {
            postCount += getResources().getInteger(R.integer.refresh_count);
            newsFeedAdapter.updatePosts(response.body().getData());
        }

        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<AllReviewsResponse> call, Throwable t) {
        Toast.makeText(this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        swipeRefresh.setRefreshing(false);
    }

    /*----OnRefreshListener Methods*/
    @Override
    public void onRefresh() {
        String urlAPI = getResources().getString(R.string.server_api);
        int userID = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).getInt("USER_ID", -1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
        minimalSoftAPI.getAllReviews("getLatest", String.valueOf(userID), String.valueOf(postCount)).enqueue(this);
    }
}