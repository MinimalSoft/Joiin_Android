package com.MinimalSoft.BrujulaUniversitaria;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DetailsActivity extends AppCompatActivity {

    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setToolbar();

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Intent i = getIntent();
         String name = i.getStringExtra(EXTRA_NAME);
         int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);

         CollapsingToolbarLayout collapser =
         (CollapsingToolbarLayout) findViewById(R.id.collapser);
         collapser.setTitle(name); // Cambiar título

         loadImageParallax(idDrawable);// Cargar Imagen

         actionBar = getSupportActionBar();
         actionBar.setDisplayShowHomeEnabled(true);
         */

        setUber ();


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
                showSnackBar("Share");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * Se carga una imagen aleatoria para el detalle

    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // Usando Glide para la carga asíncrona
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }
     */

    private void setUber (){

        /**
        RequestButton requestButton = (RequestButton) findViewById(R.id.uberb);
        requestButton.setText("Pedir un Uber");
        RideParameters rideParams = new RideParameters.Builder()
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setPickupLocation(19.305917f, -99.108155f, "Tu Ubicacion Actual", "")
                .setDropoffLocation(19.376468f, -99.178189f, "El califa", "")
                .build();
        requestButton.setRideParameters(rideParams);

         */

        FloatingActionButton Uber = (FloatingActionButton) findViewById(R.id.uber);
        Uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PackageManager pm = getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri =

                            "uber://?client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J" +
                            "&action=setPickup" +
                            "&pickup[latitude]=19.305917" +
                            "&pickup[longitude]=-99.108155" +
                            "&pickup[nickname]=Tu%20Ubicacion%20Actual" +
                            "&dropoff[latitude]=19.376468" +
                            "&dropoff[longitude]=-99.178189" +
                            "&dropoff[nickname]=El%20Califa" +
                            "&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    // No Uber app! Open mobile website.
                    String url = "https://m.uber.com/sign-up?client_id=YOUR_CLIENT_ID";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }
        });
    }

}



