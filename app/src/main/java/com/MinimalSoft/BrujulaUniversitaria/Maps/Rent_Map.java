package com.MinimalSoft.BrujulaUniversitaria.Maps;

import android.content.Intent;
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

public class Rent_Map extends Fragment implements GoogleMap.OnMyLocationChangeListener{
    MapView mMapView;
    private GoogleMap googleMap;

    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_SECTION_TITLE = "section_number";

    private ImageView imgMyLocation;
    /**
     * Crea una instancia prefabricada de {@link Rent_Map}
     *
     * @param sectionTitle Título usado en el contenido
     * @return Instancia del fragmento
     */
    public static Rent_Map newInstance(String sectionTitle) {
        Rent_Map fragment = new Rent_Map();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public Rent_Map() {
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
                }
                catch (NullPointerException ex)
                {

                }

            }
        });

        Fragment summaryFragment = new Summary();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.container, summaryFragment).commit();

        FrameLayout frame = (FrameLayout) v.findViewById (R.id.container);

        frame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        Marks();// create marker
        return v;

    } //Inicializamos mapa

    private void Marks (){

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(19.323504, -99.130752)).title("Renta de Departamento").snippet("Xola 1413, Narvarte Poniente.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.renta));

        MarkerOptions marker1 = new MarkerOptions().position(
                new LatLng(19.339878, -99.129834)).title("Renta de Departamento").snippet("Mérida 105, Roma Nte.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.renta));

        MarkerOptions marker3 = new MarkerOptions().position(
                new LatLng(19.326105, -99.139122)).title("Renta de Cuarto").snippet("Francisco Márquez 125, Condesa.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.renta));

        MarkerOptions marker4 = new MarkerOptions().position(
                new LatLng(19.412344, -99.161900)).title("Renta de Cuarto").snippet("Río Balsas 40, Cuauhtémoc.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.renta));


        googleMap.addMarker(marker);
        googleMap.addMarker(marker1);
        googleMap.addMarker(marker3);
        googleMap.addMarker(marker4);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker mark) {

                FrameLayout frame = (FrameLayout) getView().findViewById(R.id.container);
                frame.setVisibility(View.VISIBLE);

                ImageView imagen = (ImageView) getView().findViewById(R.id.summary_image);
                TextView titulo = (TextView) getView().findViewById(R.id.summary_tittle);
                TextView direccion = (TextView) getView().findViewById(R.id.summary_address);

                switch (mark.getSnippet()) {
                    case "Xola 1413, Narvarte Poniente.":
                        titulo.setText("Renta de Departamento");
                        direccion.setText("Xola 1413, Narvarte Poniente.");
                        imagen.setImageResource(R.drawable.depa1);
                        mark.showInfoWindow();
                        break;

                    case "Mérida 105, Roma Nte.":
                        titulo.setText("Renta de Departamento");
                        direccion.setText("Mérida 105, Roma Nte.");
                        imagen.setImageResource(R.drawable.depa2);
                        mark.showInfoWindow();
                        break;

                    case "Francisco Márquez 125, Condesa.":
                        titulo.setText("Renta de Cuarto");
                        direccion.setText("Francisco Márquez 125, Condesa.");
                        imagen.setImageResource(R.drawable.cuarto1);
                        mark.showInfoWindow();
                        break;

                    case "Río Balsas 40, Cuauhtémoc.":
                        titulo.setText("Renta de Cuarto");
                        direccion.setText("Río Balsas 40, Cuauhtémoc.");
                        imagen.setImageResource(R.drawable.cuarto2);
                        mark.showInfoWindow();
                        break;
                }
                return true;
            }
        });

    }

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


}
