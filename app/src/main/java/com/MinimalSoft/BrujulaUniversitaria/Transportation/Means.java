package com.MinimalSoft.BrujulaUniversitaria.Transportation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MinimalSoft.BrujulaUniversitaria.R;

public class Means extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private Bundle bundle = null;
    private Intent intent;

    public Means() {
        // Required empty public constructor
    }

    public static Means newInstance() {
        Means fragment = new Means();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_means, container, false);

        ImageView metro = (ImageView) rootView.findViewById(R.id.transportation_metro_image);
        ImageView metrobus = (ImageView) rootView.findViewById(R.id.transportation_metrobus_image);
        ImageView trenligero = (ImageView) rootView.findViewById(R.id.transportation_tl_image);
        ImageView trolebus = (ImageView) rootView.findViewById(R.id.transportation_tb_image);
        ImageView suburbano = (ImageView) rootView.findViewById(R.id.transportation_suburbano_image);
        ImageView ecobici = (ImageView) rootView.findViewById(R.id.transportation_ecobici_image);

        metro.setOnClickListener(this);
        metrobus.setOnClickListener(this);
        trenligero.setOnClickListener(this);
        trolebus.setOnClickListener(this);
        suburbano.setOnClickListener(this);
        ecobici.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        bundle = new Bundle();

        switch (v.getId()) {
            case R.id.transportation_metro_image:
                bundle.putString("Titulo", "Metro");
                logOutConfirmDialog();
                break;

            case R.id.transportation_metrobus_image:
                bundle.putString("Titulo", "Metrobus");
                logOutConfirmDialog();
                break;

            case R.id.transportation_tl_image:
                bundle.putString("Titulo", "Tren Ligero");
                getAllRoutes();
                break;

            case R.id.transportation_tb_image:
                bundle.putString("Titulo", "Trolebus");
                logOutConfirmDialog();
                break;

            case R.id.transportation_suburbano_image:
                bundle.putString("Titulo", "Suburbano");
                getAllRoutes();
                break;

            case R.id.transportation_ecobici_image:
                bundle.putString("Titulo", "Ecobici");
                getAllRoutes();
                break;
        }
    }

    private void logOutConfirmDialog() {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this.getActivity());
        confirmDialog.setMessage("¿Deseas vizualizar toda la red, o solo una línea?");
        confirmDialog.setNegativeButton("Toda la red", this);
        confirmDialog.setPositiveButton("Solo una línea", this);
        confirmDialog.setTitle("Seleciona");
        confirmDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which)
        {
            case -1:
                intent = new Intent(getActivity(), Routes.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case -2:
                getAllRoutes();
                break;
        }
    }

    public void getAllRoutes()
    {
        bundle.putString("Route", "0");
        intent = new Intent(getActivity(), Map.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
