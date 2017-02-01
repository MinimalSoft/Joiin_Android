package com.MinimalSoft.BUniversitaria.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MinimalSoft.BUniversitaria.Maps.Categories_Map;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.ViewerActivity;

public class Categories extends Fragment implements View.OnClickListener {
    private View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_categories, container, false);

            ImageView barsButton = (ImageView) inflatedView.findViewById(R.id.categories_barsImage);
            ImageView foodButton = (ImageView) inflatedView.findViewById(R.id.categories_foodImage);
            ImageView gymsButton = (ImageView) inflatedView.findViewById(R.id.categories_gymsImage);
            ImageView jobsButton = (ImageView) inflatedView.findViewById(R.id.categories_jobsImage);
            ImageView promosButton = (ImageView) inflatedView.findViewById(R.id.categories_promosImage);
            ImageView featuredButton = (ImageView) inflatedView.findViewById(R.id.categories_featuredImage);
            ImageView suppliesButton = (ImageView) inflatedView.findViewById(R.id.categories_suppliesImage);
            ImageView transportButton = (ImageView) inflatedView.findViewById(R.id.category_transportImage);
            ImageView residencesButton = (ImageView) inflatedView.findViewById(R.id.categories_residencesImage);

            residencesButton.setOnClickListener(this);
            transportButton.setOnClickListener(this);
            suppliesButton.setOnClickListener(this);
            featuredButton.setOnClickListener(this);
            promosButton.setOnClickListener(this);
            gymsButton.setOnClickListener(this);
            foodButton.setOnClickListener(this);
            barsButton.setOnClickListener(this);
            jobsButton.setOnClickListener(this);
        }

        return inflatedView;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        Intent intent;

        switch (view.getId()) {
            case R.id.categories_featuredImage:
                bundle.putString("Titulo", "Destacados");
                bundle.putString("Type", "1");
                bundle.putString("Marker", "marker_bu");
                break;

            case R.id.categories_barsImage:
                bundle.putString("Titulo", "Bares");
                bundle.putString("Type", "2");
                bundle.putString("Marker", "marker_bars");
                bundle.putString("stringType", "bar");
                break;

            case R.id.categories_foodImage:
                bundle.putString("Titulo", "Comida");
                bundle.putString("Type", "3");
                bundle.putString("Marker", "marker_food");
                bundle.putString("stringType", "sitio de comida");
                break;

            case R.id.categories_gymsImage:
                bundle.putString("Titulo", "Gimnasios");
                bundle.putString("Type", "4");
                bundle.putString("Marker", "marker_gyms");
                bundle.putString("stringType", "gimnasio");
                break;

            case R.id.categories_suppliesImage:
                bundle.putString("Titulo", "Materiales");
                bundle.putString("Type", "5");
                bundle.putString("Marker", "marker_supplies");
                bundle.putString("stringType", "proveedor de material");
                break;

            case R.id.categories_residencesImage:
                bundle.putString("Titulo", "Residencias");
                bundle.putString("Type", "6");
                bundle.putString("Marker", "marker_residence");
                bundle.putString("stringType", "lugar para vivir");
                break;

            case R.id.categories_jobsImage:
                bundle.putString("Titulo", "Trabajo");
                bundle.putString("Type", "7");
                bundle.putString("Marker", "marker_jobs");
                bundle.putString("stringType", "trabajo");
                break;

            case R.id.category_transportImage:
                bundle.putString("Titulo", "Transporte");
                intent = new Intent(Categories.this.getActivity(), ViewerActivity.class);
                intent.putExtras(bundle);
                this.startActivity(intent);
                return;

            case R.id.categories_promosImage:
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

    /*public void modifyVerticalPosition(int offset) {
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
    }*/
}