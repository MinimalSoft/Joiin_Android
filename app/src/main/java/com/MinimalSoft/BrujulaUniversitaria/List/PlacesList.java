package com.MinimalSoft.BrujulaUniversitaria.List;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.MinimalSoft.BrujulaUniversitaria.Models.Data_General;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.example_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getExtras().getString("TITLE"));
        String strGson = getIntent().getExtras().getString("GSON");

        Type type = new TypeToken<List<Data_General>>() {
        }.getType();
        List<Data_General> dataAPIList = new Gson().fromJson(strGson, type);
        List<Place> placesList = new ArrayList<>();

        for (short i = 0; i < dataAPIList.size(); i++) {
            String address = dataAPIList.get(i).getStreet() +
                    " # " + dataAPIList.get(i).getNumber() +
                    ", " + dataAPIList.get(i).getNeighborhood();
            String name = dataAPIList.get(i).getPlaceName();
            placesList.add(new Place(name, address));
        }

        PlaceListAdapter listAdapter = new PlaceListAdapter(this, R.layout.item_list_place, placesList);
        ListView listView = (ListView) findViewById(R.id.list_listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
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