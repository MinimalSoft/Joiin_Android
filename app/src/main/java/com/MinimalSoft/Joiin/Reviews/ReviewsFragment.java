package com.MinimalSoft.Joiin.Reviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MinimalSoft.Joiin.BU;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.ReviewsData;
import com.MinimalSoft.Joiin.Responses.ReviewsResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<ReviewsResponse> {
    private SwipeRefreshLayout swipeRefresh;
    private ReviewsAdapter reviewsAdapter;
    private TextView textView;

    private Call<ReviewsResponse> apiCall;
    private int userID;

    public static ReviewsFragment newInstance(int placeID, int typeID, int userID) {
        ReviewsFragment instance = new ReviewsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(BU.USER_ID, userID);
        bundle.putInt(BU.PLACE_ID_KEY, placeID);
        bundle.putInt(BU.PLACE_TYPE_KEY, typeID);

        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.widget_list, container, false);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflatedView.getContext());
        RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.list_recyclerView);
        swipeRefresh = (SwipeRefreshLayout) inflatedView.findViewById(R.id.list_swipeRefresh);
        textView = (TextView) inflatedView.findViewById(R.id.list_textView);

        Bundle arguments = getArguments();

        if (arguments != null) {
            userID = arguments.getInt(BU.USER_ID);
            int placeID = arguments.getInt(BU.PLACE_ID_KEY);
            int typeID = arguments.getInt(BU.PLACE_TYPE_KEY);

            reviewsAdapter = new ReviewsAdapter(placeID, userID);
            swipeRefresh.setColorSchemeColors(BU.getCategoryColor(getContext(), typeID));
        } else {
            swipeRefresh.setColorSchemeResources(R.color.bars, R.color.food, R.color.gyms, R.color.residences);
            userID = getContext().getSharedPreferences(BU.PREFERENCES, Context.MODE_PRIVATE)
                    .getInt(BU.USER_ID, BU.NO_VALUE);
            reviewsAdapter = new ReviewsAdapter(userID);
        }

        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reviewsAdapter);
        swipeRefresh.setOnRefreshListener(this);

        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    /*---------------------------------- Callback Methods ----------------------------------*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        apiCall.cancel();
    }

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
        if (response.isSuccessful()) {
            List<ReviewsData> dataList = response.body().getData();
            if (dataList.size() > 0) {
                reviewsAdapter.updateItems(response.body().getData());
                textView.setVisibility(View.INVISIBLE);
            }
        } else {
            textView.setText(response.message());
        }

        swipeRefresh.setRefreshing(false);
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<ReviewsResponse> call, Throwable t) {
        textView.setText(t.getMessage());
        swipeRefresh.setRefreshing(false);
        t.printStackTrace();
    }

    /* OnRefreshListener Methods */
    @Override
    public void onRefresh() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
        Bundle arguments = getArguments();

        if (arguments != null) {
            int placeID = arguments.getInt(BU.PLACE_ID_KEY);
            apiCall = api.getReviews("reviews", String.valueOf(userID), String.valueOf(placeID));
        } else {
            int count = 25; // Default fetching amount.
            apiCall = api.getAllReviews("getLatest", String.valueOf(userID), String.valueOf(count));
        }

        apiCall.enqueue(this);
    }
}