package com.MinimalSoft.BUniversitaria.Reviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Details.DetailsActivity;
import com.MinimalSoft.BUniversitaria.Facebook.FacebookData;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Utilities.UnitFormatterUtility;
import com.bumptech.glide.Glide;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ReviewHolder extends RecyclerView.ViewHolder implements GraphRequest.Callback, View.OnClickListener {
    private final int STARS_COUNT = 5;
    private int placeType;
    private int placeID;

    private Context context;
    private View bottomLine;
    private TextView moreLabel;
    private TextView dateLabel;
    private TextView reviewText;
    private TextView userNameLabel;
    private TextView placeNameLabel;
    private CircleImageView profileImage;
    private ReactionsHandler reactionHandler;
    private ImageView imageView, stars[] = new ImageView[STARS_COUNT];

    ReviewHolder(View itemView, boolean singlePlaceReviews) {
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
        imageView = (ImageView) itemView.findViewById(R.id.review_imageView);
        reviewText = (TextView) itemView.findViewById(R.id.review_textLabel);
        dateLabel = (TextView) itemView.findViewById(R.id.review_dateLabel);
        moreLabel = (TextView) itemView.findViewById(R.id.review_moreLabel);

        bottomLine = itemView.findViewById(R.id.review_line);
        context = itemView.getContext();

        if (singlePlaceReviews) {
            placeNameLabel.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
        } else {
            layout.setOnClickListener(this);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.review_textLabel) {
            reviewText.setEllipsize(null);
            reviewText.setClickable(false);
            reviewText.setMaxLines(Integer.MAX_VALUE);

            moreLabel.setVisibility(View.GONE);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(BU.PLACE_ID_KEY, placeID);
            bundle.putInt(BU.PLACE_TYPE_KEY, placeType);
            bundle.putString(BU.PLACE_NAME_KEY, placeNameLabel.getText().toString());

            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }
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

    protected void setImages(String facebookID, String url) {
        if (facebookID != null) {
            url = "http://graph.facebook.com/" + facebookID + "/picture?width="
                    + profileImage.getWidth() + "&height=" + profileImage.getHeight();
            Glide.with(context).load(url).placeholder(R.drawable.default_profile).into(profileImage);
        } else {
            /* This code shouldn't exist! */
            String[] chunks = url.split(String.valueOf('/'));
            if (chunks.length == 5) {
                url = "http://graph.facebook.com/" + chunks[3] + "/picture?width="
                        + profileImage.getWidth() + "&height=" + profileImage.getHeight();
            }

            Glide.with(context).load(url).placeholder(R.drawable.default_profile).into(profileImage);
        }

        imageView.setVisibility(View.GONE);

        //Bundle parameters = new Bundle();
        //parameters.putString("type", "square");
        //parameters.putBoolean("redirect", false);
        //parameters.putInt("width", profileImage.getWidth());
        //parameters.putInt("height", profileImage.getHeight());

        //new GraphRequest(AccessToken.getCurrentAccessToken(), "/1333050543419193/picture", parameters, HttpMethod.GET, this).executeAsync();
    }

    protected void setReactionsData(int userID, int reviewID, int reaction, int likes, int dislikes) {
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

    protected void setBasicInfo(String userName, String date) {
        dateLabel.setText(UnitFormatterUtility.getTimeDescription(date));
        userNameLabel.setText(userName);
    }

    protected void setPlaceInfo(int placeID, String placeName, int typeID) {
        placeType = typeID;
        this.placeID = placeID;
        placeNameLabel.setText(placeName);
        bottomLine.setBackgroundColor(BU.getCategoryColor(context, typeID));
    }
}