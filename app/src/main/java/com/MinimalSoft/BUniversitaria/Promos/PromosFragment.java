package com.MinimalSoft.BUniversitaria.Promos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MinimalSoft.BUniversitaria.R;

public class PromosFragment extends Fragment {


    // TODO: Rename and change types and number of parameters
    public static PromosFragment newInstance() {
        PromosFragment fragment = new PromosFragment();
        return fragment;
    }

    public PromosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_promos, container, false);

        ImageView bares = (ImageView) rootView.findViewById(R.id.Promo_Bar);
        ImageView gyms = (ImageView) rootView.findViewById(R.id.Promo_Gym);
        ImageView food = (ImageView) rootView.findViewById(R.id.Promo_Food);

        bares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), Bars_Promo.class);
                startActivity(intent);
            }
        });

        gyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), Gyms_Promo.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), Food_Promo.class);
                startActivity(intent);
            }
        });

        return rootView;

    }


}
