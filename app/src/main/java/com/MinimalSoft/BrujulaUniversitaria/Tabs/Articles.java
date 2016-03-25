package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BrujulaUniversitaria.R;


public class Articles extends Fragment {
    public static Articles newInstance() {
        Articles fragment = new Articles();
        return fragment;
    }

    public Articles() {
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
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }
}
