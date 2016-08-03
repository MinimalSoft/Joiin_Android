package com.MinimalSoft.BrujulaUniversitaria.Maps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.MinimalSoft.BrujulaUniversitaria.R;

public class AddReview extends AppCompatActivity {

    Bundle bundle;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        getDataFromBundle();
    }


    private void getDataFromBundle() {
        this.bundle = getIntent().getExtras();
        this.idUser = this.bundle.getString("idUser");
        this.idUser = this.bundle.getString("placeId");
    }
}
