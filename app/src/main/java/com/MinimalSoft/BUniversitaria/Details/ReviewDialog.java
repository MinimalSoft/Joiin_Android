package com.MinimalSoft.BUniversitaria.Details;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Models.DatalessResponse;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.Interfaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ReviewDialog implements View.OnClickListener, Callback<DatalessResponse> {
    private DetailsActivity detailsActivity;
    private ReviewsFragment reviewsFragment;

    private ProgressDialog progressDialog;
    private AlertDialog dialog;

    private RatingBar ratingBar;
    private TextView textView;
    private EditText editText;

    private int userID;

    public ReviewDialog(DetailsActivity detailsActivity, ReviewsFragment reviewsFragment, int userID) {
        LayoutInflater inflater = detailsActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_review, null);

        editText = (EditText) dialogView.findViewById(R.id.review_inputText);
        ratingBar = (RatingBar) dialogView.findViewById(R.id.review_ratingBar);
        textView = (TextView) dialogView.findViewById(R.id.review_errorTextView);

        progressDialog = new ProgressDialog(detailsActivity, ProgressDialog.STYLE_SPINNER);
        AlertDialog.Builder builder = new AlertDialog.Builder(detailsActivity);

        this.detailsActivity = detailsActivity;
        this.reviewsFragment = reviewsFragment;
        this.userID = userID;

        builder.setTitle(detailsActivity.getIntent().getStringExtra("PLACE_NAME"));
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Enviar", null);
        builder.setView(dialogView);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Enviando...");
        progressDialog.setIndeterminate(true);

        dialog = builder.create();
        dialog.show();

        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(this);
        textView.setVisibility(View.GONE);
    }

    /*---------------------------------- Callback Methods ----------------------------------*/
    @Override
    public void onResponse(Call<DatalessResponse> call, Response<DatalessResponse> response) {
        if (response.isSuccessful()) {
            reviewsFragment.onRefresh();
            showAlertMessage("Reseña enviada", "Gracias por enviar tu reseña!! \nTe amamos");
        } else {
            showAlertMessage("Error al enviar la reseña", "!Algo salio mal! verifica tu conexión a internet.");
        }
    }

    @Override
    public void onFailure(Call<DatalessResponse> call, Throwable t) {
        showAlertMessage("Error al enviar la reseña", "!Algo salio mal! verifica tu conexión a internet.");
    }

    /*----OnClickListener methods----*/
    @Override
    public void onClick(View v) {
        int rating = (int) ratingBar.getRating();
        String text = editText.getText().toString().trim();

        if (text.length() == 0) {
            //editText.setError("El campo no puede estar vacío.");
            textView.setText("El campo no puede estar vacío.");
        } else if (rating <= 0) {
            textView.setText("Asigna una calificación para continuar.");
        } else {
            dialog.dismiss();
            progressDialog.show();

            int placeID = detailsActivity.getIntent().getIntExtra("ID_PLACE", -1);
            String urlAPI = ratingBar.getResources().getString(R.string.server_api);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
            Interfaces minimalSoftAPI = retrofit.create(Interfaces.class);
            minimalSoftAPI.putReview("putReviews", String.valueOf(userID), String.valueOf(placeID), text, String.valueOf(rating)).enqueue(this);
        }

        textView.setVisibility(View.VISIBLE);
    }

    private void showAlertMessage(String title, String message) {
        progressDialog.hide();
        new AlertDialog.Builder(detailsActivity).setTitle(title).setMessage(message).show();
    }
}
