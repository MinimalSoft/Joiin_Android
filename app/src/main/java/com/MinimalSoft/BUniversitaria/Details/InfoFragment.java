package com.MinimalSoft.BUniversitaria.Details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Models.AllPlaceData;
import com.MinimalSoft.BUniversitaria.R;
import com.google.gson.Gson;

public class InfoFragment extends Fragment implements View.OnClickListener {
    private static final String KEY = "GSON_DATA";

    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView aboutTextView;

    private View instagramButton;
    private View facebookButton;
    private View twitterButton;
    private View webPageButton;
    private View emailButton;

    private AllPlaceData placeData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_information, container, false);
        phoneTextView = (TextView) inflatedView.findViewById(R.id.info_telephoneLabel);
        addressTextView = (TextView) inflatedView.findViewById(R.id.info_addressLabel);
        aboutTextView = (TextView) inflatedView.findViewById(R.id.info_aboutLabel);

        instagramButton = inflatedView.findViewById(R.id.info_instagramButton);
        facebookButton = inflatedView.findViewById(R.id.info_facebookButton);
        twitterButton = inflatedView.findViewById(R.id.info_twitterButton);
        webPageButton = inflatedView.findViewById(R.id.info_webPageButton);
        emailButton = inflatedView.findViewById(R.id.info_emailButton);

        instagramButton.setVisibility(View.GONE);
        facebookButton.setVisibility(View.GONE);
        twitterButton.setVisibility(View.GONE);
        webPageButton.setVisibility(View.GONE);
        emailButton.setVisibility(View.GONE);

        /*String gsonData = getArguments().getString(KEY);
        Type type = new TypeToken<AllPlaceData>() {}.getType();
        placeData = new Gson().fromJson(gsonData, type);
        setData(placeData);*/

        return inflatedView;
    }

    /*----OnClickListener methods----*/
    @Override
    public void onClick(View v) {
        Intent intent;
        Uri uri;

        try {
            switch (v.getId()) {
                case R.id.info_telephoneLabel:
                    uri = Uri.fromParts("tel", String.valueOf(placeData.getPhone1()), null);
                    intent = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(intent);
                    break;

                case R.id.info_addressLabel:
                    uri = Uri.parse("geo: 0,0 ?q=" + placeData.getLatitude() + ',' + placeData.getLongitude());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.info_instagramButton:
                    uri = Uri.parse(placeData.getInstagram());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.info_facebookButton:
                    uri = Uri.parse(placeData.getFacebook());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.info_twitterButton:
                    uri = Uri.parse(placeData.getTwitter());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.info_webPageButton:
                    uri = Uri.parse(placeData.getWebPage());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.info_emailButton:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, placeData.getEmail());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto desde app BU");
                    startActivity(Intent.createChooser(intent, "Contactar por correo"));
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected static InfoFragment newInstance(AllPlaceData data) {
        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        String gsonData = gson.toJson(data);
        InfoFragment instance = new InfoFragment();

        bundle.putString(KEY, gsonData);
        instance.setArguments(bundle);

        return instance;
    }

    protected void setData(AllPlaceData placeData) {
        if (phoneTextView != null && aboutTextView != null && addressTextView != null) {
            String address = placeData.getStreet() + ' ' + placeData.getNumber() + ", " + placeData.getNeighborhood();
            phoneTextView.setText(String.valueOf(placeData.getPhone1()));
            aboutTextView.setText(placeData.getDescription());
            addressTextView.setText(address);

            if (placeData.getInstagram().length() > 0) {
                instagramButton.setVisibility(View.VISIBLE);
                instagramButton.setOnClickListener(this);
            }

            if (placeData.getFacebook().length() > 0) {
                facebookButton.setVisibility(View.VISIBLE);
                facebookButton.setOnClickListener(this);
            }

            if (placeData.getTwitter().length() > 0) {
                twitterButton.setVisibility(View.VISIBLE);
                twitterButton.setOnClickListener(this);
            }

            if (placeData.getWebPage().length() > 0) {
                webPageButton.setVisibility(View.VISIBLE);
                webPageButton.setOnClickListener(this);
            }

            if (placeData.getEmail().length() > 0) {
                emailButton.setVisibility(View.VISIBLE);
                emailButton.setOnClickListener(this);
            }

            addressTextView.setOnClickListener(this);
            phoneTextView.setOnClickListener(this);

            this.placeData = placeData;
        }
    }
}