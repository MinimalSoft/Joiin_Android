package com.MinimalSoft.BrujulaUniversitaria.Transportation;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class Map extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Bundle bundle;
    private String titulo, agency, route;
    private ImageView imgMyLocation;

    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng latLng;
    SupportMapFragment mFragment;
    Marker currLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setToolbar();
        this.bundle = getIntent().getExtras();
        this.titulo = this.bundle.getString("Titulo");
        this.route = this.bundle.getString("Route");
        this.setTitle(titulo);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                getDistance(marker);

                return false;
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void drawStops(String route) {

        String sentence = "SELECT * FROM stops WHERE agency = '" + this.agency + "' AND route ='" + route + "'";

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<Stop> stops = databaseAccess.getStops(sentence);
        databaseAccess.close();

        for (int i = 0; i < stops.size(); i++) {

            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier(stops.get(i).stopIcon, "raw", getPackageName())))
                    .anchor(0.5f, 0.5f)
                    .title("Línea "+stops.get(i).route+" - "+stops.get(i).stopName)
                    .position(new LatLng(Double.parseDouble(stops.get(i).stopLat), Double.parseDouble(stops.get(i).stopLong))));

            if (i + 1 < stops.size()) {
                mMap.addPolyline(new PolylineOptions().geodesic(false)
                        .add(new LatLng(Double.parseDouble(stops.get(i).stopLat), Double.parseDouble(stops.get(i).stopLong)))
                        .add(new LatLng(Double.parseDouble(stops.get(i + 1).stopLat), Double.parseDouble(stops.get(i + 1).stopLong)))
                        .color(Color.parseColor(getResources().getString(getResources().
                                getIdentifier(this.agency + "_" + route, "color", getPackageName()))))
                        .width(40)
                );
            }
        }
    }

    private void drawEcobici() {

        String sentence = "SELECT * FROM stops WHERE agency = 'ECO'";

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<Stop> stops = databaseAccess.getStops(sentence);
        databaseAccess.close();

        for (int i = 0; i < stops.size(); i++) {

            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier(stops.get(i).stopIcon, "raw", getPackageName())))
                    .anchor(0.5f, 0.5f)
                    .title(stops.get(i).stopName)
                    .position(new LatLng(Double.parseDouble(stops.get(i).stopLat), Double.parseDouble(stops.get(i).stopLong))));
        }
    }

    private void getStops() {
        int routes = 0;

        switch (this.titulo) {
            case "Metro":
                this.agency = "METRO";
                metro();
                break;
            case "Metrobus":
                this.agency = "MB";
                routes = 5;
                others(routes);
                break;
            case "Tren Ligero":
                this.agency = "TL";
                routes = 1;
                others(routes);
                break;
            case "Trolebus":
                this.agency = "TB";
                routes = 8;
                others(routes);
                break;
            case "Suburbano":
                this.agency = "SUB";
                routes = 1;
                others(routes);
                break;
            case "Ecobici":
                this.agency = "ECO";
                drawEcobici();
                break;
        }

    }

    public void metro() {
        if (this.route.equals("0")) {
            for (int i = 1; i <= 12; i++) {
                if (i == 10)
                    drawStops("A");

                else if (i == 11)
                    drawStops("B");

                else
                    drawStops(i + "");
            }
        } else {
            drawStops(this.route);
        }
    }

    public void others(int routes) {

        switch (agency) {


        }
        if (this.route.equals("0")) {
            for (int i = 1; i <= routes; i++) {
                drawStops(i + "");
            }
        } else {
            drawStops(this.route);
        }
    }

    private void setLocationButton() {
        imgMyLocation = (ImageView) this.findViewById(R.id.imgMyLocation);
        imgMyLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                onLocationChanged(mLastLocation);
            }
        });
    }

    private void getDistance (Marker stop)
    {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        float distance=0;

        Location stopLocation = new Location(stop.getTitle());
        stopLocation.setLatitude(stop.getPosition().latitude);
        stopLocation.setLongitude(stop.getPosition().longitude);

        distance = mLastLocation.distanceTo(stopLocation)/ 1000;

        stop.setSnippet("A "+String.format("%.2f", distance)+" km");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        setLocationButton();
    }

    @Override
    public void onLocationChanged(Location location) {

        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(15).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        getStops();

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
