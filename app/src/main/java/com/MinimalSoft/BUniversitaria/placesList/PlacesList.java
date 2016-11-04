package com.MinimalSoft.BUniversitaria.PlacesList;

import com.MinimalSoft.BUniversitaria.Maps.DetailsActivity;
import com.MinimalSoft.BUniversitaria.Models.PlaceData;
import com.MinimalSoft.BUniversitaria.R;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ListView;
import android.widget.AdapterView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

public class PlacesList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        ListView listView = (ListView) findViewById(R.id.list_listView);

        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        String strGson = getIntent().getStringExtra("GSON");

        Type type = new TypeToken<List<PlaceData>>() {
        }.getType();
        List<PlaceData> placesList = new Gson().fromJson(strGson, type);

        PlaceListAdapter listAdapter = new PlaceListAdapter(this, R.layout.item_place, placesList);

        listView.setOnItemClickListener(this);
        listView.setAdapter(listAdapter);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*if (!super.onOptionsItemSelected(item)) {
                    NavUtils.navigateUpFromSameTask(this);
                }*/
                return true;

            case R.id.options_list:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*----OnItemClickListener Methods----*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();

        PlaceData place = (PlaceData) parent.getAdapter().getItem(position);

        String imageURL = view.getContext().getString(R.string.server_api) + "/imagenes/" + place.getImage();

        bundle.putString("PLACE_NAME", String.valueOf(place.getPlaceName()));
        bundle.putString("PLACE_ID", String.valueOf(place.getIdPlace()));
        bundle.putString("IMAGE_URL", imageURL);

        Intent intent = new Intent(view.getContext(), DetailsActivity.class);
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}