package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.MinimalSoft.BrujulaUniversitaria.Maps.Categories_Map;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.ScreenUtility;
import com.MinimalSoft.BrujulaUniversitaria.ViewerActivity;

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
    private ImageView transportButton;
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
            transportButton = (ImageView) inflatedLayout.findViewById(R.id.categories_transport_image);
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
            transportButton.setOnClickListener(this);
            gymsButton.setOnClickListener(this);
            foodButton.setOnClickListener(this);
            barsButton.setOnClickListener(this);
            jobsButton.setOnClickListener(this);
        }

        return inflatedLayout;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.categories_featured_image:
                bundle.putString("Titulo", "Destacados");
                bundle.putString("Type", "1");
                bundle.putString("Marker", "marker_bu");
                break;

            case R.id.categories_bars_image:
                bundle.putString("Titulo", "Bares");
                bundle.putString("Type", "2");
                bundle.putString("Marker", "marker_bars");
                bundle.putString("stringType", "bar");
                break;

            case R.id.categories_food_image:
                bundle.putString("Titulo", "Comida");
                bundle.putString("Type", "3");
                bundle.putString("Marker", "marker_food");
                bundle.putString("stringType", "sitio de comida");
                break;

            case R.id.categories_gyms_image:
                bundle.putString("Titulo", "Gimnasios");
                bundle.putString("Type", "4");
                bundle.putString("Marker", "marker_gyms");
                bundle.putString("stringType", "gimnasio");
                break;

            case R.id.categories_supplies_image:
                bundle.putString("Titulo", "Materiales");
                bundle.putString("Type", "5");
                bundle.putString("Marker", "marker_suplies");
                bundle.putString("stringType", "proveedor de material");
                break;

            case R.id.categories_residence_image:
                bundle.putString("Titulo", "Residencias");
                bundle.putString("Type", "6");
                bundle.putString("Marker", "marker_residence");
                bundle.putString("stringType", "lugar para vivir");
                break;

            case R.id.categories_jobs_image:
                bundle.putString("Titulo", "Trabajo");
                bundle.putString("Type", "7");
                bundle.putString("Marker", "marker_jobs");
                bundle.putString("stringType", "trabajo");
                break;

            case R.id.categories_transport_image:
                bundle.putString("Titulo", "Transporte");
                intent = new Intent(Categories.this.getActivity(), ViewerActivity.class);
                intent.putExtras(bundle);
                this.startActivity(intent);
                return;

            case R.id.categories_promos_image:
                bundle.putString("Titulo", "Promociones");
                intent = new Intent(Categories.this.getActivity(), ViewerActivity.class);
                intent.putExtras(bundle);
                this.startActivity(intent);
                return;
        }

        intent = new Intent(Categories.this.getActivity(), Categories_Map.class);
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
}