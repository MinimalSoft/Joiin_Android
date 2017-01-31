package com.MinimalSoft.BUniversitaria.Maps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Models.Data_General;
import com.MinimalSoft.BUniversitaria.Models.Response_General;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Categories_Map extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, Callback<Response_General> {

    private Bundle bundle;
    final private String cityCenterLong = "-99.141483", cityCenterLat = "19.409520";
    private String activityTittle, idType, marker, stringType, placeAddress, placeName,
            placeLat, placeLong, placeId, userLong, userLat;
    BitmapDescriptor icon;
    //private Bitmap imageToPass;
    private String placeImage;
    private ImageView button_location;
    private Marker tempMarker;
    private FrameLayout frame;
    private ImageView sum_image, sum_stars_image;
    private TextView sum_tittle, sum_address, sum_stars, sum_distance, sum_placeId;
    List<Data_General> placesData;
    Data_General placeData;
    HashMap<Marker, Data_General> haspMap = new HashMap<Marker, Data_General>();

    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getDataFromBundle();

        setToolbar();

        setActivityParams();

        this.setTitle(activityTittle);
    }

    private void drawPlaces() {

        Double Lat, Long;

        for (int i = 0; i < placesData.size(); i++) {

            Lat = Double.parseDouble(placesData.get(i).getLatitude());
            Long = Double.parseDouble(placesData.get(i).getLongitude());

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .icon(icon)
                    .position(new LatLng(Lat, Long)));
            haspMap.put(marker, placesData.get(i));

        }

        setOnMarkerClickListener();
        setOnLongClick();

    }


//Retrofit Methods

    private void getDataFromServer() {

        String BASE_URL = "http://api.buniversitaria.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interfaces inter = retrofit.create(Interfaces.class);

        Call<Response_General> call = inter.getPlaces("places", this.idType, cityCenterLat, cityCenterLong, "100");

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Response_General> call, Response<Response_General> response) {

        int code = response.code();
        if (code == 200 && response.body().getResponse().equals("success")) {
            placesData = response.body().getData();
            drawPlaces();
        } else {
            Toast.makeText(this, "Error al conectar con el servidor!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Response_General> call, Throwable t) {

        Toast.makeText(this, "Error de red!", Toast.LENGTH_LONG).show();
    }

//Listeners

    private void setOnLongClick() {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (tempMarker != null)
                    tempMarker.remove();

                // Placing a marker on the touched position
                tempMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("¿Agregar aqui un nuevo " + stringType + "? Haz Click!")
                        .icon(icon));

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents its recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getSubLocality();
                    tempMarker.setSnippet(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                tempMarker.showInfoWindow();

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        Intent intent = new Intent(getApplicationContext(), AddPlaceActivity.class);
                        startActivity(intent);
                    }
                });

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        tempMarker.remove();
                        mMap.setOnInfoWindowClickListener(null);
                        mMap.setOnMapClickListener(null);
                    }
                });

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });
    }

    private void setSummaryListener() {
        if (frame != null) {
            frame.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("placeAddress", placeAddress);
                    bundle.putString("placeName", placeName);
                    bundle.putString("placeId", placeId);
                    bundle.putString("placeImage", placeImage);
                    bundle.putString("placeLat", placeLat);
                    bundle.putString("placeLong", placeLong);
                    bundle.putString("userLat", userLat);
                    bundle.putString("userLong", userLong);
                    //bundle.putParcelable("image", imageToPass);
                    //intent.putExtra("image", imageToPass);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

    private void setOnMarkerClickListener() {

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                sum_image = (ImageView) findViewById(R.id.summary_image);
                sum_tittle = (TextView) findViewById(R.id.summary_placeName);
                sum_address = (TextView) findViewById(R.id.summary_address);
                sum_stars = (TextView) findViewById(R.id.summary_stars);
                sum_distance = (TextView) findViewById(R.id.summary_distance);
                sum_stars_image = (ImageView) findViewById(R.id.summary_stars_image);
                sum_placeId = (TextView) findViewById(R.id.summary_placeId);

                if (tempMarker != null) {
                    tempMarker.setIcon(icon);
                }

                tempMarker = marker;
                Data_General data = haspMap.get(marker);
                placeData = data;
                marker.setIcon(BitmapDescriptorFactory.fromResource(getResources().
                        getIdentifier("marker_bu", "drawable", getPackageName())));
                frame.setVisibility(View.VISIBLE);

                //TODO: Re-write the pass image to activity code
                String baseURL = "http://api.buniversitaria.com/imagenes/";
                placeImage = baseURL + data.getImage();
                Picasso.with(getApplicationContext())
                        .load(placeImage)
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                sum_image.setImageBitmap(bitmap);
                                //imageToPass = bitmap;
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
                placeAddress = data.getStreet() + " " + data.getNumber() + ", " + data.getNeighborhood();
                placeName = data.getPlaceName();
                placeId = data.getIdPlace() + "";
                placeLat = data.getLatitude();
                placeLong = data.getLongitude();

                sum_tittle.setText(placeName);
                sum_address.setText(placeAddress);
                if (data.getStars() == 0) {
                    sum_stars.setTextSize(15);
                    sum_stars_image.setVisibility(View.INVISIBLE);
                    sum_stars_image.getLayoutParams().height = 0;
                    sum_stars_image.getLayoutParams().width = 0;
                    sum_stars.setText("Aun no hay reseñas");
                } else {
                    final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                    int size = (int) (25 * scale + 0.5f);
                    sum_stars.setTextSize(25);
                    sum_stars.setText(data.getStars() + "");
                    sum_stars_image.setVisibility(View.VISIBLE);
                    sum_stars_image.getLayoutParams().height = size;
                    sum_stars_image.getLayoutParams().width = size;
                }

                sum_distance.setText(getDistance(data.getLatitude(), data.getLongitude()));
                sum_placeId.setText(data.getIdPlace() + "");

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        tempMarker = marker;
                        marker.setIcon(icon);
                        mMap.setOnMapClickListener(null);
                        frame.setVisibility(View.INVISIBLE);
                    }
                });

                setSummaryListener();

                return false;
            }
        });
    }

//Map methods

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setCompassEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            Toast.makeText(this, "Cannot show current Location", Toast.LENGTH_LONG).show();
        }

        mMap = googleMap;

        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setCompassEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        //mMap.getUiSettings().setRotateGesturesEnabled(true);
        //mMap.getUiSettings().setAllGesturesEnabled(true);

        //Prepare Map before use
        buildGoogleApiClient();
        mGoogleApiClient.connect();

        //Set Summary Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.page_view, new Summary(), "summary_fragment");
        transaction.commit();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        getDataFromServer();

        setLocationButton();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Error de red!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        userLat = location.getLatitude() + "";
        userLong = location.getLongitude() + "";
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(15).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

//Utility methods

    private String getDistance(String latitude, String longitude) {
        float distance = 0;

        Location stopLocation = new Location("Distance");
        stopLocation.setLatitude(Double.parseDouble(latitude));
        stopLocation.setLongitude(Double.parseDouble(longitude));

        distance = mLastLocation.distanceTo(stopLocation) / 1000;

        return String.format("%.2f", distance);
    }

    private void getDataFromBundle() {
        this.bundle = getIntent().getExtras();
        this.activityTittle = this.bundle.getString("Titulo");
        this.idType = this.bundle.getString("Type");
        this.marker = this.bundle.getString("Marker");
        this.stringType = this.bundle.getString("stringType");
        this.icon = BitmapDescriptorFactory.fromResource(getResources().
                getIdentifier(this.marker, "drawable", getPackageName()));
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

            /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }*/
    }

    private void setActivityParams() {
        frame = (FrameLayout) findViewById(R.id.page_view);
    }

    private void setLocationButton() {
        button_location = (ImageView) this.findViewById(R.id.imgMyLocation);
        button_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                onLocationChanged(mLastLocation);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.options_list:
                /*if (placesData != null) {
                    Gson gson = new Gson();
                    Bundle bundle = getIntent().getExtras();
                    String gsonInfo = gson.toJson(placesData);
                    Intent intent = new Intent(this, PlacesList.class);
                    intent.putExtra("IMAGE", bundle.getString("MARKER"));
                    intent.putExtra("TITLE", getIntent().getStringExtra("TITLE"));
                    intent.putExtra("GSON", gsonInfo);
                    startActivity(intent);
                }*/
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}