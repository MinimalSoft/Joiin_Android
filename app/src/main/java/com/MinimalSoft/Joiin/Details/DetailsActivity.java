package com.MinimalSoft.Joiin.Details;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Reviews.ReviewDialog;
import com.MinimalSoft.Joiin.Reviews.ReviewsFragment;
import com.MinimalSoft.Joiin.Utilities.ViewSectionsPagerAdapter;
import com.bumptech.glide.Glide;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    private final String[] TAB_TITLES = {"Información", "Reseñas"};

    private ReviewDialog reviewDialog;
    private FloatingActionButton fab;
    private ImageView imageView;
    private int tabIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.details_collapsingToolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.details_tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.details_viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);

        imageView = (ImageView) findViewById(R.id.details_imageView);
        fab = (FloatingActionButton) findViewById(R.id.details_fab);

        Bundle extras = getIntent().getExtras();
        int placeID = extras.getInt(Joiin.PLACE_ID_KEY);
        int typeID = extras.getInt(Joiin.PLACE_TYPE_KEY);
        String title = extras.getString(Joiin.PLACE_NAME_KEY);
        int userID = getSharedPreferences(Joiin.USER_PREFERENCES, MODE_PRIVATE).getInt(Joiin.USER_ID, Joiin.NO_VALUE);

        ViewSectionsPagerAdapter pagerAdapter = new ViewSectionsPagerAdapter(getSupportFragmentManager(), true);

        pagerAdapter.addFragment(InformationFragment.newInstance(placeID), TAB_TITLES[0]);
        pagerAdapter.addFragment(ReviewsFragment.newInstance(placeID, typeID, userID), TAB_TITLES[1]);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        reviewDialog = new ReviewDialog(this, userID);

        fab.setOnClickListener(this);
        collapsingToolbar.setTitle(title);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //String fileName = uri.getLastPathSegment();
                reviewDialog.setImage(bitmap);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*----OnTabSelectedListener Methods----*/

    /**
     * Called when a tab enters the selected state.
     *
     * @param tab The tab that was selected
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabIndex = tab.getPosition();

        if (tabIndex > 0) {
            int typeID = getIntent().getExtras().getInt(Joiin.PLACE_TYPE_KEY);
            fab.setColorNormal(Joiin.getCategoryColor(this, typeID));
            fab.setImageResource(R.drawable.ic_create);
        } else {
            fab.setImageResource(R.drawable.ic_uber);
            fab.setColorNormalResId(R.color.black);
        }
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param tab The tab that was unselected
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications
     * may use this action to return to the top level of a category.
     *
     * @param tab The tab that was reselected.
     */
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
                if (tabIndex > 0) {
                    reviewDialog.display();
                } else {
                    goToUber();
                }
            /*case R.id.action_share:
                String message = "Gracias a Joiin, descrubri "+ placeName+ "\nBaja la app, !esta genial! \n " +
                        "http://brujulauniversitaria.com.mx";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Comparte tu nuevo hallazgo"));
                return true;*/
        }
    }

    private void goToUber() {
        try {
            /*String uri = "uber://?client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J" +
                    "&action=setPickup" +
                    "&pickup[latitude]=" + 19.329263 +
                    "&pickup[longitude]=" + -99.112118 +
                    "&pickup[nickname]=Tu%20Ubicación%20Actual" +
                    "&dropoff[latitude]=" + 0 +
                    "&dropoff[longitude]=" + 0 +
                    "&dropoff[nickname]=" + 0 +
                    "&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";*/
            getPackageManager().getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri = "uber://?action=setPickup&pickup=my_location&client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J";
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

    protected void loadImage(String source) {
        String imageURL = Joiin.API_URL + "/imagenes/places/" + source;
        Glide.with(this).load(imageURL).placeholder(R.drawable.image_loading).into(imageView);
        //Picasso.with(this).load(imageURL).resize(imageView.getWidth(), imageView.getHeight()).placeholder(R.drawable.image_loading).into(imageView);
    }
}