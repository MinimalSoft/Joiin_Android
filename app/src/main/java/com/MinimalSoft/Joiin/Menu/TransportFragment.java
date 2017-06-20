package com.MinimalSoft.Joiin.Menu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Transport.Routes;
import com.MinimalSoft.Joiin.Transport.TransportMap;

public class TransportFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_transport, container, false);

        ImageButton metroButton = (ImageButton) inflatedView.findViewById(R.id.menu_metroButton);
        ImageButton metrobusButton = (ImageButton) inflatedView.findViewById(R.id.menu_metrobusButton);
        ImageButton trenButton = (ImageButton) inflatedView.findViewById(R.id.menu_trenButton);
        ImageButton trolebusButton = (ImageButton) inflatedView.findViewById(R.id.menu_trolebusButton);
        ImageButton suburbanoButton = (ImageButton) inflatedView.findViewById(R.id.menu_suburbanoButton);
        ImageButton ecobiciButton = (ImageButton) inflatedView.findViewById(R.id.menu_ecobiciButton);

        ecobiciButton.setOnClickListener(this);
        suburbanoButton.setOnClickListener(this);
        trolebusButton.setOnClickListener(this);
        trenButton.setOnClickListener(this);
        metrobusButton.setOnClickListener(this);
        metroButton.setOnClickListener(this);

        return inflatedView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        bundle = new Bundle();

        switch (v.getId()) {
            case R.id.menu_metroButton:
                bundle.putString("Titulo", "Metro");
                bundle.putString("Agency", "METRO");
                logOutConfirmDialog();
                break;

            case R.id.menu_metrobusButton:
                bundle.putString("Titulo", "Metrobus");
                bundle.putString("Agency", "MB");
                logOutConfirmDialog();
                break;

            case R.id.menu_trenButton:
                bundle.putString("Titulo", "Tren Ligero");
                bundle.putString("Agency", "TL");
                getAllRoutes();
                break;

            case R.id.menu_trolebusButton:
                bundle.putString("Titulo", "Trolebus");
                bundle.putString("Agency", "TB");
                logOutConfirmDialog();
                break;

            case R.id.menu_suburbanoButton:
                bundle.putString("Titulo", "Suburbano");
                bundle.putString("Agency", "SUB");
                getAllRoutes();
                break;

            case R.id.menu_ecobiciButton:
                bundle.putString("Titulo", "Ecobici");
                bundle.putString("Agency", "ECO");
                getAllRoutes();
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent;

        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                intent = new Intent(getActivity(), Routes.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case AlertDialog.BUTTON_NEGATIVE:
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

    public void getAllRoutes() {
        bundle.putString("Route", "0");
        Intent intent = new Intent(getActivity(), TransportMap.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}