package com.MinimalSoft.BUniversitaria.Promos;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.PromoData;
import com.bumptech.glide.Glide;

class PromoDialog {
    private AlertDialog dialog;

    PromoDialog(Context context, PromoData promo) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = View.inflate(context, R.layout.dialog_promo, null);

        ImageView imageView = (ImageView) inflatedView.findViewById(R.id.promo_imageView);
        TextView placeLabel = (TextView) inflatedView.findViewById(R.id.promo_placeLabel);
        TextView coinsLabel = (TextView) inflatedView.findViewById(R.id.promo_coinsLabel);
        TextView titleLabel = (TextView) inflatedView.findViewById(R.id.promo_titleLabel);

        //descriptionText.setMovementMethod(new ScrollingMovementMethod());

        String imageURL = BU.API_URL + "/imagenes/promos/" + promo.getBanner();
        Glide.with(context).load(imageURL).placeholder(R.drawable.default_image).into(imageView);

        placeLabel.setText(promo.getPlaceName() + " te ofrece:");
        coinsLabel.setText(String.valueOf(promo.getPrice()));
        titleLabel.setText(promo.getDescription());

        dialog = new AlertDialog.Builder(context)
                .setView(inflatedView).create();
    }

    protected void display() {
        dialog.show();
    }
}