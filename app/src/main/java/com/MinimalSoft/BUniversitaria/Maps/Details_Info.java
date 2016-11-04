package com.MinimalSoft.BUniversitaria.Maps;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.Models.Data_General;
import com.MinimalSoft.BUniversitaria.Models.Response_PlaceDetails;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;
import com.MinimalSoft.BUniversitaria.Web.WebActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Details_Info extends Fragment implements Callback<Response_PlaceDetails> {

    private Bundle bundle;
    private View superView;
    private Context context;
    private Data_General placeDetails;
    private RelativeLayout details_twitter, details_instagram, details_facebook, details_email, details_webpage;
    private TextView details_phone, details_about, details_address;
    private String placePhone, placeEmail, placeWebPage, placeFacebook, placeTwitter, placeInstagram,
            placeAbout, placeAddress, placeName, placeId, placeLat, placeLong;


    public Details_Info() {
        // Required empty public constructor
    }

    public static Details_Reviews newInstance() {
        Details_Reviews fragment = new Details_Reviews();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromBundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_info, container, false);
        context = rootView.getContext();
        setActivityGUI(rootView);
        getDataFromServer();
        return rootView;
    }

    private void getDataFromServer() {

        String BASE_URL = "http://api.buniversitaria.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Interfaces inter = retrofit.create(Interfaces.class);
        Call<Response_PlaceDetails> call = inter.getPlaceDetails("placeDetails", placeId);
        call.enqueue(this);
    }

    public void setActivityData() {

        placePhone = placeDetails.getPhone1();
        placeEmail = placeDetails.getEmail();
        placeWebPage = placeDetails.getWebPage();
        placeFacebook = placeDetails.getFacebook();
        placeTwitter = placeDetails.getTwitter();
        placeInstagram = placeDetails.getInstagram();
        placeAbout = placeDetails.getDescription();

        this.details_about.setText(placeAbout);
        setAddress();
        setPhone();

        validateSocial();

    }

    private void getDataFromBundle() {
        this.bundle = this.getArguments();
        placeName = bundle.getString("placeName");
        placeAddress = bundle.getString("placeAddress");
        placeId = bundle.getString("placeId");
        placeLat = bundle.getString("placeLat");
        placeLong = bundle.getString("placeLong");
    }

    private void setActivityGUI(View rootView) {

        superView = rootView.findViewById(R.id.coordinator_info);

        this.details_phone = (TextView) rootView.findViewById(R.id.details_phone);
        this.details_about = (TextView) rootView.findViewById(R.id.details_about);
        this.details_address = (TextView) rootView.findViewById(R.id.details_address);

        this.details_email = (RelativeLayout) rootView.findViewById(R.id.details_email);
        this.details_webpage = (RelativeLayout) rootView.findViewById(R.id.details_webpage);
        this.details_facebook = (RelativeLayout) rootView.findViewById(R.id.details_facebook);
        this.details_twitter = (RelativeLayout) rootView.findViewById(R.id.details_twitter);
        this.details_instagram = (RelativeLayout) rootView.findViewById(R.id.details_instagram);
    }

    private void validateSocial() {
        if (placeFacebook.equals("")) {
            details_facebook.setVisibility(View.GONE);
        } else {
            details_facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("TITLE", "Facebook de " + placeName);
                    intent.putExtra("LINK", placeFacebook);
                    startActivity(intent);
                }
            });
        }

        if (placeTwitter.equals("")) {
            details_twitter.setVisibility(View.GONE);
        } else {
            details_twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("TITLE", "Twitter de " + placeName);
                    intent.putExtra("LINK", placeTwitter);
                    startActivity(intent);
                }
            });
        }

        if (placeInstagram.equals("")) {
            details_instagram.setVisibility(View.GONE);
        } else {
            details_instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("TITLE", "Instagram de " + placeName);
                    intent.putExtra("LINK", placeInstagram);
                    startActivity(intent);
                }
            });
        }

        if (placeWebPage.equals("")) {
            details_webpage.setVisibility(View.GONE);
        } else {
            details_webpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("TITLE", "Web de " + placeName);
                    intent.putExtra("LINK", placeWebPage);
                    startActivity(intent);
                }
            });
        }

        if (placeEmail.equals("")) {
            details_email.setVisibility(View.GONE);
        } else {
            details_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{placeEmail});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Contaco desde app BU");
                    //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                    try {
                        startActivity(Intent.createChooser(i, "Contactar por correo"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), "No se encontro aplicacion para enviar correos.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onResponse(Call<Response_PlaceDetails> call, Response<Response_PlaceDetails> response) {
        int code = response.code();
        if (code == 200 && response.body().getResponse().equals("success")) {
            placeDetails = response.body().getData();
            setActivityData();
        } else {
            showSnackBar("Error al conectar con el servidor!");
        }
    }

    @Override
    public void onFailure(Call<Response_PlaceDetails> call, Throwable t) {
        showSnackBar("Error de red!");
    }

    private void showSnackBar(String msg) {
        Snackbar.make(superView, msg, Snackbar.LENGTH_LONG).show();
    }

    public void setAddress() {

        this.details_address.setText(placeAddress);

        details_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 0,0 ?q=" + placeLat + "," +
                        placeLong));
                startActivity(mapIntent);
            }
        });
    }

    public void setPhone() {

        this.details_phone.setText(placePhone);
        this.details_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", placePhone, null));
                startActivity(intent);
            }
        });
    }
}