package com.MinimalSoft.BrujulaUniversitaria.Main;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.AppBarLayout;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.ScreenUtility;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter pagerAdapter;
    private ScreenUtility screenUtility;
    private AppBarLayout appBarLayout;
    private ViewPager pagerView;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerView = (ViewPager) findViewById(R.id.page_view);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        this.setSupportActionBar(toolbar);
        this.verifyGPSStatus();

        screenUtility = new ScreenUtility(this);
        pagerAdapter = new SectionsPagerAdapter(this, pagerView);

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
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setMessage("El GPS esta desactivado. \n Desea activarlo?").setCancelable(false).setPositiveButton("Activar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        }
                    });

            alertDialogBuilder.setNegativeButton("Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
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
