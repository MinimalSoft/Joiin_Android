package com.MinimalSoft.BrujulaUniversitaria;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.MinimalSoft.BrujulaUniversitaria.Maps.Bars_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Food_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Gym_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Rent_Map;
import com.MinimalSoft.BrujulaUniversitaria.Maps.Work_Map;
import com.MinimalSoft.BrujulaUniversitaria.Promos.PromosFragment;

public class ViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        setToolbar();

        Bundle bundle = getIntent().getExtras();
        String titulo = bundle.getString("Titulo");

        switch (titulo) {
            case "bares": setFragment(new Bars_Map());
                setTitle("Bares");
                break;
            case "comida": setFragment(new Food_Map());
                setTitle("Comida");
                break;
            case "gyms": setFragment(new Gym_Map());
                setTitle("Gimnasios");
                break;
            case "renta": setFragment(new Rent_Map());
                setTitle("Donde Vivir");
                break;
            case "trabajo": setFragment(new Work_Map());
                setTitle("Trabajos");
                break;
            case "promos": setFragment(new PromosFragment());
                setTitle("Promociones");
                break;

        }

    }


    protected void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Viewer_Container, fragment);
        fragmentTransaction.commit();

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
