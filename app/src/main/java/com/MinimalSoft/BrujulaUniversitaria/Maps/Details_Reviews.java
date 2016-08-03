package com.MinimalSoft.BrujulaUniversitaria.Maps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.MinimalSoft.BrujulaUniversitaria.Models.Data_Reviews;
import com.MinimalSoft.BrujulaUniversitaria.Models.Response_Reviews;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Details_Reviews extends Fragment implements Callback<Response_Reviews> {

    private RecyclerView recyclerView;
    private ArrayList<Data_Reviews> data;
    //private List<Data_Reviews> placeReviews;
    private Data_Reviews placeReviews;
    private ReviewsAdapter adapter;
    private View rootView, superView;
    private Bundle bundle;
    private String placeId;


    public Details_Reviews() {
        // Required empty public constructor
    }


    public static Details_Reviews newInstance() {
        Details_Reviews fragment = new Details_Reviews();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_reviews, container, false);

        getDataFromBundle();
        initViews();

        return rootView;
    }

    private void initViews() {

        superView = rootView.findViewById(R.id.coordinator_info);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.reviews_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        getDataFromServer();
    }

    private void getDataFromBundle() {
        this.bundle = this.getArguments();
        placeId = bundle.getString("placeId");
    }

    private void getDataFromServer() {

        String BASE_URL = "http://ec2-52-38-75-156.us-west-2.compute.amazonaws.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Interfaces inter = retrofit.create(Interfaces.class);
        Call<Response_Reviews> call = inter.getReview("reviews",placeId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Response_Reviews> call, Response<Response_Reviews> response) {
        int code = response.code();
        if (code == 200 && response.body().getResponse().equals("success")) {
            placeReviews = response.body().getData();

            data = new ArrayList<>(Arrays.asList(placeReviews));
            adapter = new ReviewsAdapter(data);
            recyclerView.setAdapter(adapter);

            FrameLayout frame = (FrameLayout) rootView.findViewById(R.id.reviews_message);
            frame.setVisibility(View.GONE);
        } else {
            showSnackBar("Error al conectar con el servidor!");
        }

    }

    @Override
    public void onFailure(Call<Response_Reviews> call, Throwable t) {
        showSnackBar("Error de red!");
    }

    private void showSnackBar(String msg) {
        //Snackbar.make(superView, msg, Snackbar.LENGTH_LONG).show();
    }
}
