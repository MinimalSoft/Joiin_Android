package com.MinimalSoft.BUniversitaria.Places;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.PlaceData;
import com.MinimalSoft.BUniversitaria.Responses.PlacesResponse;
import com.MinimalSoft.BUniversitaria.Services.MappingServices;
import com.MinimalSoft.BUniversitaria.Services.MinimalSoftServices;
import com.MinimalSoft.BUniversitaria.Viewer.ListViewerActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesMapActivity extends MappingServices implements Callback<PlacesResponse> {
    private HashMap<Marker, PlaceData> placesDictionary = new HashMap<>();
    private SummaryFragment summaryFragment;

    private Marker selectedMarker, newMarker;
    private GoogleMap map;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.map_showList:
                if (!placesDictionary.isEmpty()) {
                    int typeID = getIntent().getIntExtra(BU.PLACE_TYPE_KEY, BU.NO_VALUE);
                    String title = BU.getCategoryName(typeID) + " cerca de ti";
                    String json = new Gson().toJson(placesDictionary.values());

                    Bundle bundle = new Bundle();
                    bundle.putString(BU.JSON_DATA_KEY, json);
                    bundle.putString(BU.ACTIVITY_TITLE_KEY, title);
                    bundle.putInt(BU.PLACE_TYPE_KEY, typeID);
                    bundle.putInt(BU.RESOURCE_KEY, R.layout.item_place);

                    Intent intent = new Intent(this, ListViewerActivity.class);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else {
                    //String mensaje = "Aún no se han encontrado " + BU.getCategoryName(placeTypeID);
                    Toast.makeText(this, "No se han encotrado establecimientos", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onServicesReady(GoogleMap map) {
        map.setOnMapLongClickListener(this);
        map.setOnMapClickListener(this);

        this.map = map;
        onCurrentLocationFired(null);
    }

    @Override
    public void onCurrentLocationFired(View view) {
        super.onCurrentLocationFired(view);
        Toast.makeText(this, "Buscando establecimientos...", Toast.LENGTH_SHORT).show();
        //Snackbar.make(getCurrentFocus(), "Buscando establecimientos...", Snackbar.LENGTH_SHORT).show();
        int typeID = getIntent().getIntExtra(BU.PLACE_TYPE_KEY, BU.NO_VALUE);

        String radio = String.valueOf(map.getCameraPosition().zoom);
        String latitude = String.valueOf(map.getCameraPosition().target.latitude);
        String longitude = String.valueOf(map.getCameraPosition().target.longitude);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
        api.getPlaces("places", String.valueOf(typeID), latitude, longitude, radio).enqueue(this);

    }

    /*-------------------------------- Callback Methods --------------------------------*/

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
        if (response.isSuccessful()) {
            List<PlaceData> placesList = response.body().getData();
            MarkerOptions options = new MarkerOptions();
            map.clear();

            for (PlaceData data : placesList) {
                LatLng coords = new LatLng(data.getLatitude(), data.getLongitude());
                options.position(coords);

                Marker marker = map.addMarker(options);
                placesDictionary.put(marker, data);
            }
        } else {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<PlacesResponse> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (selectedMarker != null) {
            selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }

        if (placesDictionary.containsKey(marker)) {
            PlaceData data = placesDictionary.get(marker);
            summaryFragment = SummaryFragment.newInstance(data);
            setSummaryFragment(R.id.map_summaryFragment, summaryFragment);

            //marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bu));
            selectedMarker = marker;
            return false;
        } else {
            String address = getGeolocatedAddress(marker.getPosition());

            if (address != null) {
                marker.setSnippet(address);
            }

            marker.showInfoWindow();
            return true;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (selectedMarker != null) {
            removeSummaryFragment(summaryFragment);

            selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker());
            selectedMarker = null;
        }

        if (newMarker != null) {
            newMarker.remove();
            newMarker = null;
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng)
                .title("Agregar nuevo establecimiento aqui? Haz click!");

        if (newMarker != null) {
            newMarker.remove();
            newMarker = null;
        }

        newMarker = map.addMarker(options);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Adding a place is not yet avalible...", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, AddPlaceActivity.class);
        //startActivity(intent);
    }
}