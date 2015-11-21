package com.MinimalSoft.BrujulaUniversitaria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.Maps.Bars_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Food_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Gym_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Rent_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Work_Map;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FacebookSdk.sdkInitialize(getApplicationContext());

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
        navigationView.setItemIconTintList(null);

        CheckGPSStatus();

        //getFB();

        setName();


        actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_home);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        /**

        ImageView settings = (ImageView) findViewById(R.id.settingsImage);
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startSettings();
            }
        });
        **/
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
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_home);

            Fragment fragment = HomeFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        }else if(id==R.id.nav_Bar) {
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_bar);

            Fragment fragment = Bars_Map.newInstance("Bares");
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Fod) {
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_food);

            Fragment fragment = Food_Map.newInstance("Comida");
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Gym) {
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_gym);

            Fragment fragment = Gym_Map.newInstance("Gimnasios");
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Work) {
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_work);

            Fragment fragment = Work_Map.newInstance("Trabajos");
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Rent) {
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_rent);

            Fragment fragment = Rent_Map.newInstance("Rentas");
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Promo) {
            actionBar = getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_promo);

            Fragment fragment = PromosFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckGPSStatus();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();

        } else if (id == R.id.nav_Settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
           }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getFB(){
        new Thread(new Runnable() {

            @Override
            public void run() {

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {

                                String profileURL = json.getJSONObject("picture").getJSONObject("data").getString("url");
                                InputStream profileIn = new URL(profileURL).openStream();
                                Bitmap profile = BitmapFactory.decodeStream(profileIn);

                                String coverURL = json.getJSONObject("cover").getString("source");
                                InputStream coverIn = new URL(coverURL).openStream();
                                Bitmap cover = BitmapFactory.decodeStream(coverIn);

                                LinearLayout coverPic = (LinearLayout) findViewById(R.id.header_background);
                                BitmapDrawable background = new BitmapDrawable(cover);

                                Bitmap blurredBitmap = AsyncBlur.blur( getApplicationContext(), cover );
                                coverPic.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );

                                CircleImageView profilePic = (CircleImageView) findViewById(R.id.circle_userPicture);
                                profilePic.setImageBitmap(profile);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.type(large),cover");
                request.setParameters(parameters);
                request.executeAsync();

            }
        }).start();


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

    private void setName() {

        SharedPreferences settings = getSharedPreferences("facebook_pref", 0);
        TextView name = (TextView) findViewById(R.id.Name);
        TextView email = (TextView) findViewById(R.id.Email);

        name.setText(settings.getString("userName", "NA"));
        email.setText(settings.getString("userEmail", "NA"));

    }



}




