package com.MinimalSoft.Joiin.Promos;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.MinimalSoft.Joiin.BU;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.PromoData;
import com.MinimalSoft.Joiin.Responses.PromosResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.MinimalSoft.Joiin.Viewer.ListViewerActivity;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromosFragment extends Fragment implements View.OnClickListener, DialogInterface.OnCancelListener, Callback<PromosResponse> {
    private Call<PromosResponse> serviceCall;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_promos, container, false);

        ImageButton foodButton = (ImageButton) inflatedView.findViewById(R.id.promo_foodButton);
        ImageButton gymsButton = (ImageButton) inflatedView.findViewById(R.id.promo_gymsButton);
        ImageButton barsButton = (ImageButton) inflatedView.findViewById(R.id.promo_barsButton);

        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setOnCancelListener(this);
        progressDialog.setMessage("Buscando...");

        foodButton.setOnClickListener(this);
        gymsButton.setOnClickListener(this);
        barsButton.setOnClickListener(this);

        return inflatedView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        progressDialog.show();
        int chosenCategoryType;

        switch (v.getId()) {
            case R.id.promo_barsButton:
                chosenCategoryType = BU.BARS_ID;
                break;

            case R.id.promo_foodButton:
                chosenCategoryType = BU.FOOD_ID;
                break;

            case R.id.promo_gymsButton:
                chosenCategoryType = BU.GYMS_ID;
                break;

            default:
                chosenCategoryType = BU.NO_VALUE;
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BU.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
        serviceCall = api.getPromos("getPromos", String.valueOf(chosenCategoryType));
        serviceCall.enqueue(this);
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
    public void onResponse(Call<PromosResponse> call, Response<PromosResponse> response) {
        progressDialog.hide();

        if (response.isSuccessful()) {
            List<PromoData> promosList = response.body().getData();

            if (promosList != null) {
                String json = new Gson().toJson(promosList);

                Bundle bundle = new Bundle();
                bundle.putString(BU.JSON_DATA_KEY, json);
                bundle.putInt(BU.RESOURCE_KEY, R.layout.item_promo);
                //extras.putInt(BU.PLACE_TYPE_KEY, chosenCategoryType);
                bundle.putString(BU.ACTIVITY_TITLE_KEY, "Promociones");

                Intent intent = new Intent(getActivity(), ListViewerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(getActivity()).setMessage("Parece que no hay promociones por el momento.").show();
            }

            //PromoData[] promos = new PromoData[list.size()];
            //promosAdapter.setPromosData(promos);
        } else {
            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
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
    public void onFailure(Call<PromosResponse> call, Throwable t) {
        progressDialog.hide();
        t.printStackTrace();
    }

    /**
     * This method will be invoked when the dialog is canceled.
     *
     * @param dialog The dialog that was canceled will be passed into the
     *               method.
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        serviceCall.cancel();
    }
}