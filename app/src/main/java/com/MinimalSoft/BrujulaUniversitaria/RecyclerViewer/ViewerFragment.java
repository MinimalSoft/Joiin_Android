package com.MinimalSoft.BrujulaUniversitaria.RecyclerViewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.DetailsActivity;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PlacesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View inflatedView;

    public ViewerFragment() {

    }


    public static ViewerFragment newInstance(String param1, String param2) {
        ViewerFragment fragment = new ViewerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_viewer, container, false);

            mRecyclerView = (RecyclerView) inflatedView.findViewById(R.id.viewer_recycler);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mAdapter = new PlacesAdapter(getContext());
            //layoutManager = new LinearLayoutManager(inflatedView.getContext());
            mRecyclerView.setAdapter(mAdapter);
            //mRecyclerView.setLayoutManager(layoutManager);

            getPlaces();

        }

        return inflatedView;
    }


    private void getPlaces() {
        final String ROOT_URL = "http://ec2-54-210-116-247.compute-1.amazonaws.com/appBrujula/controllers";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter
        GetPlaces service = restAdapter.create(GetPlaces.class);
        PlacesRequest task = new PlacesRequest("place","1","","","");
        service.getPlaces(task, new Callback<Place.PlaceResult>() {
            @Override
            public void success(Place.PlaceResult placeResult, Response response) {
                mAdapter.setPlacesList(placeResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }



    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView address;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.item_place_picture);
            name = (TextView) itemView.findViewById(R.id.item_place_name);
            address = (TextView) itemView.findViewById(R.id.item_place_address);

        }
    }
    public static class PlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
        private List<Place> mPlacesList;
        private LayoutInflater mInflater;
        private Context mContext;

        public PlacesAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public PlaceViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = mInflater.inflate(R.layout.item_place, parent, false);
            final PlaceViewHolder viewHolder = new PlaceViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext,DetailsActivity.class);
                    intent.putExtra(DetailsActivity.EXTRA_PLACE, mPlacesList.get(position));
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PlaceViewHolder holder, int position) {
            Place place = mPlacesList.get(position);

            Picasso.with(mContext)
                    .load(place.getImage())
                    .placeholder(R.color.colorAccent)
                    .into(holder.picture);

            holder.name.setText(place.getPlaceName());
            holder.address.setText(place.getStreet()+", "+place.getNumber()+", "+place.getNeighborhood());
        }

        @Override
        public int getItemCount() {
            return (mPlacesList == null) ? 0 : mPlacesList.size();
        }

        public void setPlacesList(List<Place> placeList) {
            this.mPlacesList = new ArrayList<>();
            this.mPlacesList.addAll(placeList);
            notifyDataSetChanged();
        }
    }


}
