package com.MinimalSoft.Joiin.Places;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.PlacesResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.MinimalSoft.Joiin.Viewer.FormViewerActivity;
import com.bumptech.glide.Glide;
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceFormFragment extends Fragment implements Callback<PlacesResponse>,
        View.OnClickListener, DialogInterface.OnClickListener {
    private Address address;
    private byte[] bitmapBytes;

    private Spinner attrSpinner;
    private Spinner typeSpinner;
    private ImageButton imageButton;
    private ProgressDialog progressDialog;

    private TextInputEditText nameField;
    private TextInputEditText aboutField;
    private TextInputEditText telephoneField;
    private TextInputEditText placeExtension;

    private TextInputEditText webField;
    private TextInputEditText mailField;
    private TextInputEditText twitterField;
    private TextInputEditText facebookField;
    private TextInputEditText instagramField;


    private TextInputEditText extField;
    private TextInputEditText emailField;
    private TextInputEditText phoneField;
    private TextInputEditText managerField;
    private TextInputEditText lastNameField;
    private TextInputEditText occupationField;

    private TextInputEditText localityField;
    private TextInputEditText adminAreaField;
    private TextInputEditText postalCodeField;
    private TextInputEditText subAdminAreaField;
    private TextInputEditText thoroughfareField;
    private TextInputEditText subThoroughfareField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_form_place, container, false);

        subThoroughfareField = (TextInputEditText) inflatedView.findViewById(R.id.form_subThoroughfareField);
        thoroughfareField = (TextInputEditText) inflatedView.findViewById(R.id.form_thoroughfareField);
        subAdminAreaField = (TextInputEditText) inflatedView.findViewById(R.id.form_subAdminAreaField);
        postalCodeField = (TextInputEditText) inflatedView.findViewById(R.id.form_postalCodeField);
        adminAreaField = (TextInputEditText) inflatedView.findViewById(R.id.form_adminAreaField);
        localityField = (TextInputEditText) inflatedView.findViewById(R.id.form_localityField);

        placeExtension = (TextInputEditText) inflatedView.findViewById(R.id.form_extensionField);
        telephoneField = (TextInputEditText) inflatedView.findViewById(R.id.form_telephoneField);
        nameField = (TextInputEditText) inflatedView.findViewById(R.id.form_placeNameField);
        aboutField = (TextInputEditText) inflatedView.findViewById(R.id.form_aboutField);
        imageButton = (ImageButton) inflatedView.findViewById(R.id.form_imageButton);

        instagramField = (TextInputEditText) inflatedView.findViewById(R.id.form_instagramField);
        facebookField = (TextInputEditText) inflatedView.findViewById(R.id.form_facebookField);
        twitterField = (TextInputEditText) inflatedView.findViewById(R.id.form_twitterField);
        mailField = (TextInputEditText) inflatedView.findViewById(R.id.form_mailField);
        webField = (TextInputEditText) inflatedView.findViewById(R.id.form_webField);

        occupationField = (TextInputEditText) inflatedView.findViewById(R.id.form_occupationField);
        lastNameField = (TextInputEditText) inflatedView.findViewById(R.id.form_lastNameField);
        managerField = (TextInputEditText) inflatedView.findViewById(R.id.form_managerField);
        phoneField = (TextInputEditText) inflatedView.findViewById(R.id.form_phoneField);
        emailField = (TextInputEditText) inflatedView.findViewById(R.id.form_emailField);
        extField = (TextInputEditText) inflatedView.findViewById(R.id.form_extField);
        attrSpinner = (Spinner) inflatedView.findViewById(R.id.form_attrSpinner);
        typeSpinner = (Spinner) inflatedView.findViewById(R.id.form_typeSpinner);

        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Enviando...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        imageButton.setOnClickListener(this);
        setHasOptionsMenu(true);
        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        address = intent.getParcelableExtra(FormViewerActivity.ADDRESS_KEY);

        try {
            Geocoder geocoder = new Geocoder(getActivity(), address.getLocale());
            List<Address> addressList = geocoder.getFromLocation(address.getLatitude(),
                    address.getLongitude(), 1);
            address = addressList.get(0);

            thoroughfareField.setText(address.getThoroughfare());
            subThoroughfareField.setText(address.getSubThoroughfare());
            subAdminAreaField.setText(address.getSubAdminArea());
            adminAreaField.setText(address.getAdminArea());
            postalCodeField.setText(address.getPostalCode());

            String locality = address.getSubLocality();
            localityField.setText(locality == null ? address.getLocality() : locality);

            nameField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                Uri uri = data.getData();
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteStream);
                bitmapBytes = byteStream.toByteArray();
                inputStream.close();

                imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).loadFromMediaStore(uri).into(imageButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.form_acceptItem && verifyRequiredData()) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Se verificará que la información proporcionada cumpla con los " +
                            "requisitos necesarios y será aprobado en un lapso no mayor a 24 horas.")
                    .setPositiveButton("Continuar", this)
                    .setNegativeButton("Cancelar", null)
                    .setTitle("Algo importante")
                    .create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.form_imageButton:
                CharSequence strings[] = {"Cámara", "Galería"};

                new AlertDialog.Builder(getActivity())
                        .setTitle("Elige Una Opción")
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        LongImageCameraActivity.launch(getActivity());
                                        break;
                                    case 1:
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
                                        startActivityForResult(intent, Joiin.IMAGE_PICKER_REQUEST);
                                        break;
                                }
                            }
                        })
                        .create().show();
                break;
        }
    }

    @Override
    public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().getResponse().equals("success")) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "Listo", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
                getActivity().finish();
            } else {
                showAlertMessage(response.body().getResponse(), response.body().getMessage());
            }
        } else {
            showAlertMessage(null, response.message());
        }
    }

    @Override
    public void onFailure(Call<PlacesResponse> call, Throwable t) {
        showAlertMessage(t.getMessage(), t.getLocalizedMessage());
        t.printStackTrace();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        progressDialog.show();

        String placeName = nameField.getText().toString();
        String street = thoroughfareField.getText().toString();
        String neighborhood = localityField.getText().toString();
        String county = subAdminAreaField.getText().toString();
        String about = aboutField.getText().toString();

        String number = subThoroughfareField.getText().toString().trim();
        String state = adminAreaField.getText().toString().trim();
        String zip = postalCodeField.getText().toString().trim();
        String telephone = telephoneField.getText().toString().trim();
        String extension = placeExtension.getText().toString().trim();

        String mail = mailField.getText().toString().trim();
        String webPage = webField.getText().toString().trim();
        String twitter = twitterField.getText().toString().trim();
        String facebook = facebookField.getText().toString().trim();
        String instagram = instagramField.getText().toString().trim();

        String ext = extField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String manager = managerField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String occupation = occupationField.getText().toString().trim();

        String phoneType = typeSpinner.getSelectedItem().toString();
        String gender = attrSpinner.getSelectedItemPosition() == 0 ? "M" : "F";

        SharedPreferences settings = getActivity().getSharedPreferences(Joiin.USER_PREFERENCES, Context.MODE_PRIVATE);
        Intent intent = getActivity().getIntent();

        int placeType = intent.getIntExtra(Joiin.PLACE_TYPE_KEY, Joiin.NO_VALUE);
        int userID = settings.getInt(Joiin.USER_ID, Joiin.NO_VALUE);

        String encodedImage = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);

        String name = placeName.replace(" ", "_");
        String date = UnitFormatterUtility.getCurrentTime().replace(" ", "_");
        String fileName = String.format(Locale.getDefault(), "%d_%s_%s", placeType, name, date);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);

        api.putPlace("put", String.valueOf(placeType), String.valueOf(userID), placeName,
                street, number, neighborhood, county, state, "México", zip, webPage,
                facebook, twitter, instagram, about, String.valueOf(address.getLatitude()),
                String.valueOf(address.getLongitude()), "3", manager, lastName, gender,
                phone, ext, phoneType, email, occupation, encodedImage, fileName).enqueue(this);
    }

    private boolean verifyRequiredData() {
        String s = "Campo requerido";

        String placeName = nameField.getText().toString().trim();
        String street = thoroughfareField.getText().toString().trim();
        String neighborhood = localityField.getText().toString().trim();
        String county = subAdminAreaField.getText().toString().trim();
        String about = aboutField.getText().toString().trim();

        if (placeName.isEmpty()) {
            nameField.setError(s);
            nameField.requestFocus();
        } else if (street.isEmpty()) {
            thoroughfareField.setError(s);
            thoroughfareField.requestFocus();
        } else if (neighborhood.isEmpty()) {
            localityField.setError(s);
            localityField.requestFocus();
        } else if (county.isEmpty()) {
            subAdminAreaField.setError(s);
            subAdminAreaField.requestFocus();
        } else if (about.isEmpty()) {
            aboutField.setError(s);
            aboutField.requestFocus();
        } else if (bitmapBytes == null) {
            showAlertMessage("Agrege una imagen", null);
        } else {
            nameField.setText(placeName);
            thoroughfareField.setText(street);
            localityField.setText(neighborhood);
            subAdminAreaField.setText(county);
            aboutField.setText(about);
            return true;
        }

        return false;
    }

    private void showAlertMessage(String title, String message) {
        progressDialog.hide();

        new AlertDialog.Builder(getActivity())
                .setPositiveButton("Ok", null)
                .setMessage(message)
                .setTitle(title)
                .create().show();
    }

    public void setImage(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteStream);
        bitmapBytes = byteStream.toByteArray();

        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getActivity()).load(path).into(imageButton);
    }
}