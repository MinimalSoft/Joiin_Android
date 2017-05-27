package com.MinimalSoft.BUniversitaria.Details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.DetailsResponse;
import com.MinimalSoft.BUniversitaria.Responses.PlaceData;
import com.MinimalSoft.BUniversitaria.Services.MinimalSoftServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformationFragment extends Fragment implements Callback<DetailsResponse>, View.OnClickListener {
    private Call<DetailsResponse> apiCall;
    private ImageButton instagramButton;
    private ImageButton facebookButton;
    private ImageButton twitterButton;
    private ImageButton webPageButton;
    private ImageButton emailButton;
    private TextView addressLabel;
    private TextView aboutLabel;
    private TextView phoneLabel;
    private PlaceData placeData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_information, container, false);

        instagramButton = (ImageButton) inflatedView.findViewById(R.id.information_instagramButton);
        facebookButton = (ImageButton) inflatedView.findViewById(R.id.information_facebookButton);
        twitterButton = (ImageButton) inflatedView.findViewById(R.id.information_twitterButton);
        webPageButton = (ImageButton) inflatedView.findViewById(R.id.information_webButton);
        emailButton = (ImageButton) inflatedView.findViewById(R.id.information_emailButton);
        addressLabel = (TextView) inflatedView.findViewById(R.id.information_addressLabel);
        aboutLabel = (TextView) inflatedView.findViewById(R.id.information_aboutLabel);
        phoneLabel = (TextView) inflatedView.findViewById(R.id.information_phoneLabel);

        int placeID = getArguments().getInt(BU.PLACE_ID_KEY);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
        apiCall = api.getPlaceDetails("placeDetails", String.valueOf(placeID));

        instagramButton.setOnClickListener(this);
        facebookButton.setOnClickListener(this);
        twitterButton.setOnClickListener(this);
        webPageButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);

        instagramButton.setVisibility(View.GONE);
        facebookButton.setVisibility(View.GONE);
        twitterButton.setVisibility(View.GONE);
        webPageButton.setVisibility(View.GONE);
        emailButton.setVisibility(View.GONE);

        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiCall.enqueue(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        apiCall.cancel();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        Uri uri;

        try {
            switch (v.getId()) {
                case R.id.information_phoneLabel:
                    uri = Uri.fromParts("tel", String.valueOf(placeData.getPhone1()), null);
                    intent = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(intent);
                    break;

                case R.id.information_addressLabel:
                    uri = Uri.parse("geo: 0,0 ?q=" + placeData.getLatitude() + ',' + placeData.getLongitude());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.information_facebookButton:
                    uri = Uri.parse(placeData.getFacebook());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.information_webButton:
                    uri = Uri.parse(placeData.getWebPage());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.information_emailButton:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, placeData.getEmail());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto desde Brújula Universitaria");
                    startActivity(Intent.createChooser(intent, "Contactar por correo"));
                    break;

                case R.id.information_instagramButton:
                    uri = Uri.parse(placeData.getInstagram());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;

                case R.id.information_twitterButton:
                    uri = Uri.parse(placeData.getTwitter());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
        DetailsActivity activity = (DetailsActivity) getActivity();

        if (response.isSuccessful()) {
            placeData = response.body().getData();

            activity.loadImage(placeData.getImage());

            aboutLabel.setText(placeData.getDescription());
            phoneLabel.setText(String.valueOf(placeData.getPhone1()));

            String address = placeData.getStreet() + " # "
                    + placeData.getNumber() + " Colonia "
                    + placeData.getNeighborhood();
            addressLabel.setText(address);

            phoneLabel.setOnClickListener(this);
            addressLabel.setOnClickListener(this);

            if (placeData.getInstagram().length() > 0) {
                instagramButton.setVisibility(View.VISIBLE);
            }

            if (placeData.getFacebook().length() > 0) {
                facebookButton.setVisibility(View.VISIBLE);
            }

            if (placeData.getTwitter().length() > 0) {
                twitterButton.setVisibility(View.VISIBLE);
            }

            if (placeData.getWebPage().length() > 0) {
                webPageButton.setVisibility(View.VISIBLE);
            }

            if (placeData.getEmail().length() > 0) {
                emailButton.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<DetailsResponse> call, Throwable t) {
        t.printStackTrace();
    }

    protected static InformationFragment newInstance(int placeID) {
        InformationFragment instance = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BU.PLACE_ID_KEY, placeID);
        instance.setArguments(bundle);
        return instance;
    }
}