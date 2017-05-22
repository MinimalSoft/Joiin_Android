package com.MinimalSoft.BUniversitaria.Transport;

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
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Interfaces.MinimalSoftServices;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.Ecobici_Stop;
import com.MinimalSoft.BUniversitaria.Responses.Transport_Stop;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TransportMap extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, Callback<List<Ecobici_Stop>> {

    private Bundle bundle;
    private String titulo, agency, route;
    private ImageView imgMyLocation;

    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LatLng latLng;
    SupportMapFragment mFragment;
    Marker currLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transportation_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setToolbar();

        this.bundle = getIntent().getExtras();
        this.titulo = this.bundle.getString("Titulo");
        this.route = this.bundle.getString("Route");
        this.agency = this.bundle.getString("Agency");
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

        if (agency.equals("ECO"))
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    String snippet[] = marker.getSnippet().split(",");

                    View v = getLayoutInflater().inflate(R.layout.fragment_info, null);

                    TextView titulo = (TextView) v.findViewById(R.id.info_nameLabel);
                    TextView bicis = (TextView) v.findViewById(R.id.info_bikesLabel);
                    TextView espacios = (TextView) v.findViewById(R.id.info_spacingLabel);
                    TextView distancia = (TextView) v.findViewById(R.id.info_distanceLabel);

                    titulo.setText(marker.getTitle());
                    bicis.setText(snippet[0]);
                    espacios.setText(snippet[1]);
                    distancia.setText(snippet[2]);
                    return v;

                }
            });
    }


    private void drawStops() {

        String sentence, stopRoute, stopName, snippet;
        Double stopLat, stopLong;
        BitmapDescriptor stopIcon;
        int color;
        float anchor = 0.5f;

        if (this.route.equals("0"))
            sentence = "SELECT * FROM stops WHERE agency = '" + this.agency + "'";
        else
            sentence = "SELECT * FROM stops WHERE agency = '" + this.agency + "' AND route ='" + this.route + "'";

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<Transport_Stop> stops = databaseAccess.getStops(sentence);
        databaseAccess.close();

        for (int i = 0; i < stops.size(); i++) {

            stopRoute = stops.get(i).route;
            stopName = stops.get(i).stopName;
            stopLat = Double.parseDouble(stops.get(i).stopLat);
            stopLong = Double.parseDouble(stops.get(i).stopLong);
            stopIcon = BitmapDescriptorFactory.fromResource(getResources().
                    getIdentifier(stops.get(i).stopIcon, "raw", getPackageName()));
            color = Color.parseColor(getResources().getString(getResources().
                    getIdentifier(this.agency + "_" + stopRoute, "color", getPackageName())));

            mMap.addMarker(new MarkerOptions()
                    .icon(stopIcon)
                    .anchor(anchor, anchor)
                    .title("Línea " + stopRoute + " - " + stopName)
                    .snippet("A " + getDistance(stops.get(i), this.mLastLocation) + " km")
                    .position(new LatLng(stopLat, stopLong)));

            if (i + 1 < stops.size())
                if (stops.get(i).route.equals(stops.get(i + 1).route)) {
                    mMap.addPolyline(new PolylineOptions().geodesic(false)
                            .add(new LatLng(stopLat, stopLong))
                            .add(new LatLng(Double.parseDouble(stops.get(i + 1).stopLat), Double.parseDouble(stops.get(i + 1).stopLong)))
                            .color(color)
                            .width(40)
                    );
                }
        }
    }

    private void drawEcobici(List<Ecobici_Stop> stations) {

        String sentence = "SELECT * FROM stops WHERE agency = 'ECO'";
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(getResources().
                getIdentifier("eco_icon", "raw", getPackageName()));

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<Transport_Stop> stops = databaseAccess.getStops(sentence);
        databaseAccess.close();

        for (int i = 0; i < stops.size(); i++) {

            mMap.addMarker(new MarkerOptions()
                    .icon(icon)
                    .title(stops.get(i).stopName)
                    .snippet(stations.get(i).getFreeBikes() + "," + stations.get(i).getEmptySlots() + "," + getDistance(stops.get(i), this.mLastLocation))
                    .position(new LatLng(Double.parseDouble(stops.get(i).stopLat), Double.parseDouble(stops.get(i).stopLong))));
        }


    }

    private void getDisponibility() {
        String BASE_URL = "https://ecobici.me";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MinimalSoftServices inter = retrofit.create(MinimalSoftServices.class);

        Call<List<Ecobici_Stop>> call = inter.getAllStations();
        //asynchronous call
        call.enqueue(this);
    }

    private void setStops() {

        switch (this.agency) {
            case "ECO":
                getDisponibility();
                break;
            default:
                drawStops();
                break;
        }

    }


    private String getDistance(Transport_Stop stop, Location mLastLocation) {
        float distance = 0;

        Location stopLocation = new Location(stop.stopName);
        stopLocation.setLatitude(Double.parseDouble(stop.stopLat));
        stopLocation.setLongitude(Double.parseDouble(stop.stopLong));

        distance = mLastLocation.distanceTo(stopLocation) / 1000;

        return String.format("%.2f", distance);
    }


    @Override
    public void onResponse(Call<List<Ecobici_Stop>> call, Response<List<Ecobici_Stop>> response) {

        int code = response.code();
        if (code == 200) {
            List<Ecobici_Stop> stations = response.body();
            drawEcobici(stations);
            //Toast.makeText(this, "Nice!: ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Try again! :(" + String.valueOf(code), Toast.LENGTH_LONG).show();
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

        setLocationButton();

        setStops();
    }

    @Override
    public void onLocationChanged(Location location) {

        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(15).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onFailure(Call<List<Ecobici_Stop>> call, Throwable t) {
        Toast.makeText(this, "Error al cargar disponibilidad", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

}
