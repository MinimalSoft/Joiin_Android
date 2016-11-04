package com.MinimalSoft.BUniversitaria.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Tabs.Articles;
import com.MinimalSoft.BUniversitaria.Tabs.Categories;
import com.MinimalSoft.BUniversitaria.Tabs.NewsFeed;
import com.MinimalSoft.BUniversitaria.Tabs.Profile;
import com.MinimalSoft.BUniversitaria.Utilities.FragmentsViewPagerAdapter;
import com.google.android.gms.maps.MapView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, DialogInterface.OnClickListener {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.main_tabLayout);
        ViewPager viewPager = (ViewPager) this.findViewById(R.id.main_viewPager);

        FragmentsViewPagerAdapter pageAdapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), false);

        pageAdapter.addFragment(new NewsFeed(), getResources().getString(R.string.tab_news));
        pageAdapter.addFragment(new Articles(), getResources().getString(R.string.tab_articles));
        pageAdapter.addFragment(new Categories(), getResources().getString(R.string.tab_categories));
        pageAdapter.addFragment(new Profile(), getResources().getString(R.string.tab_settings));

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.tab_newsfeed);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_explore);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_categories);
        tabLayout.getTabAt(3).setIcon(R.drawable.tab_profile);

        prepareMap();
        onPageSelected(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocationManager service = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean isEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isEnabled) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Activar", this);
            alertBuilder.setNegativeButton("Cancelar", this);
            alertBuilder.setTitle("El GPS esta desactivado");
            alertBuilder.setMessage("Se recomienda activar el servicio de GPS para ofrecer una óptima funcionalidad de la aplicación.");

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        }
    }

    /*----OnPageChangeListener Methods----*/

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                toolbar.setTitle(R.string.tab_news);
                break;
            case 1:
                toolbar.setTitle(R.string.tab_articles);
                break;
            case 2:
                toolbar.setTitle(R.string.tab_categories);
                break;
            case 3:
                toolbar.setTitle(R.string.tab_settings);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }

    private void prepareMap() {
        // Fixing Later Map loading Delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                } catch (Exception ignored) {

                }
            }
        }).start();
    }
}