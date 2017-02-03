package com.MinimalSoft.BUniversitaria.Maps;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.Models.Data_Reviews;
import com.MinimalSoft.BUniversitaria.R;

import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private ArrayList<Data_Reviews> reviews;

    public ReviewsAdapter(ArrayList<Data_Reviews> android) {
        this.reviews = android;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.review_name.setText(reviews.get(i).getName());
        viewHolder.review_text.setText(reviews.get(i).getText());

        //TODO: Improve date parsing
        String date = reviews.get(i).getDate();
        String dateFormated = date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
        viewHolder.review_date.setText(dateFormated);

        switch (reviews.get(i).getStars()) {
            case "1":
                viewHolder.review_star1.setImageResource(R.drawable.star_on);
                break;

            case "2":
                viewHolder.review_star1.setImageResource(R.drawable.star_on);
                viewHolder.review_star2.setImageResource(R.drawable.star_on);
                break;

            case "3":
                viewHolder.review_star1.setImageResource(R.drawable.star_on);
                viewHolder.review_star2.setImageResource(R.drawable.star_on);
                viewHolder.review_star3.setImageResource(R.drawable.star_on);
                break;

            case "4":
                viewHolder.review_star1.setImageResource(R.drawable.star_on);
                viewHolder.review_star2.setImageResource(R.drawable.star_on);
                viewHolder.review_star3.setImageResource(R.drawable.star_on);
                viewHolder.review_star4.setImageResource(R.drawable.star_on);
                break;

            case "5":
                viewHolder.review_star1.setImageResource(R.drawable.star_on);
                viewHolder.review_star2.setImageResource(R.drawable.star_on);
                viewHolder.review_star3.setImageResource(R.drawable.star_on);
                viewHolder.review_star4.setImageResource(R.drawable.star_on);
                viewHolder.review_star5.setImageResource(R.drawable.star_on);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView review_name, review_date, review_text;
        private ImageView review_star1, review_star2, review_star3, review_star4, review_star5;
        private String stars;

        public ViewHolder(View view) {
            super(view);

            review_name = (TextView) view.findViewById(R.id.review_textName);
            review_date = (TextView) view.findViewById(R.id.review_textDateTime);
            review_text = (TextView) view.findViewById(R.id.review_textReview);

            review_star1 = (ImageView) view.findViewById(R.id.star_1);
            review_star2 = (ImageView) view.findViewById(R.id.star_1);
            review_star3 = (ImageView) view.findViewById(R.id.star_1);
            review_star4 = (ImageView) view.findViewById(R.id.star_1);
            review_star5 = (ImageView) view.findViewById(R.id.star_1);

        }
    }

}