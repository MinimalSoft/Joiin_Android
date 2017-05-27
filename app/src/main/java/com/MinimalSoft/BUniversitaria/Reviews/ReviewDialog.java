package com.MinimalSoft.BUniversitaria.Reviews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.ReviewsResponse;
import com.MinimalSoft.BUniversitaria.Services.MinimalSoftServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewDialog implements Callback<ReviewsResponse>, View.OnClickListener {
    private ProgressDialog progressDialog;
    private ReviewsFragment fragment;
    private AlertDialog dialog;
    private RatingBar ratingBar;
    private TextView textView;
    private EditText editText;

    public ReviewDialog(ReviewsFragment fragment) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = View.inflate(fragment.getActivity(), R.layout.dialog_review, null);

        ratingBar = (RatingBar) inflatedView.findViewById(R.id.review_ratingBar);
        textView = (TextView) inflatedView.findViewById(R.id.review_errorLabel);
        editText = (EditText) inflatedView.findViewById(R.id.review_inputText);

        progressDialog = new ProgressDialog(fragment.getActivity(), ProgressDialog.STYLE_SPINNER);
        dialog = new AlertDialog.Builder(fragment.getActivity()).setView(inflatedView)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Enviar", null)
                .setCancelable(false)
                .create();

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Enviando...");
        this.fragment = fragment;
    }

    public void display(String placeName) {
        textView.setVisibility(View.GONE);

        dialog.setTitle(placeName);
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        textView.setVisibility(View.VISIBLE);
        int rating = (int) ratingBar.getRating();
        String text = editText.getText().toString().trim();

        if (text.length() == 0) {
            //editText.setError("El campo no puede estar vacío.");
            textView.setText("Escriba una reseña.");
        } else if (rating <= 0) {
            textView.setText("Asigna una calificación para continuar.");
        } else {
            dialog.dismiss();
            progressDialog.show();

            Bundle arguments = fragment.getArguments();
            int userID = arguments.getInt(BU.USER_ID);
            int placeID = arguments.getInt(BU.PLACE_ID_KEY);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
            api.putReview("putReviews", String.valueOf(userID), String.valueOf(placeID),
                    text, String.valueOf(rating)).enqueue(this);
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
    public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
        if (response.isSuccessful()) {
            fragment.onRefresh();
            showAlertMessage("Reseña enviada", "Gracias por enviar tu reseña!! \nTe amamos");
        } else {
            showAlertMessage(null, response.message());
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
    public void onFailure(Call<ReviewsResponse> call, Throwable t) {
        showAlertMessage(t.getMessage(), t.getLocalizedMessage());
        t.printStackTrace();
    }

    private void showAlertMessage(String title, String message) {
        progressDialog.hide();

        dialog = new AlertDialog.Builder(fragment.getActivity())
                .setTitle(title).setMessage(message).show();
    }
}