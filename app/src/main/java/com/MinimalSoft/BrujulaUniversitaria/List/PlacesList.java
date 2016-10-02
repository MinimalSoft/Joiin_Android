package com.MinimalSoft.BrujulaUniversitaria.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.MinimalSoft.BrujulaUniversitaria.Models.PlaceData;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getExtras().getString("TITLE"));
        String strGson = getIntent().getExtras().getString("GSON");
        String serverURL = getResources().getString(R.string.server_api) + "/imagenes/";

        Type type = new TypeToken<List<PlaceData>>() {
        }.getType();
        List<PlaceData> placeDataList = new Gson().fromJson(strGson, type);
        List<Place> placesList = new ArrayList<>();

        for (short i = 0; i < placeDataList.size(); i++) {
            String address = placeDataList.get(i).getStreet() +
                    " # " + placeDataList.get(i).getNumber() +
                    ", " + placeDataList.get(i).getNeighborhood();
            String name = placeDataList.get(i).getPlaceName();
            String link = placeDataList.get(i).getImage();
            int rating = placeDataList.get(i).getStars();
            String url = serverURL + link;

            placesList.add(new Place(name, address, rating, url));
        }

        PlaceListAdapter listAdapter = new PlaceListAdapter(this, R.layout.item_place, placesList);
        ListView listView = (ListView) findViewById(R.id.list_listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(listAdapter);
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

    }
}