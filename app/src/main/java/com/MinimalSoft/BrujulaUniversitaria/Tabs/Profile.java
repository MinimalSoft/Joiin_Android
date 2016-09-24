package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import com.MinimalSoft.BrujulaUniversitaria.Start.LoginActivity;
import com.MinimalSoft.BrujulaUniversitaria.SettingsActivity;
import com.MinimalSoft.BrujulaUniversitaria.Web.WebActivity;
import com.MinimalSoft.BrujulaUniversitaria.R;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONObject;
import org.json.JSONException;

import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.annotation.Nullable;

import java.net.URL;
import java.net.MalformedURLException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment implements GraphRequest.GraphJSONObjectCallback, DialogInterface.OnClickListener, View.OnClickListener {
    private boolean flag;
    private TextView nameLabel;
    private TextView emailLabel;
    private ImageView imageView;
    private CircleImageView profilePicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        imageView = (ImageView) inflatedView.findViewById(R.id.cover_imageView);
        nameLabel = (TextView) inflatedView.findViewById(R.id.profile_nameLabel);
        emailLabel = (TextView) inflatedView.findViewById(R.id.profile_emailLabel);
        profilePicture = (CircleImageView) inflatedView.findViewById(R.id.profile_imageView);
        Button logoutButton = (Button) inflatedView.findViewById(R.id.profile_logoutButton);
        Button updateButton = (Button) inflatedView.findViewById(R.id.profile_upDateButton);
        Button settingsButton = (Button) inflatedView.findViewById(R.id.profile_settingsButton);
        Button disclaimerButton = (Button) inflatedView.findViewById(R.id.profile_disclaimerButton);

        disclaimerButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences settings = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        emailLabel.setText(settings.getString("USER_EMAIL", "loading..."));
        nameLabel.setText(settings.getString("USER_NAME", "loading..."));
        String facebookID = settings.getString("FACEBOOK_ID", "NA");

        if (!facebookID.equals("NA")) {
            flag = true;
            Bundle parameters = new Bundle();
            FacebookSdk.sdkInitialize(getActivity());
            //String facebookFields = "name,email,picture.type(large),source";
            String facebookFields = "cover";
            parameters.putString("fields", facebookFields);

            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        } else {
            flag = false;
        }
    }

    /*GraphRequest implemented methods*/

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        JSONObject json = response.getJSONObject();

        try {
            Log.i(getClass().getSimpleName(), json.toString());
            URL coverPicURL = new URL(json.getJSONObject("cover").getString("source"));
            //URL profilePicURL = new URL(json.getJSONObject("picture").getJSONObject("data").getString("url"));
            //boolean imagesSaved = settings.getBoolean("USER_PICS", false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*----DialogInterface methods----*/

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences.Editor settingsEditor = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();

        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //emailLabel.setText(getResources().getString(R.string.user_email_hint));
        //nameLabel.setText(getResources().getString(R.string.user_name_hint));

        if (flag) {
            //settingsEditor.putBoolean("USER_PICS", false);
            settingsEditor.putString("FACEBOOK_ID", "NA");
            FacebookSdk.sdkInitialize(this.getActivity());
            LoginManager.getInstance().logOut();
        }

        settingsEditor.putBoolean("LOGGED_IN", false);
        settingsEditor.apply();
        startActivity(intent);
        getActivity().finish();
    }

    /*----View methods----*/

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.profile_logoutButton:
                AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this.getActivity());
                confirmDialog.setMessage("Cada que alguien nos deja, nuestro  DevTeam llora :'(");
                confirmDialog.setTitle("¿Serguro que deseas salir?");
                confirmDialog.setPositiveButton("Continuar", this);
                confirmDialog.setNegativeButton("Cancelar", null);
                confirmDialog.show();
                break;

            case R.id.profile_disclaimerButton:
                String link = getResources().getString(R.string.terms_url);
                intent = new Intent(this.getActivity(), WebActivity.class);

                intent.putExtra("TITLE", "Aviso de privacidad");
                intent.putExtra("LINK", link);
                startActivity(intent);
                break;

            case R.id.profile_settingsButton:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

/*public class Profile extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {
    private FacebookPicturesCollector picturesCollector;
    private CircleImageView profilePicture;
    private TextView disclaimerButton;
    private TextView settingsButton;
    private ImageView coverPicture;
    private TextView logOutButton;
    private TextView emailLabel;
    private TextView nameLabel;
    private View inflatedView;
    private Intent intent;

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

            picturesCollector = new FacebookPicturesCollector(this);

            disclaimerButton.setOnClickListener(this);
            settingsButton.setOnClickListener(this);
            logOutButton.setOnClickListener(this);

            this.setUserProfile();
        }

        return inflatedView;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences settings = this.getActivity().getSharedPreferences("FACEBOOK_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = settings.edit();
        Resources resources = inflatedView.getResources();

        settingsEditor.putString("FACEBOOK_ID", "NA");
        settingsEditor.commit();

        FacebookSdk.sdkInitialize(this.getActivity());
        LoginManager.getInstance().logOut();

        nameLabel.setText(resources.getString(R.string.user_name_hint));
        emailLabel.setText(resources.getString(R.string.user_email_hint));

        intent = new Intent(this.getActivity(), LoginActivity.class);
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
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.profile_disclaimer_button:
                Intent intent = new Intent(this.getActivity(), WebActivity.class);
                intent.putExtra("TITLE", "Aviso de Privacidad");
                intent.putExtra("LINK", "http://brujulauniversitaria.com.mx/aviso-de-privacidad/");
                this.startActivity(intent);
                break;
        }
    }

    private void setUserProfile () {
        SharedPreferences settings = this.getActivity().getSharedPreferences("FACEBOOK_PREF", Context.MODE_PRIVATE);
        boolean imagesSaved = settings.getBoolean("USER_PICS", false);

        if (imagesSaved) {
            emailLabel.setText(settings.getString("USER_EMAIL", ""));
            nameLabel.setText(settings.getString("USER_NAME", ""));

            try {
                FileInputStream fileInputStream = this.getContext().openFileInput("PROFILE_BITMAP");
                Bitmap profileBitmap = BitmapFactory.decodeStream(fileInputStream);

                fileInputStream = this.getContext().openFileInput("COVER_BITMAP");
                Bitmap coverBitmap = BitmapFactory.decodeStream(fileInputStream);

                profilePicture.setImageBitmap(profileBitmap);
                coverPicture.setImageBitmap(coverBitmap);

                fileInputStream.close();
            } catch (FileNotFoundException exc) {
                emailLabel.setText("loading...");
                exc.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            emailLabel.setText("loading...");
            nameLabel.setText("loading...");
        }
    }

    public void uploadPictures(Bitmap profileBitmap, Bitmap coverBitmap) {
        SharedPreferences settings = this.getActivity().getSharedPreferences("FACEBOOK_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = settings.edit();

        try {
            FileOutputStream fileOutputStream = this.getContext().openFileOutput("PROFILE_BITMAP", Context.MODE_PRIVATE);
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream = this.getContext().openFileOutput("COVER_BITMAP", Context.MODE_PRIVATE);
            coverBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.close();

            settingsEditor.putBoolean("USER_PICS", true);
            settingsEditor.commit();
            this.setUserProfile();
        } catch (FileNotFoundException e) {
            emailLabel.setText("loading...");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logOutConfirmDialog() {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this.getActivity());
        confirmDialog.setMessage("Cada que alguien nos deja, nuestro  DevTeam llora :'(");
        confirmDialog.setNegativeButton("Evitar que lloren", null);
        confirmDialog.setPositiveButton("No me importa", this);
        confirmDialog.setTitle("¿Serguro que deseas salir?");
        confirmDialog.show();
    }

    public void reloadPictures () {
        if (picturesCollector != null) {
            picturesCollector.execute();
        }
    }
}*/