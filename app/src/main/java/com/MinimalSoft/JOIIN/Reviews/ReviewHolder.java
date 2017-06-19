package com.MinimalSoft.JOIIN.Reviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.JOIIN.BU;
import com.MinimalSoft.JOIIN.Details.DetailsActivity;
import com.MinimalSoft.JOIIN.Facebook.FacebookData;
import com.MinimalSoft.JOIIN.R;
import com.MinimalSoft.JOIIN.Responses.ReviewsResponse;
import com.MinimalSoft.JOIIN.Services.MinimalSoftServices;
import com.MinimalSoft.JOIIN.Utilities.UnitFormatterUtility;
import com.MinimalSoft.JOIIN.Widgets.CircleImageView;
import com.bumptech.glide.Glide;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ReviewHolder extends RecyclerView.ViewHolder implements GraphRequest.Callback, View.OnClickListener, PopupMenu.OnMenuItemClickListener, Callback<ReviewsResponse> {
    private final int STARS_COUNT = 5;
    private int placeType;
    private int reviewID;
    private int placeID;
    private int userID;
    private int position;

    private Context context;
    private View bottomLine;
    private Snackbar snackbar;

    private TextView moreLabel;
    private TextView dateLabel;
    private TextView reviewText;
    private TextView userNameLabel;
    private TextView placeNameLabel;
    private ImageButton imageButton;
    private CircleImageView profileImage;
    private ReactionsHandler reactionHandler;
    private ImageView imageView, stars[] = new ImageView[STARS_COUNT];

    private ReviewsAdapter adapter;

    ReviewHolder(View itemView, boolean singlePlaceReviews, ReviewsAdapter reviewsAdapter) {
        super(itemView);

        int REACTIONS_COUNT = 2; // There are only two types of reactions.

        TextView[] countLabels = new TextView[REACTIONS_COUNT];
        LikeButton[] reactionButtons = new LikeButton[REACTIONS_COUNT];

        countLabels[0] = (TextView) itemView.findViewById(R.id.review_likesLabel);
        countLabels[1] = (TextView) itemView.findViewById(R.id.review_dislikesLabel);
        reactionButtons[0] = (LikeButton) itemView.findViewById(R.id.review_likeButton);
        reactionButtons[1] = (LikeButton) itemView.findViewById(R.id.review_dislikeButton);

        stars[0] = (ImageView) itemView.findViewById(R.id.star_one);
        stars[1] = (ImageView) itemView.findViewById(R.id.star_two);
        stars[2] = (ImageView) itemView.findViewById(R.id.star_three);
        stars[3] = (ImageView) itemView.findViewById(R.id.star_four);
        stars[4] = (ImageView) itemView.findViewById(R.id.star_five);

        reactionHandler = new ReactionsHandler(reactionButtons, countLabels);

        LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.review_layout);
        profileImage = (CircleImageView) itemView.findViewById(R.id.review_profileImage);
        placeNameLabel = (TextView) itemView.findViewById(R.id.review_placeLabel);
        userNameLabel = (TextView) itemView.findViewById(R.id.review_nameLabel);
        imageButton = (ImageButton) itemView.findViewById(R.id.review_button);
        imageView = (ImageView) itemView.findViewById(R.id.review_imageView);
        reviewText = (TextView) itemView.findViewById(R.id.review_textLabel);
        dateLabel = (TextView) itemView.findViewById(R.id.review_dateLabel);
        moreLabel = (TextView) itemView.findViewById(R.id.review_moreLabel);
        bottomLine = itemView.findViewById(R.id.review_line);
        context = itemView.getContext();

        snackbar = Snackbar.make(itemView, "¿Seguro que desea eliminar este review?"
                , Snackbar.LENGTH_LONG).setAction("SI", this);
        imageButton.setOnClickListener(this);

        if (singlePlaceReviews) {
            placeNameLabel.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
        } else {
            layout.setOnClickListener(this);
        }

        this.adapter = reviewsAdapter;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.review_textLabel:
                reviewText.setEllipsize(null);
                reviewText.setClickable(false);
                reviewText.setMaxLines(Integer.MAX_VALUE);

                moreLabel.setVisibility(View.GONE);
                break;

            case R.id.review_button:
                PopupMenu popupMenu = new PopupMenu(context, imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.actions_review, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
                break;

            case R.id.review_layout:
                Bundle bundle = new Bundle();
                bundle.putInt(BU.PLACE_ID_KEY, placeID);
                bundle.putInt(BU.PLACE_TYPE_KEY, placeType);
                bundle.putString(BU.PLACE_NAME_KEY, placeNameLabel.getText().toString());

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtras(bundle);

                context.startActivity(intent);
                break;

            default: // Snackbar button
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
                api.deleteReview("deleteReview", String.valueOf(reviewID), String.valueOf(userID)).enqueue(this);
        }
    }

    /**
     * This method will be invoked when a menu item is clicked if the item
     * itself did not already handle the event.
     *
     * @param item the menu item that was clicked
     * @return {@code true} if the event was handled, {@code false}
     * otherwise
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        snackbar.show();
        return true;
    }

    /*---------------------------------- Callback Methods ----------------------------------*/

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
            adapter.removeAtPosition(position);
            Toast.makeText(context, "Review eliminado.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
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
        t.printStackTrace();
    }

    /*---------------- GraphRequest Methods ----------------*/

    /**
     * The method that will be called when a request completes.
     *
     * @param response the Response of this request, which may include error information if the
     *                 request was unsuccessful
     */
    @Override
    public void onCompleted(GraphResponse response) {
        if (response.getError() == null) {
            Type type = new TypeToken<FacebookData.Picture>() {
            }.getType();
            FacebookData.Picture picture = new Gson().fromJson(response.getJSONObject().toString(), type);
            String url = (picture.getData() != null) ? picture.getData().getUrl() : "";
            Glide.with(context).load(url).placeholder(R.drawable.default_profile).into(profileImage);
        } else {
            Toast.makeText(context, response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void setImages(String imageName, String facebookURL) {
        int size = (int) context.getResources().getDimension(R.dimen.profile_image_size);

        //if (facebookID != null) {
        //    url = "http://graph.facebook.com/" + facebookID + "/picture?width=" + size + "&height=" + size;
        //    Glide.with(context).load(url).placeholder(R.drawable.default_profile).into(profileImage);
        //} else {
            /* This code shouldn't exist! */
        String[] chunks = facebookURL.split(String.valueOf('/'));
        if (chunks.length == 5) {
            facebookURL = "http://graph.facebook.com/" + chunks[3] + "/picture?width=" + size + "&height=" + size;
        }

        Glide.with(context).load(facebookURL).placeholder(R.drawable.default_profile).into(profileImage);
        //}

        if (!imageName.equals("No image")) {
            //byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            imageView.setVisibility(View.VISIBLE);
            String imageURL = BU.API_URL + "/imagenes/reviews/" + imageName;
            Glide.with(context).load(imageURL).placeholder(R.drawable.default_image).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        //Bundle parameters = new Bundle();
        //parameters.putString("type", "square");
        //parameters.putBoolean("redirect", false);
        //parameters.putInt("width", profileImage.getWidth());
        //parameters.putInt("height", profileImage.getHeight());

        //new GraphRequest(AccessToken.getCurrentAccessToken(), "/1333050543419193/picture", parameters, HttpMethod.GET, this).executeAsync();
    }

    protected void setReactionsData(int reviewID, int reaction, int likes, int dislikes) {
        List<Integer> amounts = Arrays.asList(likes, dislikes);
        reactionHandler.setData(userID, reviewID, reaction, amounts);
    }

    protected void setReview(String text) {
        int maxLines = 6; // Maximum lines count for a preview.

        reviewText.setText(text);

        if (reviewText.getLineCount() > maxLines) {
            reviewText.setClickable(true);
            reviewText.setMaxLines(maxLines);
            reviewText.setOnClickListener(this);
            reviewText.setEllipsize(TextUtils.TruncateAt.END);

            moreLabel.setVisibility(View.VISIBLE);
        } else {
            moreLabel.setVisibility(View.GONE);
        }
    }

    protected void setStars(int rating) {
        for (int i = 0; i < STARS_COUNT; i++) {
            if (i <= (rating - 1)) {
                stars[i].setImageResource(R.drawable.star_on);
            } else {
                stars[i].setImageResource(R.drawable.star_off);
            }
        }
    }

    protected void setBasicInfo(int reviewID, String userName, String date, int reviewUserID, int userID, int position) {
        dateLabel.setText(UnitFormatterUtility.getTimeDescription(date));
        userNameLabel.setText(userName);

        if (userID != reviewUserID) {
            imageButton.setVisibility(View.INVISIBLE);
            imageButton.setEnabled(false);
        } else {
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setEnabled(true);
        }

        this.position = position;
        this.reviewID = reviewID;
        this.userID = userID;
    }

    protected void setPlaceInfo(int placeID, String placeName, int typeID) {
        placeType = typeID;
        this.placeID = placeID;
        placeNameLabel.setText(placeName);
        placeNameLabel.setTextColor(BU.getCategoryColor(context, typeID));
        bottomLine.setBackgroundColor(BU.getCategoryColor(context, typeID));
    }
}