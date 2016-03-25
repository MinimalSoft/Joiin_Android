package com.MinimalSoft.BrujulaUniversitaria.Tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.BrujulaUniversitaria.AsyncBlur;
import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.SettingsActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private View rootView = null;

    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        new AsyncPictures().execute("");
        TextView settings = (TextView) getActivity().findViewById(R.id.Profile_Settings);
        TextView disclaimer = (TextView) getActivity().findViewById(R.id.Profile_Disclaimer);
        TextView logOut = (TextView) getActivity().findViewById(R.id.Profile_LogOut);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
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
        TextView name = (TextView) this.getView().findViewById(R.id.Profile_Name);
        TextView email = (TextView) this.getView().findViewById(R.id.Profile_Email);

        name.setText(settings.getString("userName", "NA"));
        email.setText(settings.getString("userEmail", "NA"));

        CircleImageView profilePic = (CircleImageView) rootView.findViewById(R.id.Profile_ProfilePic);
        ImageView coverPic = (ImageView) rootView.findViewById(R.id.Profile_CoverPic);

        coverPic.setImageBitmap(loadBitmap(context, "cover"));
        profilePic.setImageBitmap(loadBitmap(context, "profile"));

    }


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