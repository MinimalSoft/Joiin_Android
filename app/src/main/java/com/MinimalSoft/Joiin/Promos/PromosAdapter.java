package com.MinimalSoft.Joiin.Promos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.PromoData;
import com.MinimalSoft.Joiin.Responses.TransactionResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Utilities.UnitFormatterUtility;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromosAdapter extends ArrayAdapter<PromoData> implements AdapterView.OnItemClickListener, Callback<TransactionResponse> {
    private TextView toolbarLabel;
    private int coins = 0;
    private int userID;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public PromosAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull PromoData[] objects) {
        super(context, resource, objects);

        SharedPreferences settings = getContext().getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE);
        userID = settings.getInt(Joiin.USER_ID, Joiin.NO_VALUE);
        toolbarLabel = (TextView) context.findViewById(R.id.list_toolText);
        toolbarLabel.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
        api.getCoins("getCoins", String.valueOf(userID)).enqueue(this);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_promo, parent, false);
        }

        TextView promoLabel = (TextView) convertView.findViewById(R.id.promo_titleLabel);
        TextView coinsLabel = (TextView) convertView.findViewById(R.id.promo_coinsLabel);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.promo_imageView);
        FrameLayout layer = (FrameLayout) convertView.findViewById(R.id.promo_blockLayer);

        if (getCount() > 0) {
            PromoData data = getItem(position);

            String imageURL = Joiin.API_URL + "/imagenes/promos/" + data.getBanner();
            String price = String.format(UnitFormatterUtility.MEXICAN_LOCALE, "%,d", data.getPrice());

            Glide.with(getContext()).load(imageURL).placeholder(R.drawable.image_loading).into(imageView);

            promoLabel.setText(data.getDescription());
            coinsLabel.setText(price);

            if (coins >= data.getPrice()) {
                layer.setVisibility(View.INVISIBLE);
            }
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
        //PromoData promo = (PromoData) parent.getItemAtPosition(position);
        PromoData data = getItem(position);

        if (coins >= data.getPrice()) {
            PromoDialog dialog = new PromoDialog(getContext(), getItem(position), userID);
            dialog.display();
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
    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
        if (response.isSuccessful()) {
            coins = response.body().getData().getCoins();

            toolbarLabel.setText(String.format(UnitFormatterUtility.MEXICAN_LOCALE, "%,d", coins));

            if (!isEmpty()) {
                notifyDataSetChanged();
            }
        } else {
            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
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
    public void onFailure(Call<TransactionResponse> call, Throwable t) {
        t.printStackTrace();
    }
}