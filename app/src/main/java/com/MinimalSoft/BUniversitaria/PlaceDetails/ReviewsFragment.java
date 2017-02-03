package com.MinimalSoft.BUniversitaria.PlaceDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Models.ReviewsResponse;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<ReviewsResponse> {
    private SwipeRefreshLayout swipeRefresh;
    private ReviewAdapter reviewsAdapter;
    private TextView textView;

    private static final String KEY_PLACE = "PLACE_ID";
    private static final String KEY_USER = "USER_ID";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_refresher_list, container, false);
        RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.refresher_recyclerView);
        swipeRefresh = (SwipeRefreshLayout) inflatedView.findViewById(R.id.refresher_swipeRefresh);
        textView = (TextView) inflatedView.findViewById(R.id.refresher_textView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflatedView.getContext());

        //swipeRefresh.setColorSchemeResources(R.color.red_900, R.color.brown_500, R.color.green_900, R.color.orange_600);
        swipeRefresh.setOnRefreshListener(this);
        reviewsAdapter = new ReviewAdapter();
        textView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reviewsAdapter);
        recyclerView.setHasFixedSize(true);

        onRefresh();

        return inflatedView;
    }

    /*---------------------------------- Callback Methods ----------------------------------*/
    @Override
    public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
        if (response.code() == 404) {
            Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
        } else if (!response.body().getResponse().equals("success")) {
            Toast.makeText(getContext(), response.body().getResponse(), Toast.LENGTH_LONG).show();
        } else if (response.body().getData().size() > 0) {
            reviewsAdapter.updateReviews(response.body().getData());
            textView.setVisibility(View.GONE);
        }

        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<ReviewsResponse> call, Throwable t) {
        Toast.makeText(this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        swipeRefresh.setRefreshing(false);
    }

    /* OnRefreshListener Methods */
    @Override
    public void onRefresh() {
        int userID = getArguments().getInt(KEY_USER);
        int placeID = getArguments().getInt(KEY_PLACE);
        String urlAPI = getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
        minimalSoftAPI.getReviews("reviews", String.valueOf(userID), String.valueOf(placeID)).enqueue(this);
    }

    protected static ReviewsFragment newInstance (int placeID, int userID) {
        ReviewsFragment instance= new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_USER, userID);
        bundle.putInt(KEY_PLACE, placeID);
        instance.setArguments(bundle);
        return instance;
    }
}