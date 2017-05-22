package com.MinimalSoft.BUniversitaria.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.MinimalSoft.BUniversitaria.Articles.ArticlesFragment;
import com.MinimalSoft.BUniversitaria.Menu.CategoriesFragment;
import com.MinimalSoft.BUniversitaria.Preferences.ProfileFragment;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Reviews.ReviewsFragment;
import com.MinimalSoft.BUniversitaria.Utilities.ViewSectionsPagerAdapter;
import com.google.android.gms.maps.MapView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, DialogInterface.OnClickListener {
    private final String[] PAGE_TITLES = {"Novedades", "Explorar", "Categorias", "Perfil"};
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        ViewSectionsPagerAdapter pageAdapter = new ViewSectionsPagerAdapter(getSupportFragmentManager(), false);

        pageAdapter.addFragment(new ReviewsFragment(), PAGE_TITLES[0]);
        pageAdapter.addFragment(new ArticlesFragment(), PAGE_TITLES[1]);
        pageAdapter.addFragment(new CategoriesFragment(), PAGE_TITLES[2]);
        pageAdapter.addFragment(new ProfileFragment(), PAGE_TITLES[3]);

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(PAGE_TITLES.length);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_newsfeed);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_explore);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_menu);
        tabLayout.getTabAt(3).setIcon(R.drawable.tab_profile);
    }

    /*--- OnPageChangeListener Methods ---*/
    @Override
    public void onPageSelected(int position) {
        toolbar.setTitle(PAGE_TITLES[position]);
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

        if (position == 2) {
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        } else {
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onStart() {
        super.onStart();
        LocationManager service = (LocationManager) this.getSystemService(LOCATION_SERVICE);
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
        // Fixing Later TransportMap loading Delay
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