package com.MinimalSoft.Joiin.Places;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Details.DetailsActivity;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.PlaceData;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import me.grantland.widget.AutofitTextView;

public class SummaryFragment extends Fragment implements View.OnClickListener {
    protected static SummaryFragment newInstance(PlaceData data) {
        SummaryFragment instance = new SummaryFragment();
        String json = new Gson().toJson(data);
        Bundle bundle = new Bundle();

        bundle.putString(Joiin.JSON_DATA_KEY, json);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_summary, container, false);

        AutofitTextView distanceLabel = (AutofitTextView) inflatedView.findViewById(R.id.summary_distanceLabel);
        AutofitTextView addressLabel = (AutofitTextView) inflatedView.findViewById(R.id.summary_addressLabel);
        AutofitTextView nameLabel = (AutofitTextView) inflatedView.findViewById(R.id.summary_nameLabel);
        TextView ratingLabel = (TextView) inflatedView.findViewById(R.id.summary_ratingLabel);
        TextView unitsLabel = (TextView) inflatedView.findViewById(R.id.summary_unitsLabel);
        ImageView imageView = (ImageView) inflatedView.findViewById(R.id.summary_imageView);
        inflatedView.setOnClickListener(this);

        PlacesMapActivity activity = (PlacesMapActivity) getActivity();

        String json = getArguments().getString(Joiin.JSON_DATA_KEY);
        Type type = new TypeToken<PlaceData>() {
        }.getType();
        PlaceData data = new Gson().fromJson(json, type);

        String address = data.getStreet() + ' ' + data.getNumber() + ", " + data.getNeighborhood();
        String imageURL = Joiin.API_URL + "/imagenes/places/" + data.getImage();
        float meters = activity.getDistanceRelativeTo(data.getLatitude(), data.getLongitude());
        String[] distance = UnitFormatterUtility.getFormattedDistance(meters);

        Glide.with(activity).load(imageURL).placeholder(R.drawable.default_image).into(imageView);
        ratingLabel.setText(String.valueOf(data.getStars()));
        nameLabel.setText(data.getPlaceName());
        distanceLabel.setText(distance[0]);
        unitsLabel.setText(distance[1]);
        addressLabel.setText(address);

        return inflatedView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        PlacesMapActivity activity = (PlacesMapActivity) getActivity();

        int typeID = activity.getIntent().getIntExtra(Joiin.PLACE_TYPE_KEY, Joiin.NO_VALUE);
        String json = getArguments().getString(Joiin.JSON_DATA_KEY);
        Type type = new TypeToken<PlaceData>() {
        }.getType();
        PlaceData data = new Gson().fromJson(json, type);

        Bundle bundle = new Bundle();
        bundle.putInt(Joiin.PLACE_TYPE_KEY, typeID);
        bundle.putInt(Joiin.PLACE_ID_KEY, data.getIdPlace());
        bundle.putString(Joiin.PLACE_NAME_KEY, data.getPlaceName());

        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }
}
