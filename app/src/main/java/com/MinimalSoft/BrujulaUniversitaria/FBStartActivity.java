package com.MinimalSoft.BrujulaUniversitaria;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class FBStartActivity extends AppCompatActivity {

    private VideoView video;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_start);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile email");

        setColor();
        setVideo();
        setFacebook();

    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        setVideo();
    }

    public void setFacebook()
    {
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SharedPreferences settings = getSharedPreferences("facebook_pref", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("userId", loginResult.getAccessToken().getUserId());
                editor.putString("userToken", loginResult.getAccessToken().getToken());
                editor.commit();

                getFB ();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException exception) {
                confirmDialog();
            }
        });
    }

    private void confirmDialog ()
    {
        new AlertDialog.Builder(this)
                .setTitle("Ups!")
                .setMessage("Parece que no estas conectado internet")
                .setPositiveButton("Aceptar", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setColor ()
    {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (android.os.Build.VERSION.SDK_INT >= 21)
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

    protected void setVideo()
    {
        video =(VideoView)findViewById(R.id.videoView);

        String uriPath = "android.resource://"+getPackageName()+"/raw/video";
        Uri uri = Uri.parse(uriPath);
        video.setVideoURI(uri);
        video.setMediaController(null);

        video.start();

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.start();
            }
        });
    }

    private void getFB(){
        new Thread(new Runnable() {

            @Override
            public void run() {

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {
                                String name = json.getString("name");
                                String email = json.getString("email");

                                SharedPreferences settings = getSharedPreferences("facebook_pref", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("userName", name);
                                editor.putString("userEmail", email);
                                editor.commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }
        }).start();


    }

}
