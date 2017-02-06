package com.MinimalSoft.BUniversitaria.Places;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.MinimalSoft.BUniversitaria.Models.PlaceData;
import com.MinimalSoft.BUniversitaria.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PlacesActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.refresher_recyclerView);
        SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.refresher_swipeRefresh);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        String strGson = getIntent().getStringExtra("GSON_DATA");

        Type type = new TypeToken<PlaceData[]>() {}.getType();
        PlaceData[] placesList = new Gson().fromJson(strGson, type);

        PlacesListAdapter placesListAdapter = new PlacesListAdapter(placesList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(placesListAdapter);
        swipeRefresh.setEnabled(false);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}