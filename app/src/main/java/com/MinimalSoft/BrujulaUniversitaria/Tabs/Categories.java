package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.ViewerActivity;
import com.MinimalSoft.BrujulaUniversitaria.Maps.AddPlaceActivity;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.ScreenUtility;

public class Categories extends Fragment implements View.OnClickListener {
    private int pagerViewHeight;
    private int topLayoutsMargin;
    private int fullLayoutHeight;

    private Intent intent;
    private View inflatedLayout;
    private ImageView jobsButton;
    private ImageView barsButton;
    private ImageView foodButton;
    private ImageView gymsButton;
    private ImageView coffeeButton;
    private ImageView promosButton;
    private ImageView featuredButton;
    private ImageView suppliesButton;
    private ImageView residenceButton;
    private LinearLayout topVerticalLayout;
    private LinearLayout middleVerticalLayout;
    private LinearLayout bottomVerticalLayout;
    private LinearLayout.LayoutParams layoutParams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedLayout == null) {
            inflatedLayout = inflater.inflate(R.layout.fragment_categories, container, false);

            jobsButton = (ImageView) inflatedLayout.findViewById(R.id.categories_jobs_image);
            barsButton = (ImageView) inflatedLayout.findViewById(R.id.categories_bars_image);
            foodButton = (ImageView) inflatedLayout.findViewById(R.id.categories_food_image);
            gymsButton = (ImageView) inflatedLayout.findViewById(R.id.categories_gyms_image);
            coffeeButton = (ImageView) inflatedLayout.findViewById(R.id.categories_coffee_image);
            promosButton = (ImageView) inflatedLayout.findViewById(R.id.categories_promos_image);
            featuredButton = (ImageView) inflatedLayout.findViewById(R.id.categories_featured_image);
            suppliesButton = (ImageView) inflatedLayout.findViewById(R.id.categories_supplies_image);
            residenceButton = (ImageView) inflatedLayout.findViewById(R.id.categories_residence_image);
            topVerticalLayout = (LinearLayout) inflatedLayout.findViewById(R.id.categories_top_vertical_layout);
            middleVerticalLayout = (LinearLayout) inflatedLayout.findViewById(R.id.categories_middle_vertical_layout);
            bottomVerticalLayout = (LinearLayout) inflatedLayout.findViewById(R.id.categories_bottom_vertical_layout);

            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fullLayoutHeight = topVerticalLayout.getHeight() + middleVerticalLayout.getHeight() + bottomVerticalLayout.getHeight();

            residenceButton.setOnClickListener(this);
            suppliesButton.setOnClickListener(this);
            featuredButton.setOnClickListener(this);
            promosButton.setOnClickListener(this);
            coffeeButton.setOnClickListener(this);
            gymsButton.setOnClickListener(this);
            foodButton.setOnClickListener(this);
            barsButton.setOnClickListener(this);
            jobsButton.setOnClickListener(this);
        }

        return inflatedLayout;
    }

    /* OnClickListener implemented Methods */

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.categories_featured_image:
                //bundle.putString("Titulo", "destacados");
                intent = new Intent(getContext(), AddPlaceActivity.class);
                break;

            case R.id.categories_bars_image:
                bundle.putString("Titulo", "bares");
                break;

            case R.id.categories_food_image:
                bundle.putString("Titulo", "comida");
                break;

            case R.id.categories_coffee_image:
                break;

            case R.id.categories_supplies_image:
                break;

            case R.id.categories_residence_image:
                bundle.putString("Titulo", "renta");
                break;

            case R.id.categories_jobs_image:
                bundle.putString("Titulo", "trabajo");
                break;

            case R.id.categories_gyms_image:
                bundle.putString("Titulo", "gyms");
                break;

            case R.id.categories_promos_image:
                bundle.putString("Titulo", "promos");
                break;
        }

        intent = new Intent(Categories.this.getActivity(), ViewerActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void modifyVerticalPosition(int offset) {
        if (topVerticalLayout != null) {
            if(topVerticalLayout.getHeight() == 0) {
                fullLayoutHeight = (int) (360 * ScreenUtility.getDensity()); // Cheater way !!
            } else {
                fullLayoutHeight = topVerticalLayout.getHeight() + middleVerticalLayout.getHeight() + bottomVerticalLayout.getHeight();
            }

            pagerViewHeight = ScreenUtility.getPhysicalHeight() - (ScreenUtility.getAppBarPhysicalHeight() + offset);
            topLayoutsMargin = (pagerViewHeight - fullLayoutHeight) / 4;

            layoutParams.setMargins(0, topLayoutsMargin, 0, 0);
            topVerticalLayout.setLayoutParams(layoutParams);
            middleVerticalLayout.setLayoutParams(layoutParams);
            bottomVerticalLayout.setLayoutParams(layoutParams);
        }
    }

    /*public static Categories newInstance() {
        Categories fragment = new Categories();
        return fragment;
    }*/
}