package com.MinimalSoft.BUniversitaria.RecyclerPosts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Details.DetailsActivity;
import com.MinimalSoft.BUniversitaria.Models.ReviewsData;
import com.MinimalSoft.BUniversitaria.R;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    /* A post can show a maximum of 256 characters long
     * There are only two types of reactions. */
    private final int MAX_CHARACTERS = 256;
    private final int REACTIONS_COUNT = 2;
    private final int STARS_COUNT = 5;
    private ReviewsData postData;

    private ImageView stars[] = new ImageView[STARS_COUNT];
    private ReactionHandler reactionHandler;
    private CircleImageView profileImage;
    private LinearLayout reviewLayout;
    private TextView placeNameText;
    private TextView userNameText;
    private TextView dateTimeText;
    private TextView reviewLabel;
    private TextView reviewText;
    private Context context;
    private View bottomLine;

    public PostHolder(View itemView) {
        super(itemView);

        TextView[] counterFields = new TextView[REACTIONS_COUNT];
        LikeButton[] reactionButtons = new LikeButton[REACTIONS_COUNT];

        reactionButtons[1] = (LikeButton) itemView.findViewById(R.id.post_dislikeButton);
        reactionButtons[0] = (LikeButton) itemView.findViewById(R.id.post_likeButton);
        counterFields[1] = (TextView) itemView.findViewById(R.id.post_textDislikes);
        counterFields[0] = (TextView) itemView.findViewById(R.id.post_textLikes);

        reviewLayout = (LinearLayout) itemView.findViewById(R.id.post_reviewLayout);
        profileImage = (CircleImageView) itemView.findViewById(R.id.post_image);
        dateTimeText = (TextView) itemView.findViewById(R.id.post_textDateTime);
        placeNameText = (TextView) itemView.findViewById(R.id.post_textPlace);
        reviewLabel = (TextView) itemView.findViewById(R.id.post_reviewLabel);
        userNameText = (TextView) itemView.findViewById(R.id.post_textName);
        reviewText = (TextView) itemView.findViewById(R.id.post_textReview);

        reactionHandler = new ReactionHandler(reactionButtons, counterFields);

        stars[0] = (ImageView) itemView.findViewById(R.id.star_1);
        stars[1] = (ImageView) itemView.findViewById(R.id.star_2);
        stars[2] = (ImageView) itemView.findViewById(R.id.star_3);
        stars[3] = (ImageView) itemView.findViewById(R.id.star_4);
        stars[4] = (ImageView) itemView.findViewById(R.id.star_5);

        bottomLine = itemView.findViewById(R.id.post_line);
        context = itemView.getContext();
    }

    /*----OnClickListener methods----*/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post_textReview) {
            reviewText.setText(postData.getText());
            reviewLabel.setVisibility(View.GONE);
            reviewText.setClickable(false);
        } else {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("PLACE_NAME", postData.getPlaceName());
            intent.putExtra("PLACE_ID", postData.getIdPlace());
            context.startActivity(intent);
        }
    }

    protected void setData(ReviewsData postData, int userId) {
        Picasso.with(context).load(Uri.parse(postData.getFbImage())).placeholder(R.drawable.default_profile).into(profileImage);
        dateTimeText.setText(postData.getDate().replace(" ", " a las "));
        placeNameText.setText(postData.getPlaceName());
        userNameText.setText(postData.getName());
        setTypeColors(postData.getIdType());
        setStars(postData.getStars());

        int amounts[] = new int[REACTIONS_COUNT];
        amounts[1] = postData.getDislikes();
        amounts[0] = postData.getLikes();
        String text = postData.getText();

        if (text.length() > MAX_CHARACTERS) {
            reviewText.setClickable(true);
            reviewText.setOnClickListener(this);
            reviewLabel.setVisibility(View.VISIBLE);
            reviewText.setText(text.substring(0, MAX_CHARACTERS));
        } else {
            reviewLabel.setVisibility(View.GONE);
            reviewText.setText(text);
        }

        this.postData = postData;
        reviewLayout.setOnClickListener(this);
        placeNameText.setOnClickListener(this);
        reactionHandler.setData(userId, postData.getIdReview(), postData.getReaction(), amounts);
    }

    protected void setText(String text) {
        placeNameText.setText(text);
        dateTimeText.setText(text);
        userNameText.setText(text);
        reviewText.setText(text);
    }

    private void setStars(int rating) {
        for (int i = 0; i < STARS_COUNT; i++) {
            if (i <= (rating - 1)) {
                stars[i].setImageResource(R.drawable.star_on);
            } else {
                stars[i].setImageResource(R.drawable.star_off);
            }
        }
    }

    private void setTypeColors(int placeType) {
        int color;

        switch (placeType) {
            case 1:
                color = ContextCompat.getColor(context, R.color.featured);
                break;
            case 2:
                color = ContextCompat.getColor(context, R.color.bars);
                break;
            case 3:
                color = ContextCompat.getColor(context, R.color.food);
                break;
            case 4:
                color = ContextCompat.getColor(context, R.color.gyms);
                break;
            case 5:
                color = ContextCompat.getColor(context, R.color.supplies);
                break;
            case 6:
                color = ContextCompat.getColor(context, R.color.residences);
                break;
            case 7:
                color = ContextCompat.getColor(context, R.color.jobs);
                break;

            default:
                color = ContextCompat.getColor(context, R.color.iron);
        }

        bottomLine.setBackgroundColor(color);
        placeNameText.setTextColor(color);
    }
}