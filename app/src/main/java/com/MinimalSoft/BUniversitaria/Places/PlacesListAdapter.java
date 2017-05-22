package com.MinimalSoft.BUniversitaria.Places;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Details.DetailsActivity;
import com.MinimalSoft.BUniversitaria.R;
import com.MinimalSoft.BUniversitaria.Responses.PlaceData;
import com.bumptech.glide.Glide;

public class PlacesListAdapter extends ArrayAdapter<PlaceData> implements AdapterView.OnItemClickListener {
    private int typeID;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public PlacesListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull PlaceData[] objects, int typeID) {
        super(context, resource, objects);
        this.typeID = typeID;
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public PlacesListAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull PlaceData[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.typeID = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
        }

        TextView addressLabel = (TextView) convertView.findViewById(R.id.place_addressLabel);
        TextView ratingLabel = (TextView) convertView.findViewById(R.id.place_ratingLabel);
        TextView nameLabel = (TextView) convertView.findViewById(R.id.place_nameLabel);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.place_imageView);

        if (getCount() > 0) {
            PlaceData data = getItem(position);

            String imageURL = BU.API_URL + "/imagenes/places/" + data.getImage();
            String address = data.getStreet() + " # " + data.getNumber() + ", " + data.getNeighborhood();

            Glide.with(getContext()).load(imageURL).placeholder(R.drawable.default_image).into(imageView);
            ratingLabel.setText(String.valueOf(data.getStars()));
            nameLabel.setText(data.getPlaceName());
            addressLabel.setText(address);
        }

        return convertView;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlaceData data = (PlaceData) parent.getItemAtPosition(position);

        Bundle bundle = new Bundle();
        bundle.putInt(BU.PLACE_TYPE_KEY, typeID);
        bundle.putInt(BU.PLACE_ID_KEY, data.getIdPlace());
        bundle.putString(BU.PLACE_NAME_KEY, data.getPlaceName());

        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtras(bundle);

        getContext().startActivity(intent);
    }
}
