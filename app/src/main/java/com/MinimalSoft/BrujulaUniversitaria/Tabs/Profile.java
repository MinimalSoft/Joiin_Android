package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.view.View;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.AlertDialog;

import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONObject;
import org.json.JSONException;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import de.hdodenhof.circleimageview.CircleImageView;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.AsyncBlur;
import com.MinimalSoft.BrujulaUniversitaria.SettingsActivity;
import com.MinimalSoft.BrujulaUniversitaria.Start.LoginActivity;

public class Profile extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private boolean flag;
    private Intent intent;
    private View inflatedView;
    private TextView nameLabel;
    private TextView emailLabel;
    private ImageView coverPicture;
    private TextView logOutButton;
    private TextView settingsButton;
    private TextView disclaimerButton;
    private CircleImageView profilePicture;

    public Profile() {
        flag = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

            nameLabel = (TextView) inflatedView.findViewById(R.id.profile_name_label);
            emailLabel = (TextView) inflatedView.findViewById(R.id.profile_email_label);
            logOutButton = (TextView) inflatedView.findViewById(R.id.profile_logout_button);
            coverPicture = (ImageView) inflatedView.findViewById(R.id.profile_cover_image_view);
            settingsButton = (TextView) inflatedView.findViewById(R.id.profile_settings_button);
            disclaimerButton = (TextView) inflatedView.findViewById(R.id.profile_disclaimer_button);
            profilePicture = (CircleImageView) inflatedView.findViewById(R.id.profile_user_image_view);

            disclaimerButton.setOnClickListener(this);
            settingsButton.setOnClickListener(this);
            logOutButton.setOnClickListener(this);
        }

        if (!flag) {
            new AsyncPictures().execute("");
        }

        return inflatedView;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences settings = this.getActivity().getSharedPreferences("facebook_pref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userId", "NA").commit();

        String id = settings.getString("userId", "NA");

        FacebookSdk.sdkInitialize(getActivity());
        LoginManager.getInstance().logOut();

        flag = false;

        intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
        this.getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_logout_button:
                this.logOutConfirmDialog();
                break;
            case R.id.profile_settings_button:
                intent = new Intent(this.getActivity(), SettingsActivity.class);
                this.startActivity(intent);
                break;
            case R.id.profile_disclaimer_button:
                intent = new Intent(this.getActivity(), SettingsActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    private void logOutConfirmDialog() {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this.getActivity());
        confirmDialog.setMessage("Cada que alguien nos deja, nuestro  DevTeam llora :'(");
        confirmDialog.setTitle("¿Serguro que deseas salir?");
        confirmDialog.setPositiveButton("No me importa", this);
        confirmDialog.setNegativeButton("Evitar que lloren", null);
        confirmDialog.show();
    }

    public void saveFile(Context context, Bitmap b, String picName) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadBitmap(Context context, String picName) {
        Bitmap b = null;
        FileInputStream fis;
        try {
            fis = context.openFileInput(picName);
            b = BitmapFactory.decodeStream(fis);
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    private void setProfile(Context context) {
        SharedPreferences settings = context.getSharedPreferences("facebook_pref", context.MODE_PRIVATE);

        profilePicture.setImageBitmap(loadBitmap(context, "profile"));
        coverPicture.setImageBitmap(loadBitmap(context, "cover"));
        emailLabel.setText(settings.getString("userEmail", "NA"));
        nameLabel.setText(settings.getString("userName", "NA"));

        flag = true;
    }

    /*public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }*/

    private class AsyncPictures extends AsyncTask<String, Void, Bitmap> {
        public Bitmap DownloadImageBitmap() {
            FacebookSdk.sdkInitialize(getActivity());
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {

                    JSONObject json = response.getJSONObject();
                    HttpURLConnection connection = null;
                    InputStream is = null;

                    try {
                        if (json != null) {

                            URL profileURL = new URL(json.getJSONObject("picture").getJSONObject("data").getString("url"));
                            URL coverURL = new URL(json.getJSONObject("cover").getString("source"));

                            is = (InputStream) profileURL.getContent();
                            Bitmap bitProfile = BitmapFactory.decodeStream(is);

                            is = (InputStream) coverURL.getContent();
                            Bitmap bitCover = BitmapFactory.decodeStream(is);
                            Bitmap blurredCover = AsyncBlur.blur(Profile.this.getContext(), bitCover);

                            is.close();

                            saveFile(Profile.this.getContext(), bitProfile, "profile");
                            saveFile(Profile.this.getContext(), blurredCover, "cover");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,picture.type(large),cover");
            request.setParameters(parameters);
            request.executeAsync();

            return null;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return DownloadImageBitmap();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            setProfile(Profile.this.getContext());
        }
    }
}