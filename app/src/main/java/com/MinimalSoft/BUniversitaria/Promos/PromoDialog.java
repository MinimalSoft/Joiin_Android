package com.MinimalSoft.BUniversitaria.Promos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Main.MainActivity;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.PromoData;
import com.MinimalSoft.BUniversitaria.Responses.TransactionResponse;
import com.MinimalSoft.BUniversitaria.Services.MinimalSoftServices;
import com.MinimalSoft.BUniversitaria.Widgets.SlideView;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class PromoDialog implements SlideView.OnFinishListener, Callback<TransactionResponse> {
    private ProgressDialog progressDialog;
    private PromoData promoData;
    private AlertDialog dialog;
    private int userID;

    PromoDialog(Context context, PromoData promo, int userID) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = View.inflate(context, R.layout.dialog_promo, null);

        ImageView imageView = (ImageView) inflatedView.findViewById(R.id.promo_imageView);
        TextView placeLabel = (TextView) inflatedView.findViewById(R.id.promo_placeLabel);
        TextView coinsLabel = (TextView) inflatedView.findViewById(R.id.promo_coinsLabel);
        TextView titleLabel = (TextView) inflatedView.findViewById(R.id.promo_titleLabel);
        SlideView slideView = (SlideView) inflatedView.findViewById(R.id.promo_slideButton);

        //descriptionText.setMovementMethod(new ScrollingMovementMethod());

        String imageURL = BU.API_URL + "/imagenes/promos/" + promo.getBanner();
        Glide.with(context).load(imageURL).placeholder(R.drawable.default_image).into(imageView);

        placeLabel.setText(promo.getPlaceName() + " te ofrece:");
        coinsLabel.setText(String.valueOf(promo.getPrice()));
        titleLabel.setText(promo.getDescription());
        slideView.setOnFinishListener(this);

        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Procesando...");

        dialog = new AlertDialog.Builder(context)
                .setView(inflatedView).create();
        this.userID = userID;
        promoData = promo;
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
    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
        if (response.isSuccessful()) {
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
    public void onFailure(Call<TransactionResponse> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onFinish() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
        //api.withdraw("withdraw", String.valueOf(userID), String.valueOf(promoData.getCode()))
        //progressDialog.show();

        Context context = dialog.getContext();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    protected void display() {
        dialog.show();
    }
}