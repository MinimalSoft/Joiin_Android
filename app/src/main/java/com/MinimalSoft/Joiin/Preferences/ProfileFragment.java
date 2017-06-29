package com.MinimalSoft.Joiin.Preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.Joiin.BuildConfig;
import com.MinimalSoft.Joiin.Facebook.FacebookData;
import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Responses.TransactionResponse;
import com.MinimalSoft.Joiin.Start.LoginActivity;
import com.MinimalSoft.Joiin.Web.WebActivity;
import com.MinimalSoft.Joiin.Widgets.CircleImageView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements GraphRequest.GraphJSONObjectCallback, Callback<TransactionResponse>, DialogInterface.OnClickListener, View.OnClickListener {
    private Call<TransactionResponse> serviceCall;
    private CircleImageView profilePicture;
    private ImageView coverPicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        Button disclaimerButton = (Button) inflatedView.findViewById(R.id.preferences_disclaimerButton);
        Button settingsButton = (Button) inflatedView.findViewById(R.id.preferences_settingsButton);
        Button updateButton = (Button) inflatedView.findViewById(R.id.preferences_updateButton);
        Button logoutButton = (Button) inflatedView.findViewById(R.id.preferences_logoutButton);

        profilePicture = (CircleImageView) inflatedView.findViewById(R.id.preferences_profileImage);
        coverPicture = (ImageView) inflatedView.findViewById(R.id.preferences_coverImage);

        TextView versionLabel = (TextView) inflatedView.findViewById(R.id.preferences_versionLabel);
        TextView emailLabel = (TextView) inflatedView.findViewById(R.id.preferences_emailLabel);
        TextView nameLabel = (TextView) inflatedView.findViewById(R.id.preferences_nameLabel);

        SharedPreferences settings = getContext().getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE);
        emailLabel.setText(settings.getString(Joiin.USER_EMAIL, "loading..."));
        nameLabel.setText(settings.getString(Joiin.USER_NAME, "loading..."));

        versionLabel.setText(BuildConfig.VERSION_NAME);

        disclaimerButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //serviceCall.enqueue(this);

        try {
            FileInputStream fileInputStream = getContext().openFileInput(Joiin.USER_PHOTO);
            Bitmap profileBitmap = BitmapFactory.decodeStream(fileInputStream);

            fileInputStream = getContext().openFileInput(Joiin.USER_COVER);
            Bitmap coverBitmap = BitmapFactory.decodeStream(fileInputStream);

            profilePicture.setImageBitmap(profileBitmap);
            coverPicture.setImageBitmap(coverBitmap);

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (AccessToken.getCurrentAccessToken() != null) {
            Bundle parameters = new Bundle();
            int size = (int) getResources().getDimension(R.dimen.profile_image_size);
            //int size = (int) getResources().getDimensionPixelSize(R.dimen.profile_image_size);
            parameters.putString("fields", "cover,picture.width(" + size + ").height(" + size + ')');
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preferences_disclaimerButton:
                String link = Joiin.WP_URL + "/aviso-de-privacidad/";
                Intent intent = new Intent(this.getActivity(), WebActivity.class);

                intent.putExtra(Joiin.ACTIVITY_TITLE_KEY, "Aviso de privacidad");
                intent.putExtra(Joiin.WP_URL, link);
                startActivity(intent);
                break;

            case R.id.preferences_logoutButton:
                new AlertDialog.Builder(getContext())
                        .setTitle("¿Seguro que deseas salir?")
                        .setPositiveButton("Continuar", this)
                        .setNegativeButton("Cancelar", null)
                        .show();
                break;
        }
    }

    /*----DialogInterface methods----*/

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked (e.g.
     *               {@link DialogInterface#BUTTON1}) or the position
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }

        getActivity().deleteFile(Joiin.USER_COVER);
        getActivity().deleteFile(Joiin.USER_PHOTO);
        //getActivity().deleteSharedPreferences("BU_PREF"); //Min API v24.

        getActivity().getSharedPreferences(Joiin.PREFERENCES, Context.MODE_PRIVATE)
                .edit().clear().apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
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

    }

    /*---------------- GraphJSONObjectCallback Methods ----------------*/

    /**
     * The method that will be called when the request completes.
     *
     * @param object   the GraphObject representing the returned object, or null
     * @param response the Response of this request, which may include error information if the
     */
    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        if (isAdded()) {
            if (response.getError() == null) {
                Type type = new TypeToken<FacebookData>() {
                }.getType();
                FacebookData facebookData = new Gson().fromJson(object.toString(), type);

                String coverURL = (facebookData.getCover() != null) ? (facebookData.getCover().getSource()) : "";
                String imageURL = (facebookData.getPicture() != null) ? (facebookData.getPicture().getData().getUrl()) : "";

                new PicturesCollector(this, imageURL, coverURL).execute(coverPicture.getWidth(), coverPicture.getHeight());
                //Glide.with(fragment).load(coverURL).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.image_cover).transform(this).into(imageView);
            } else {
                // todo: java.lang.NullPointerException: Attempt to invoke virtual method 'com.facebook.FacebookException com.facebook.FacebookRequestError.getException()' on a null object reference
                response.getError().getException().printStackTrace();
            }
        }
    }

    protected void setBitmaps(Bitmap profileBitmap, Bitmap coverBitmap) {
        if (!isDetached()) {
            profilePicture.setImageBitmap(profileBitmap);
            coverPicture.setImageBitmap(coverBitmap);
        }
    }
}