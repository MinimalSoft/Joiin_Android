package com.MinimalSoft.Joiin.Services;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;

public class MappingServices extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter, DialogInterface.OnClickListener {

    private final int LOCATION_BUTTON_REQUEST = 1;
    private final int MAP_LOCATION_REQUEST = 0;

    private FrameLayout summaryFragment;
    private GoogleApiClient apiClient;
    private GoogleMap mMap;

    private Location knownLocation;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // ACCESS_FINE_LOCATION
                switch (requestCode) {
                    case MAP_LOCATION_REQUEST:
                        onMapReady(mMap);
                        break;

                    case LOCATION_BUTTON_REQUEST:
                        onCurrentLocationFired(null);
                        break;
                }
            } else {
                String text = getString(R.string.app_name) + " requiere tu localización para usar todas las características y brindarte un óptima funcionalidad." +
                        "\n\nLocation Request > " + getString(R.string.app_name) + " > Permissions > Location";

                new AlertDialog.Builder(this)
                        .setNegativeButton("Cancelar", this)
                        .setPositiveButton("Aceptar", this)
                        .setTitle("Algo Importante")
                        .setCancelable(false)
                        .setMessage(text)
                        .create().show();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the mMap is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        summaryFragment = (FrameLayout) findViewById(R.id.map_summaryFragment);
        summaryFragment.setEnabled(false);
        mapFragment.getMapAsync(this);

        String title = getIntent().getStringExtra(Joiin.ACTIVITY_TITLE_KEY);

        Toolbar toolbar = (Toolbar) findViewById(R.id.map_toolbar);

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /* OnMapReadyCallback */

    /**
     * Manipulates the mMap once available.
     * This callback is triggered when the mMap is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int finePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (finePermission == PackageManager.PERMISSION_DENIED) {
            String permissions[] = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
            ActivityCompat.requestPermissions(this, permissions, MAP_LOCATION_REQUEST);
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMarkerClickListener(this);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            apiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            apiClient.connect();
        }
    }

    /* GoogleApiClient */

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        onServicesReady(mMap);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (apiClient != null) {
            apiClient.disconnect();
        }
    }

    // Use default InfoWindow frame
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /*----OnPageChangeListener Methods----*/
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                finish();
                break;
        }
    }

    /**
     * Called when the location button has been clicked.
     *
     * @param view The view that was clicked (Location Button).
     */
    public void onCurrentLocationFired(View view) {
        if (apiClient.isConnected()) {
            int finePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (finePermission == PackageManager.PERMISSION_DENIED) {
                String permissions[] = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                };
                ActivityCompat.requestPermissions(this, permissions, LOCATION_BUTTON_REQUEST);
            } else {
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (service.isProviderEnabled(LocationManager.GPS_PROVIDER)) { // GPS Activated
                    knownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);

                    if (knownLocation != null) {
                        LatLng coords = new LatLng(knownLocation.getLatitude(), knownLocation.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(coords));
                    } else {
                        Toast.makeText(this, "No se puede determinar tu localización", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String text = "Se requiere activar el servicio de Localización para usar todas las características de la app.";

                    new AlertDialog.Builder(this)
                            .setTitle("El GPS esta desactivado")
                            .setPositiveButton("Activar", this)
                            .setMessage(text)
                            .create().show();
                }
            }
        }
    }

    /**
     * Called when all Google services are running.
     *
     * @param map Instance of the Google map
     */
    public void onServicesReady(GoogleMap map) {
    }

    public void setSummaryFragment(int layout, Fragment fragment) {
        if (!summaryFragment.isEnabled()) {
            summaryFragment.setVisibility(View.VISIBLE);
            summaryFragment.setEnabled(true);
        }

        getFragmentManager().beginTransaction().replace(layout, fragment).commit();
    }

    public void removeSummaryFragment(Fragment fragment) {
        if (summaryFragment.isEnabled()) {
            summaryFragment.setEnabled(false);
            summaryFragment.setVisibility(View.INVISIBLE);

            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    public float getDistanceRelativeTo(float latitude, float longitude) {
        if (knownLocation != null) {
            Location desiredLocation = new Location(LocationManager.GPS_PROVIDER);
            desiredLocation.setLongitude(longitude);
            desiredLocation.setLatitude(latitude);

            return knownLocation.distanceTo(desiredLocation);
        } else {
            return Joiin.NO_VALUE;
        }
    }

    public String getGeolocatedAddress(LatLng latLng) {
        try {
            Geocoder geocoder = new Geocoder(this, UnitFormatterUtility.MEXICAN_LOCALE);
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address address = addressList.get(0);
            return address.getAddressLine(0) + ", " + address.getSubLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}