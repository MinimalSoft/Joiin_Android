package com.MinimalSoft.BrujulaUniversitaria.Maps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FloatingActionButton fab_button;
    private Bundle bundle;
    //TODO: Improve the way bitmap is passed
    //private Bitmap placeImage;
    private String placeImage;
    private ImageView details_image;
    CollapsingToolbarLayout collapser;
    private String placeLong, placeLat, userLong, userLat, placeAddress, placeName, placeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getDataFromBundle();
        setActivityGUI();
        setToolbar();

        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.uber_white);
                    int color = Color.parseColor(getResources().getString(getResources().
                            getIdentifier("black", "color", getPackageName())));
                    fab_button.setBackgroundTintList(ColorStateList.valueOf(color));
                    fab_button.setImageBitmap(icon);
                    setFavUber();
                } else {
                    Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.icon_write);
                    int color = Color.parseColor(getResources().getString(getResources().
                            getIdentifier("METRO_6", "color", getPackageName())));
                    fab_button.setBackgroundTintList(ColorStateList.valueOf(color));
                    fab_button.setImageBitmap(icon);
                    setFavReview();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundleInfo = new Bundle();
        Details_Info fragmentInfo = new Details_Info();
        bundleInfo.putString("placeName", placeName);
        bundleInfo.putString("placeAddress", placeAddress);
        bundleInfo.putString("placeId", placeId);
        bundleInfo.putString("placeLat", placeLat);
        bundleInfo.putString("placeLong", placeLong);
        fragmentInfo.setArguments(bundleInfo);

        Bundle bundleReviews = new Bundle();
        Details_Reviews fragmentReviews = new Details_Reviews();
        bundleReviews.putString("placeId", placeId);
        fragmentReviews.setArguments(bundleInfo);

        adapter.addFrag(fragmentInfo, "Información");
        adapter.addFrag(fragmentReviews, "Reseñas");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//Utility Methods

    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void setFavUber() {
        /**
         RequestButton requestButton = (RequestButton) findViewById(R.id.uberb);
         requestButton.setText("Pedir un Uber");
         RideParameters rideParams = new RideParameters.Builder()
         .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
         .setPickupLocation(19.305917f, -99.108155f, "Tu Ubicacion Actual", "\n")
         .setDropoffLocation(19.376468f, -99.178189f, "El califa", "\n")
         .build();
         requestButton.setRideParameters(rideParams);

         */
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PackageManager pm = getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri =

                            "uber://?client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J" +
                                    "&action=setPickup" +
                                    "&pickup[latitude]=" + userLat +
                                    "&pickup[longitude]=" + userLong +
                                    "&pickup[nickname]=Tu%20Ubicacion%20Actual" +
                                    "&dropoff[latitude]=" + placeLat +
                                    "&dropoff[longitude]=" + placeLong +
                                    "&dropoff[nickname]=" + placeName +
                                    "&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    // No Uber app! Open mobile website.
                    String url = "https://m.uber.com/sign-up?client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }
        });
    }

    private void setFavReview() {

    }

    private void getDataFromBundle() {
        bundle = getIntent().getExtras();
        placeName = bundle.getString("placeName");
        placeAddress = bundle.getString("placeAddress");
        placeId = bundle.getString("placeId");
        placeImage = bundle.getString("placeImage");
        placeLat = bundle.getString("placeLat");
        placeLong = bundle.getString("placeLong");
        userLat = bundle.getString("userLat");
        userLong = bundle.getString("userLong");
    }

    private void setActivityGUI() {
        fab_button = (FloatingActionButton) findViewById(R.id.fab_button);
        details_image = (ImageView) findViewById(R.id.details_image);
        collapser = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapser.setTitle(placeName);
        Picasso.with(getApplicationContext())
                .load(placeImage)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(details_image);
    }

}