package com.MinimalSoft.Joiin.Reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.ReviewsData;

import java.util.ArrayList;
import java.util.List;

class ReviewsAdapter extends RecyclerView.Adapter<ReviewHolder> {
    private List<ReviewsData> reviewsList = new ArrayList<>();
    private int placeID, userID;

    ReviewsAdapter(int userID) {
        this.userID = userID;
    }

    ReviewsAdapter(int placeID, int userID) {
        this.placeID = placeID;
        this.userID = userID;
    }

    /*----------------------- Adapter Methods ------------------------*/

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /* This adapter is generic, for multiple or for a single place.
          * If placeID holds a value it means this adapter will handle data for a single place.
          * If it has no value, the adapter will handle data for multiple places. */
        boolean singlePlaceReviews = (placeID != Joiin.NO_VALUE);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(inflatedView, singlePlaceReviews, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        if (getItemCount() > 0) {
            ReviewsData data = reviewsList.get(position);

            holder.setStars(data.getStars());
            holder.setReview(data.getText());
            holder.setImages(data.getImage(), data.getFbImage());
            holder.setBasicInfo(data.getIdReview(), data.getName(), data.getDate(), data.getIdUser(), userID, position);
            holder.setReactionsData(data.getIdReview(), data.getReaction(), data.getLikes(), data.getDislikes());

            if (placeID == Joiin.NO_VALUE) {
                holder.setPlaceInfo(data.getIdPlace(), data.getPlaceName(), data.getIdType());
            }
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return reviewsList.size();
        //return flag ? reviewsList.size() : 3; // Recommended.
    }

    void updateItems(List<ReviewsData> reviews) {
        reviewsList.clear();
        reviewsList.addAll(reviews);
        notifyDataSetChanged();
    }

    void removeAtPosition(int position) {
        reviewsList.remove(position);
        notifyDataSetChanged();
    }
}