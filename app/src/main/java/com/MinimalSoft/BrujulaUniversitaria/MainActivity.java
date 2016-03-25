package com.MinimalSoft.BrujulaUniversitaria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.Promos.Bars_Promo;
import com.MinimalSoft.BrujulaUniversitaria.Promos.Food_Promo;
import com.MinimalSoft.BrujulaUniversitaria.Promos.Gyms_Promo;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Articles;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Categories;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Profile;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.NewsFeed;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CheckGPSStatus();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //mViewPager.setCurrentItem(1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position)
            {
                case 0:
                    tabLayout.getTabAt(0).setIcon(R.drawable.newsfeed);
                    getItem(2);
                    getItem(3);
                    return NewsFeed.newInstance();
                case 1:
                    tabLayout.getTabAt(1).setIcon(R.drawable.explore);
                    return Articles.newInstance();
                case 2:
                    tabLayout.getTabAt(2).setIcon(R.drawable.categories);
                    return Categories.newInstance();
                case 3:
                    tabLayout.getTabAt(3).setIcon(R.drawable.profile);
                    return Profile.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

    }


    public void CheckGPSStatus() {

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

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
    }

    private void setName() {

        SharedPreferences settings = getSharedPreferences("facebook_pref", 0);
        TextView name = (TextView) findViewById(R.id.Profile_Name);
        TextView email = (TextView) findViewById(R.id.Profile_Email);

        name.setText(settings.getString("userName", "NA"));
        email.setText(settings.getString("userEmail", "NA"));

    }

    public void PromoBares (View v)
    {
        Intent intent = new Intent(this, Bars_Promo.class);
        startActivity(intent);
    }

    public void PromoGyms (View v)
    {
        Intent intent = new Intent(this, Gyms_Promo.class);
        startActivity(intent);
    }

    public void PromoComida (View v)
    {
        Intent intent = new Intent(this, Food_Promo.class);
        startActivity(intent);
    }
}




