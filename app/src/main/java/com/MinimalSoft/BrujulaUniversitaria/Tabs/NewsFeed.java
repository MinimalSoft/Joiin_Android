package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BrujulaUniversitaria.R;


public class NewsFeed extends Fragment {
    public static NewsFeed newInstance() {
        NewsFeed fragment = new NewsFeed();
        return fragment;
    }

    public NewsFeed() {
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
        return inflater.inflate(R.layout.fragment_newsfeed, container, false);
    }
}
