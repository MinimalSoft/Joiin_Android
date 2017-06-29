package com.MinimalSoft.Joiin.Notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Responses.TransactionResponse;
import com.MinimalSoft.Joiin.Services.MinimalSoftServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService implements Callback<TransactionResponse> {

    public static final String TAG = "Token Class";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        Log.d(TAG, "SendToken called!");

        String idUser = this.getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE)
                .getInt(Joiin.USER_ID, Joiin.NO_VALUE)+"";
        if(idUser.equals(Joiin.NO_VALUE))
        {
            SharedPreferences.Editor preferencesEditor = getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE).edit();
            preferencesEditor.putString(Joiin.USER_FCM_TOKEN, token);
            preferencesEditor.apply();
        }
        else{
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Joiin.API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MinimalSoftServices api = retrofit.create(MinimalSoftServices.class);
            api.updateToken("updateToken",idUser,token).enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
        Log.d(TAG, "Responded");
    }

    @Override
    public void onFailure(Call<TransactionResponse> call, Throwable t) {
        Log.d(TAG, "Failure");
    }
}