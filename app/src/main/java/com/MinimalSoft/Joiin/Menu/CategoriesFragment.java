package com.MinimalSoft.Joiin.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Places.PlacesMapActivity;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Viewer.MenuViewerActivity;

public class CategoriesFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_categories, container, false);

        ImageButton barsButton = (ImageButton) inflatedView.findViewById(R.id.menu_barsButton);
        ImageButton foodButton = (ImageButton) inflatedView.findViewById(R.id.menu_foodButton);
        ImageButton gymsButton = (ImageButton) inflatedView.findViewById(R.id.menu_gymsButton);
        ImageButton promosButton = (ImageButton) inflatedView.findViewById(R.id.menu_promosButton);
        ImageButton transportButton = (ImageButton) inflatedView.findViewById(R.id.menu_transportButton);
        ImageButton residencesButton = (ImageButton) inflatedView.findViewById(R.id.menu_residencesButton);

        residencesButton.setOnClickListener(this);
        transportButton.setOnClickListener(this);
        promosButton.setOnClickListener(this);
        gymsButton.setOnClickListener(this);
        foodButton.setOnClickListener(this);
        barsButton.setOnClickListener(this);

        return inflatedView;
    }

    /* OnClickListener implemented Methods */

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.menu_barsButton:
                intent = new Intent(getContext(), PlacesMapActivity.class);
                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Bares cercanos");
                intent.putExtra(Joiin.MAP_MARKER_KEY, "MARKER_BARS");
                intent.putExtra(Joiin.PLACE_TYPE_KEY, Joiin.BARS_ID);
                break;

            case R.id.menu_foodButton:
                intent = new Intent(getContext(), PlacesMapActivity.class);
                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Lugares para comer");
                intent.putExtra(Joiin.MAP_MARKER_KEY, "MARKER_FOOD");
                intent.putExtra(Joiin.PLACE_TYPE_KEY, Joiin.FOOD_ID);
                break;

            case R.id.menu_gymsButton:
                intent = new Intent(getContext(), PlacesMapActivity.class);
                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Mejores gimnasios");
                intent.putExtra(Joiin.MAP_MARKER_KEY, "MARKER_GYMS");
                intent.putExtra(Joiin.PLACE_TYPE_KEY, Joiin.GYMS_ID);
                break;

            case R.id.menu_residencesButton:
                intent = new Intent(getContext(), PlacesMapActivity.class);
                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Dónde vivir");
                intent.putExtra(Joiin.MAP_MARKER_KEY, "MARKER_RESIDENCES");
                intent.putExtra(Joiin.PLACE_TYPE_KEY, Joiin.RESIDENCES_ID);
                break;

            case R.id.menu_transportButton:
                intent = new Intent(getContext(), MenuViewerActivity.class);
                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Transporte público");
                intent.putExtra(Joiin.PLACE_TYPE_KEY, Joiin.SUPPLIES_ID);
                break;

            case R.id.menu_promosButton:
                intent = new Intent(getContext(), MenuViewerActivity.class);
                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Selecciona la categoria");
                intent.putExtra(Joiin.PLACE_TYPE_KEY, Joiin.FEATURED_ID);
                break;

            default:
                intent = null;
        }

        startActivity(intent);
    }
}