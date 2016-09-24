package com.MinimalSoft.BrujulaUniversitaria.Facebook;

import java.net.URL;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import java.net.MalformedURLException;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.MinimalSoft.BrujulaUniversitaria.Tabs.Profile;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.ImageUtility;

public class FacebookPicturesCollector implements GraphRequest.GraphJSONObjectCallback, Runnable {
    private boolean flag;
    private Profile profile;
    private URL coverPicURL;
    private URL profilePicURL;
    private Bitmap coverBitmap;
    private Bitmap profileBitmap;

    public FacebookPicturesCollector (final Profile profile) {
        this.profile = profile;
        flag = false;
    }

    public void execute () {
        if (!flag) {
            flag = true;
            Bundle parameters = new Bundle();
            FacebookSdk.sdkInitialize(profile.getActivity());
            parameters.putString("fields", "id,name,email,picture.type(large),cover");

            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }
    }

    /*GraphRequest implemented methods*/

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        JSONObject json = response.getJSONObject();
        Thread downloadThread = new Thread(this);

        try {
            profilePicURL = new URL(json.getJSONObject("picture").getJSONObject("data").getString("url"));
            coverPicURL = new URL(json.getJSONObject("cover").getString("source"));

            downloadThread.start();
            downloadThread.join();

            //profile.uploadPictures(profileBitmap, coverBitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
    }

    /* Runnable implemented methods */

    @Override
    public void run() {
        InputStream inputStream = null;

        try {
            inputStream = (InputStream) profilePicURL.getContent();
            profileBitmap = BitmapFactory.decodeStream(inputStream);

            inputStream = (InputStream) coverPicURL.getContent();
            coverBitmap = BitmapFactory.decodeStream(inputStream);
            coverBitmap = ImageUtility.blur(profile.getContext(), coverBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}