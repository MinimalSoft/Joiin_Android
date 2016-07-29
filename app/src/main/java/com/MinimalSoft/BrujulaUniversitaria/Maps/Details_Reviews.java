package com.MinimalSoft.BrujulaUniversitaria.Maps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BrujulaUniversitaria.R;


public class Details_Reviews extends Fragment {


    public Details_Reviews() {
        // Required empty public constructor
    }


    public static Details_Reviews newInstance() {
        Details_Reviews fragment = new Details_Reviews();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_reviews, container, false);
    }
}
