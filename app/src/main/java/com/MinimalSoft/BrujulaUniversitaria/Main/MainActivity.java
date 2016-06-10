package com.MinimalSoft.BrujulaUniversitaria.Main;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.AlertDialog;
import android.provider.Settings;

import android.content.Intent;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.AppBarLayout;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.ScreenUtility;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private SectionsPagerAdapter pagerAdapter;
    private ScreenUtility screenUtility;
    private AppBarLayout appBarLayout;
    private ViewPager pagerView;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerView = (ViewPager) findViewById(R.id.page_view);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        this.setSupportActionBar(toolbar);

        screenUtility = new ScreenUtility(this);
        pagerAdapter = new SectionsPagerAdapter(this);

        pagerView.setAdapter(pagerAdapter);
        pagerView.addOnPageChangeListener(pagerAdapter);
        appBarLayout.addOnOffsetChangedListener(pagerAdapter);
        tabLayout.setupWithViewPager(pagerView);
        tabLayout.getTabAt(0).setIcon(R.drawable.newsfeed);
        tabLayout.getTabAt(1).setIcon(R.drawable.explore);
        tabLayout.getTabAt(2).setIcon(R.drawable.categories);
        tabLayout.getTabAt(3).setIcon(R.drawable.profile);
    }

    public void verifyGPSStatus() {
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

    @Override
    public void onStart() {
        super.onStart();
        this.verifyGPSStatus();
    }

    /* Establece la toolbar como action bar */
    /*private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        if (ab != null) {
            // Poner ícono del drawer toggle
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            //ab.setDisplayHomeAsUpEnabled(true);
        }

    }*/
}