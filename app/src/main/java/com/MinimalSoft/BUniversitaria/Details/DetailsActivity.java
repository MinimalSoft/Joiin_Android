package com.MinimalSoft.BUniversitaria.Details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Reviews.ReviewDialog;
import com.MinimalSoft.BUniversitaria.Reviews.ReviewsFragment;
import com.MinimalSoft.BUniversitaria.Utilities.ViewSectionsPagerAdapter;
import com.bumptech.glide.Glide;
import com.melnykov.fab.FloatingActionButton;

public class DetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private final String[] TAB_TITLES = {"Información", "Reseñas"};

    private ViewSectionsPagerAdapter pagerAdapter;
    private FloatingActionButton fab;
    private ImageView imageView;
    private int tab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.details_collapsingToolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.details_viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.details_tabLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        imageView = (ImageView) findViewById(R.id.details_imageView);
        fab = (FloatingActionButton) findViewById(R.id.details_fab);

        pagerAdapter = new ViewSectionsPagerAdapter(getSupportFragmentManager(), true);

        Bundle extras = getIntent().getExtras();
        int placeID = extras.getInt(BU.PLACE_ID_KEY);
        int typeID = extras.getInt(BU.PLACE_TYPE_KEY);
        String title = extras.getString(BU.PLACE_NAME_KEY);

        int userID = getSharedPreferences(BU.PREFERENCES, MODE_PRIVATE).getInt(BU.USER_ID, BU.NO_VALUE);

        pagerAdapter.addFragment(InformationFragment.newInstance(placeID), TAB_TITLES[0]);
        pagerAdapter.addFragment(ReviewsFragment.newInstance(placeID, typeID, userID), TAB_TITLES[1]);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        fab.setOnClickListener(this);
        collapsingToolbar.setTitle(title);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*----OnPageChangeListener Methods----*/

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        tab = position;

        if (position > 0) {
            int typeID = getIntent().getExtras().getInt(BU.PLACE_TYPE_KEY);
            fab.setColorNormal(BU.getCategoryColor(this, typeID));
            fab.setImageResource(R.drawable.ic_create);
        } else {
            fab.setImageResource(R.drawable.ic_uber);
            fab.setColorNormalResId(R.color.black);
        }
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case View.NO_ID: // Back button
                onBackPressed();
                break;

            case R.id.details_fab:
                if (tab > 0) {
                    String placeName = getIntent().getExtras().getString(BU.PLACE_NAME_KEY);
                    ReviewsFragment fragment = (ReviewsFragment) pagerAdapter.getItem(1);
                    ReviewDialog reviewDialog = new ReviewDialog(fragment);
                    reviewDialog.display(placeName);
                } else {
                    /*try {
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
                    }*/
                }
                break;

            /*case R.id.action_share:
                String message = "Gracias a BU, descrubri "+ placeName+ "\nBaja la app, !esta genial! \n " +
                        "http://brujulauniversitaria.com.mx";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Comparte tu nuevo hallazgo"));
                return true;*/
        }
    }

    protected void loadImage(String source) {
        String imageURL = BU.API_URL + "/imagenes/places/" + source;
        Glide.with(this).load(imageURL).placeholder(R.drawable.default_image).into(imageView);
        //Picasso.with(this).load(imageURL).resize(imageView.getWidth(), imageView.getHeight()).placeholder(R.drawable.default_image).into(imageView);
    }
}