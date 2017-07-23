package com.MinimalSoft.Joiin.Reviews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Details.DetailsActivity;
import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.ReviewsResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.kcode.bottomlib.BottomDialog;
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewDialog implements Callback<ReviewsResponse>, View.OnClickListener,
        RatingBar.OnRatingBarChangeListener, BottomDialog.OnClickListener {
    private ProgressDialog progressDialog;
    private AlertDialog dialog;

    private ImageButton removeButton;
    private ImageButton imageButton;
    private RatingBar ratingBar;
    private EditText editText;
    private TextView textView;

    private String fileName;
    private Bitmap bitmap;

    private DetailsActivity activity;
    private int userID;

    public ReviewDialog(DetailsActivity activity, int userID) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = View.inflate(activity, R.layout.dialog_review, null);

        Button submitButton = (Button) inflatedView.findViewById(R.id.review_submitButton);
        Button cancelButton = (Button) inflatedView.findViewById(R.id.review_cancelButton);
        removeButton = (ImageButton) inflatedView.findViewById(R.id.review_removeButton);
        imageButton = (ImageButton) inflatedView.findViewById(R.id.review_imageButton);
        ratingBar = (RatingBar) inflatedView.findViewById(R.id.review_ratingBar);
        textView = (TextView) inflatedView.findViewById(R.id.review_textView);
        editText = (EditText) inflatedView.findViewById(R.id.review_editText);

        String place = activity.getIntent().getExtras().getString(Joiin.PLACE_NAME_KEY);
        int typeID = activity.getIntent().getExtras().getInt(Joiin.PLACE_TYPE_KEY);
        int color = Joiin.getCategoryColor(activity, typeID);

        progressDialog = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
        dialog = new AlertDialog.Builder(activity).setView(inflatedView)
                .setCancelable(false)
                .setTitle(place)
                .create();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        submitButton.setBackgroundTintList(ColorStateList.valueOf(color));
        ratingBar.setProgressTintList(ColorStateList.valueOf(color));
        ratingBar.setOnRatingBarChangeListener(this);
        textView.setTextColor(color);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Enviando...");

        removeButton.setVisibility(View.INVISIBLE);
        removeButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        imageButton.setOnClickListener(this);

        this.activity = activity;
        this.userID = userID;
    }

    /**
     * Notification that the rating has changed. Clients can use the
     * fromUser parameter to distinguish user-initiated changes from those
     * that occurred programmatically. This will not be called continuously
     * while the user is dragging, only when the user finalizes a rating by
     * lifting the touch.
     *
     * @param ratingBar The RatingBar whose rating has changed.
     * @param rating    The current rating. This will be in the range
     *                  0..numStars.
     * @param fromUser  True if the rating change was initiated by a user's
     */
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (rating > 4f) {
            textView.setText("Me ha encantado");
        } else if (rating > 3f) {
            textView.setText("Me ha gustado");
        } else if (rating > 2f) {
            textView.setText("Esta mas o menos");
        } else if (rating > 1f) {
            textView.setText("No me ha gustado");
        } else if (rating > 0f) {
            textView.setText("No me ha gustado nada");
        } else {
            textView.setText("Asigna una calificación");
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.review_submitButton:
                int rating = (int) ratingBar.getRating();
                String text = editText.getText().toString().trim();

                if (text.length() == 0) {
                    editText.setError("Escriba una reseña");
                } else if (rating <= 0) {
                    textView.setText("Asigna una calificación para continuar");
                    //textView.setError("Asigna una calificación para continuar");
                } else {
                    dialog.dismiss();
                    progressDialog.show();

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
                    int placeID = activity.getIntent().getExtras().getInt(Joiin.PLACE_ID_KEY);

                    if (bitmap != null) {
                        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 67, byteStream);
                        byte[] bytes = byteStream.toByteArray();
                        String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                        api.putReview("putReviews", String.valueOf(userID), String.valueOf(placeID),
                                text, String.valueOf(rating), fileName, encodedImage).enqueue(this);
                    } else {
                        api.putReview("putReviews", String.valueOf(userID), String.valueOf(placeID),
                                text, String.valueOf(rating), "", "").enqueue(this);
                    }
                }
                break;

            case R.id.review_removeButton:
                imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageButton.setImageResource(R.drawable.ic_add_photo);
                removeButton.setVisibility(View.INVISIBLE);
                fileName = null;
                bitmap = null;
                break;

            case R.id.review_imageButton:
                String strings[] = {"Cámara", "Galería"};
                BottomDialog bottomDialog = BottomDialog.newInstance("Elige una opción", "Cancelar", strings);
                bottomDialog.setListener(this);
                bottomDialog.show(activity.getSupportFragmentManager(), "BOTTOM_DIALOG");
                break;

            case R.id.review_cancelButton:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void click(int i) {
        switch (i) {
            case 0:
                LongImageCameraActivity.launch(activity);
                break;

            case 1:
                /* Image Picker intent */
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivityForResult(intent, Joiin.IMAGE_PICKER_REQUEST);
                    //activity.startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), BU.IMAGE_PICKER_REQUEST);
                } else {
                    Toast.makeText(activity, "No se detecto una aplicacion para tomar imagen", Toast.LENGTH_SHORT).show();
                }
                break;
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
            showAlertMessage("Reseña enviada", "Gracias por enviar tu reseña!");
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
        dialog = new AlertDialog.Builder(activity).setTitle(title).setMessage(message).show();
    }

    public void setImage(Bitmap bitmap) {
        int placeID = activity.getIntent().getExtras().getInt(Joiin.PLACE_ID_KEY);
        String date = UnitFormatterUtility.getCurrentTime().replace(" ", "_");
        fileName = String.format(Locale.getDefault(), "%d_%d_%s", placeID, userID, date);

        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        imageButton.setImageBitmap(bitmap);

        removeButton.setVisibility(View.VISIBLE);
        this.bitmap = bitmap;
    }

    public void display() {
        dialog.show();
    }
}