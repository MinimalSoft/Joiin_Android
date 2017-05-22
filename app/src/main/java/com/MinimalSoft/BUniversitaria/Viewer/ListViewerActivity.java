package com.MinimalSoft.BUniversitaria.Viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Places.PlacesListAdapter;
import com.MinimalSoft.BUniversitaria.Promos.PromosAdapter;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.PlaceData;
import com.MinimalSoft.BUniversitaria.Responses.PromoData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ListViewerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_swipeRefresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_recyclerView);
        ListView listView = (ListView) findViewById(R.id.list_listView);
        TextView textView = (TextView) findViewById(R.id.list_textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);

        textView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setEnabled(false);
        refreshLayout.setEnabled(false);

        setExtraElements(toolbar, listView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_google_maps);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This ID represents the Home or Up button. In the case of this
        // activity, the Up button is shown. For
        // more details, see the Navigation pattern on Android Design:
        //
        // http://developer.android.com/design/patterns/navigation.html#up-vs-back
        //
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setExtraElements(Toolbar toolbar, ListView listView) {
        Type type;
        Gson gson = new Gson();

        Bundle extras = getIntent().getExtras();

        int typeID = extras.getInt(BU.PLACE_TYPE_KEY);
        int resource = extras.getInt(BU.RESOURCE_KEY);
        String json = extras.getString(BU.JSON_DATA_KEY);
        String title = extras.getString(BU.ACTIVITY_TITLE_KEY);

        switch (resource) {
            case R.layout.item_promo:
                type = new TypeToken<PromoData[]>() {
                }.getType();
                PromoData[] promoData = gson.fromJson(json, type);
                PromosAdapter promosAdapter = new PromosAdapter(this, resource, promoData);
                listView.setOnItemClickListener(promosAdapter);
                listView.setAdapter(promosAdapter);
                listView.setDivider(null);
                break;

            case R.layout.item_place:
                type = new TypeToken<PlaceData[]>() {
                }.getType();
                PlaceData[] placeData = gson.fromJson(json, type);
                PlacesListAdapter placesAdapter = new PlacesListAdapter(this, resource, typeID, placeData);
                listView.setOnItemClickListener(placesAdapter);
                listView.setAdapter(placesAdapter);
                break;
        }

        toolbar.setTitle(title);
    }
}