package com.MinimalSoft.BUniversitaria.Details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Models.AllPlaceData;
import com.MinimalSoft.BUniversitaria.Models.DetailsResponse;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.FragmentsViewPagerAdapter;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity implements Callback<DetailsResponse>, ViewPager.OnPageChangeListener, View.OnClickListener {
    private final String[] TITLES = {"Información", "Reseñas"};

    private InfoFragment infoFragment = new InfoFragment();
    private FragmentsViewPagerAdapter pagerAdapter;
    private ReviewsFragment reviewsFragment;
    private ViewPager viewPager;

    private FloatingActionButton fab;
    private ImageView imageView;

    private AllPlaceData placeData;
    private short page;
    private int userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.details_collapsingToolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.details_tabLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        viewPager = (ViewPager) findViewById(R.id.details_viewPager);
        imageView = (ImageView) findViewById(R.id.details_imageView);
        fab = (FloatingActionButton) findViewById(R.id.details_fab);

        fab.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(this);

        userID = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).getInt("USER_ID", 50);
        collapsingToolbar.setTitle(getIntent().getStringExtra("PLACE_NAME"));
        int placeID = getIntent().getIntExtra("PLACE_ID", -1);

        reviewsFragment = ReviewsFragment.newInstance(placeID, userID);

        pagerAdapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), true);
        pagerAdapter.addFragment(infoFragment, TITLES[0]);
        pagerAdapter.addFragment(reviewsFragment, TITLES[1]);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        String urlAPI = getResources().getString(R.string.server_api);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
        Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
        minimalSoftAPI.getPlaceDetails("placeDetails", String.valueOf(placeID)).enqueue(this);

        onPageSelected(0);
    }

    /*---------------------------------- Callback Methods ----------------------------------*/
    @Override
    public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
        if (response.isSuccessful()) {
            placeData = response.body().getData();
            String imageName = placeData.getImage();
            String imageURL = getString(R.string.server_api) + "/imagenes/" + imageName;
            Picasso.with(this).load(Uri.parse(imageURL)).placeholder(R.drawable.default_image).error(R.drawable.default_image).into(imageView);

            infoFragment.setData(placeData);
        } else {
            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<DetailsResponse> call, Throwable t) {
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

            case R.id.details_fab:
                if (page > 0) {
                    new ReviewDialog(this, reviewsFragment, userID);
                }
                break;
        }
    }

    /*----OnPageChangeListener Methods----*/
    @Override
    public void onPageSelected(int position) {
        ColorStateList color;
        Bitmap bitmap;

        if (position == 0) {
            color = ContextCompat.getColorStateList(this, R.color.black);
            bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.uber_white);
        } else {
            color = ContextCompat.getColorStateList(this, R.color.red);
            bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_action_create);
        }

        fab.setBackgroundTintList(color);
        fab.setImageBitmap(bitmap);
        page = (short) position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}