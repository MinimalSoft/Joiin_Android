package com.MinimalSoft.BUniversitaria.RecyclerPosts;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Models.ReviewsData;
import com.MinimalSoft.BUniversitaria.R;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    /* There are only two types of reactions. */
    private final int REACTIONS_COUNT = 2;
    private final int STARS_COUNT = 5;

    private ImageView stars[] = new ImageView[STARS_COUNT];
    private ReactionHandler reactionHandler;
    private CircleImageView profileImage;
    private LinearLayout reviewLayout;
    private TextView placeNameText;
    private TextView userNameText;
    private TextView dateTimeText;
    private TextView reviewText;
    private Context context;
    private View bottomLine;

    public PostHolder(View itemView) {
        super(itemView);

        TextView [] counterFields = new TextView[REACTIONS_COUNT];
        LikeButton[] reactionButtons = new LikeButton[REACTIONS_COUNT];

        reactionButtons[1] = (LikeButton) itemView.findViewById(R.id.post_dislikeButton);
        reactionButtons[0] = (LikeButton) itemView.findViewById(R.id.post_likeButton);
        counterFields[1] = (TextView) itemView.findViewById(R.id.post_textDislikes);
        counterFields[0] = (TextView) itemView.findViewById(R.id.post_textLikes);

        reviewLayout = (LinearLayout) itemView.findViewById(R.id.post_reviewLayout);
        profileImage = (CircleImageView) itemView.findViewById(R.id.post_image);
        dateTimeText = (TextView) itemView.findViewById(R.id.post_textDateTime);
        placeNameText = (TextView) itemView.findViewById(R.id.post_textPlace);
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

    protected void setData(ReviewsData postData, int userId) {
        Picasso.with(context).load(Uri.parse(postData.getFbImage())).placeholder(R.drawable.default_profile).into(profileImage);
        dateTimeText.setText(postData.getDate().replace(" ", " a las "));
        placeNameText.setText(postData.getPlaceName());
        userNameText.setText(postData.getName());
        reviewText.setText(postData.getText());
        setTypeColors(postData.getIdType());
        setStars(postData.getStars());

        int amounts [] = new int[REACTIONS_COUNT];
        amounts[1] = postData.getDislikes();
        amounts[0] = postData.getLikes();

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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}