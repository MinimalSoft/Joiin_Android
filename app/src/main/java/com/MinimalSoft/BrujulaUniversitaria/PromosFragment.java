package com.MinimalSoft.BrujulaUniversitaria;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promos, container, false);
    }
}
