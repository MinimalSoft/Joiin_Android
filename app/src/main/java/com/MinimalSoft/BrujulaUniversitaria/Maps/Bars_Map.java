package com.MinimalSoft.BrujulaUniversitaria.Maps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.DetailsActivity;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Bars_Map extends Fragment implements GoogleMap.OnMyLocationChangeListener{
    MapView mMapView;
    private GoogleMap googleMap;

    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_SECTION_TITLE = "section_number";

    private ImageView imgMyLocation;
    private Marker tempMarker;
    String ArgTitle = null;
    /**
     * Crea una instancia prefabricada de {@link Bars_Map}
     *
     * @param sectionTitle Título usado en el contenido
     * @return Instancia del fragmento
     */
    public static Bars_Map newInstance(String sectionTitle) {
        Bars_Map fragment = new Bars_Map();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public Bars_Map() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.lay_inicio, container, false);  return rootView;
        /*View view = inflater.inflate(R.layout.section_fragment, container, false);

        /Ubicar argumento en el text view de section_fragment.xml
        String title = getArguments().getString(ARG_SECTION_TITLE);
        TextView titulo = (TextView) rootView.findViewById(R.id.title);
        titulo.setText(title);
        return View;
        */
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        //googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.setOnMyLocationChangeListener(this);

        imgMyLocation = (ImageView) v.findViewById(R.id.imgMyLocation);
        imgMyLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getMyLocation();

            }

            private void getMyLocation() {

                try {
                    Location location = googleMap.getMyLocation();

                    LatLng target = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraPosition position = googleMap.getCameraPosition();

                    CameraPosition.Builder builder = new CameraPosition.Builder();
                    builder.zoom(15);
                    builder.target(target);
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
                } catch (NullPointerException ex) {

                }

            }
        });

        Fragment summaryFragment = new Summary();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.page_view, summaryFragment).commit();

        FrameLayout frame = (FrameLayout) v.findViewById (R.id.page_view);

        frame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                Bundle b = new Bundle();
                b.putString("Titulo", ArgTitle);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        Marks();// create marker

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    if (marker.getTitle().equals("¿Agregar un nuevo bar?")) {
                        Intent intent = new Intent(getContext(), AddPlaceActivity.class);
                        startActivity(intent);
                    }

                }
            });


            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    if (tempMarker != null) {
                        tempMarker.remove();
                    }
                }
            });

            // Clears the previously touched position
            googleMap.clear();

            // Animating to the touched position
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            // Placing a marker on the touched position
            tempMarker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("¿Agregar un nuevo bar?")
                        .snippet("Click aqui y cuentanos mas!")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bar)));

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(tempMarker.getPosition().latitude, tempMarker.getPosition().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0) +", " + addresses.get(0).getSubLocality();
                    tempMarker.setSnippet(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            tempMarker.showInfoWindow();


            Marks();

            }
        });

        return v;


    } //Inicializamos mapa

    private void Marks (){

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(19.323504, -99.130752)).title("Don Quintin").snippet("Tamaulipas 37-A, Cuauhtemoc.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bar));

        MarkerOptions marker1 = new MarkerOptions().position(
                new LatLng(19.339878, -99.129834)).title("El Pata Negra").snippet("Tamaulipas 30, Cuauhtemoc.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bar));

        MarkerOptions marker3 = new MarkerOptions().position(
                new LatLng(19.326105, -99.139122)).title("Brooklyn Rooftop").snippet("San Jerónimo 263, Álvaro Obregón.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bar));


        googleMap.addMarker(marker);
        googleMap.addMarker(marker1);
        googleMap.addMarker(marker3);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker mark) {

                FrameLayout frame = (FrameLayout) getView().findViewById(R.id.page_view);
                frame.setVisibility(View.VISIBLE);

                ImageView imagen = (ImageView) getView().findViewById(R.id.summary_image);
                TextView titulo = (TextView) getView().findViewById(R.id.summary_tittle);
                TextView direccion = (TextView) getView().findViewById(R.id.summary_address);

                switch (mark.getTitle()) {
                    case "Don Quintin":
                        titulo.setText("Don Quintin");
                        direccion.setText("Tamaulipas 37-A, Cuauhtemoc.");
                        imagen.setImageResource(R.drawable.quintin);
                        mark.showInfoWindow();
                        ArgTitle=mark.getTitle();
                        break;

                    case "El Pata Negra":
                        titulo.setText("El Pata Negra");
                        direccion.setText("Tamaulipas 30, Cuauhtemoc.");
                        imagen.setImageResource(R.drawable.patanegra);
                        mark.showInfoWindow();
                        ArgTitle=mark.getTitle();
                        break;

                    case "Brooklyn Rooftop":
                        titulo.setText("Brooklyn Rooftop");
                        direccion.setText("San Jerónimo 263, Álvaro Obregón.");
                        imagen.setImageResource(R.drawable.broklin);
                        mark.showInfoWindow();
                        ArgTitle=mark.getTitle();
                        break;
                }
                return true;
            }
        });

    } //Accedemos a Marks (BARES)

    public void onMyLocationChange(Location lastKnownLocation) {
        //Latitud : 21.0345 Longitud : -100.8694 DF
        CameraUpdate myLoc = CameraUpdateFactory.newCameraPosition(
                new CameraPosition
                        .Builder()
                        .target(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()))
                        .zoom(14)
                        .build());
        //googleMap.animateCamera(myLoc);
        googleMap.moveCamera(myLoc);
        googleMap.setOnMyLocationChangeListener(null);
    } //Mueve a la posicion de ubicacion

    public final Marker addMarker (MarkerOptions options)
    {


        return null;
    }



}
