package com.MinimalSoft.BrujulaUniversitaria;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.Maps.Bars_Map;
import com.facebook.appevents.AppEventsLogger;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CheckGPSStatus();
        getUserPic("10207438192853912");

        //SharedPreferences settings = getSharedPreferences("facebook_pref", 0);


        //TextView texto = (TextView) findViewById(R.id.Text1);
        //texto.setText(settings.getString("userId", "NA"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            setTitle("Inicio");

        } else if (id == R.id.nav_Bar) {
            setTitle("Bares");
            Bundle args = new Bundle();
            args.putString(Bars_Map.ARG_SECTION_TITLE, "Bares");
            Fragment fragment = Bars_Map.newInstance("Bares");
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Fod) {
            setTitle("Comida");
            Bundle args = new Bundle();
            args.putString(Bars_Map.ARG_SECTION_TITLE, "Comida");
            Fragment fragment = Bars_Map.newInstance("Comida");
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Gym) {
            setTitle("Gimnasios");

        } else if (id == R.id.nav_Work) {
            setTitle("Trabajos");

        } else if (id == R.id.nav_Rent) {
            setTitle("Rentas");

        } else if (id == R.id.nav_Promo) {
            setTitle("Promociones");
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_Settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public Bitmap getUserPic(String userID) {
        String imageURL;
        Bitmap bitmap = null;
        Log.d("Imagen", "Loading Picture");
        imageURL = "https://graph.facebook.com/"+userID+"/picture?type=small";
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
        } catch (Exception e) {
            Log.d("TAG", "Loading Picture FAILED");
            e.printStackTrace();
        }
        return bitmap;
    }


    public void CheckGPSStatus() {

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("El GPS esta desactivado. \n Desea activarlo?")
                    .setCancelable(false)
                    .setPositiveButton("Activar",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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
    } // CHECAR GPS STATUS
}
