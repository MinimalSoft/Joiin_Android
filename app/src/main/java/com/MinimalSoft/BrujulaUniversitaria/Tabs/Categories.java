package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MinimalSoft.BrujulaUniversitaria.R;

import java.util.List;

public class Categories extends Fragment {

    public static Categories newInstance() {
        Categories fragment = new Categories();
        return fragment;
    }

    public Categories() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_categories, container, false);
        setListeners(layout);
        return layout;
    }

    private void setListeners(View layout) {
        ImageView botonBars = (ImageView) layout.findViewById(R.id.Home_Menu_Bars);
        botonBars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this.getActivity(), List.class);
                startActivity(intent);
            }
        });

        ImageView botonFood = (ImageView) layout.findViewById(R.id.Home_Menu_Food);
        botonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this.getActivity(), List.class);
                startActivity(intent);
            }
        });

        ImageView botonGyms = (ImageView) layout.findViewById(R.id.Home_Menu_Gym);
        botonGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this.getActivity(), List.class);
                startActivity(intent);
            }
        });

        ImageView botonWork = (ImageView) layout.findViewById(R.id.Home_Menu_Work);
        botonWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this.getActivity(), List.class);
                startActivity(intent);
            }
        });

        ImageView botonPromos = (ImageView) layout.findViewById(R.id.Home_Menu_Promos);
        botonPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this.getActivity(), List.class);
                startActivity(intent);
            }
        });

        ImageView botonRent = (ImageView) layout.findViewById(R.id.Home_Menu_Rent);
        botonRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this.getActivity(), List.class);
                startActivity(intent);
            }
        });

    }

}
