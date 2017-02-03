package com.MinimalSoft.BUniversitaria.PlaceDetails;

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

import com.MinimalSoft.BUniversitaria.Models.PlaceData;
import com.MinimalSoft.BUniversitaria.R;

public class InformationFragment extends Fragment implements View.OnClickListener {
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView aboutTextView;

    private View instagramButton;
    private View facebookButton;
    private View twitterButton;
    private View webPageButton;
    private View emailButton;

    private PlaceData placeData;

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

        addressTextView.setOnClickListener(this);
        phoneTextView.setOnClickListener(this);

        instagramButton.setVisibility(View.GONE);
        facebookButton.setVisibility(View.GONE);
        twitterButton.setVisibility(View.GONE);
        webPageButton.setVisibility(View.GONE);
        emailButton.setVisibility(View.GONE);

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

    protected void setData(PlaceData data) {
        String address = data.getStreet() + ' ' + data.getNumber() + ", " + data.getNeighborhood();
        phoneTextView.setText(String.valueOf(data.getPhone1()));
        aboutTextView.setText(data.getDescription());
        addressTextView.setText(address);

        if (data.getInstagram().length() > 0) {
            instagramButton.setVisibility(View.VISIBLE);
            instagramButton.setOnClickListener(this);
        }

        if (data.getFacebook().length() > 0) {
            facebookButton.setVisibility(View.VISIBLE);
            facebookButton.setOnClickListener(this);
        }

        if (data.getTwitter().length() > 0) {
            twitterButton.setVisibility(View.VISIBLE);
            twitterButton.setOnClickListener(this);
        }

        if (data.getWebPage().length() > 0) {
            webPageButton.setVisibility(View.VISIBLE);
            webPageButton.setOnClickListener(this);
        }

        if (data.getEmail().length() > 0) {
            emailButton.setVisibility(View.VISIBLE);
            emailButton.setOnClickListener(this);
        }

        placeData = data;
    }
}