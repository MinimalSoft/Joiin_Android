package com.MinimalSoft.BUniversitaria.PlaceDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.BUniversitaria.Models.ReviewsData;
import com.MinimalSoft.BUniversitaria.R;

import java.util.ArrayList;
import java.util.List;

class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {
    private List<ReviewsData> reviewList = new ArrayList<>();

    /*----------------------- Adapter Methods ------------------------*/
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        if (reviewList.size() > 0) {
            ReviewsData reviewsData = reviewList.get(position);

            holder.loadImage(reviewsData.getFbImage());
            holder.setStars(reviewsData.getStars());
            holder.setReview(reviewsData.getText());
            holder.setDate(reviewsData.getDate());
            holder.setName(reviewsData.getName());
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    protected void updateReviews(List<ReviewsData> reviews) {
        reviewList.clear();
        reviewList.addAll(reviews);
        this.notifyDataSetChanged();
    }
}