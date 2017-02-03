package com.MinimalSoft.BUniversitaria.PlaceDetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Models.PlaceResponse;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.FragmentsViewPagerAdapter;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity implements Callback<PlaceResponse>, ViewPager.OnPageChangeListener, View.OnClickListener {
    private final InformationFragment informationFragment = new InformationFragment();
    private final String[] TITLES = {"Información", "Reseñas"};

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.details_collapsingToolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.details_fab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.details_viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.details_tabLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        imageView = (ImageView) findViewById(R.id.details_imageView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);

        int userID = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).getInt("USER_ID", 50);
        int placeID = getIntent().getIntExtra("ID_PLACE", -1);

        FragmentsViewPagerAdapter pagerAdapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), true);
        ReviewsFragment reviewsFragment = ReviewsFragment.newInstance(placeID, userID);

        collapsingToolbar.setTitle(getIntent().getStringExtra("PLACE_NAME"));
        pagerAdapter.addFragment(informationFragment, TITLES[0]);
        pagerAdapter.addFragment(reviewsFragment, TITLES[1]);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        String urlAPI = getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
        minimalSoftAPI.getPlaceDetails("placeDetails", String.valueOf(placeID)).enqueue(this);
    }

    /*---------------------------------- Callback Methods ----------------------------------*/
    @Override
    public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
        if (response.isSuccessful()) {
            String imageName = response.body().getData().getImage();
            String imageURL = getString(R.string.server_api) + "/imagenes/" + imageName;
            Picasso.with(this).load(Uri.parse(imageURL)).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(imageView);
            informationFragment.setData(response.body().getData());
        } else {
            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<PlaceResponse> call, Throwable t) {
        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    /*----OnClickListener methods----*/
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case View.NO_ID: // Back button
                onBackPressed();
                break;
        }
    }

    /*----OnPageChangeListener Methods----*/
    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}