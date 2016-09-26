package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts.Post;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BrujulaUniversitaria.Models.ReviewsResponse;
import com.MinimalSoft.BrujulaUniversitaria.RecyclerPosts.NewsFeedAdapter;

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.os.Bundle;
import android.widget.Toast;
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

public class NewsFeed extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<ReviewsResponse> {
    private SwipeRefreshLayout swipeRefresh;
    private NewsFeedAdapter newsFeedAdapter;
    private List<Post> postList;
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

    /*----Callback methods----*/

    @Override
    public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
        if (response.code() == 404) {
            Toast.makeText(this.getContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
        } else if (!response.body().getResponse().equals("success")) {
            Toast.makeText(this.getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
        } else {
            int count = response.body().getData().size();
            postList = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                int stars = response.body().getData().get(i).getStars();
                int likes = response.body().getData().get(i).getLikes();
                int userID = response.body().getData().get(i).getIdUser();
                int postID = response.body().getData().get(i).getIdReview();
                int dislikes = response.body().getData().get(i).getDislikes();

                String url = response.body().getData().get(i).getFbImage();
                String review = response.body().getData().get(i).getText();
                String userName = response.body().getData().get(i).getName();
                String placeName = response.body().getData().get(i).getPlaceName();
                String dateTime = response.body().getData().get(i).getDate().replace(" ", " a las ");

                postList.add(new Post(userID, postID, stars, likes, dislikes, userName, placeName, review, dateTime, url));
                newsFeedAdapter.updatePosts(postList);
            }
        }

        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<ReviewsResponse> call, Throwable t) {
        Toast.makeText(this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        swipeRefresh.setRefreshing(false);
    }

    /*----OnRefreshListener Methods*/
    @Override
    public void onRefresh() {
        String urlAPI = getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
        minimalSoftAPI.getAllReviews("getLatest", "10").enqueue(this);
    }
}