package com.MinimalSoft.BUniversitaria.Viewer;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Menu.TransportFragment;
import com.MinimalSoft.BUniversitaria.Promos.PromosFragment;
import com.MinimalSoft.BUniversitaria.R;

public class FormViewerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        int menuType = getIntent().getIntExtra(BU.PLACE_TYPE_KEY, BU.NO_VALUE);
        String title = getIntent().getStringExtra(BU.ACTIVITY_TITLE_KEY);

        Toolbar toolbar = (Toolbar) findViewById(R.id.viewer_toolbar);

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch (menuType) {
            case BU.FEATURED_ID:
                fragmentTransaction.add(R.id.viewer_fragmentLayout, new PromosFragment());
                break;

            case BU.SUPPLIES_ID:
                fragmentTransaction.add(R.id.viewer_fragmentLayout, new TransportFragment());
                break;
        }

        fragmentTransaction.commit();
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
}