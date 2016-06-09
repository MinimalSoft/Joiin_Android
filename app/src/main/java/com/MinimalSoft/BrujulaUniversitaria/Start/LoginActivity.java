package com.MinimalSoft.BrujulaUniversitaria.Start;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import android.support.v7.app.AppCompatActivity;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;
import com.MinimalSoft.BrujulaUniversitaria.Facebook.FacebookDataCollector;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CallbackManager facebookCallbackManager;
    private LoginButton facebookLoginButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        this.setContentView(R.layout.activity_login);

        facebookLoginButton = (LoginButton) this.findViewById(R.id.login_hidden_facebook_button);
        Button fakeFacebookButton = (Button) this.findViewById(R.id.login_facebook_button);
        facebookCallbackManager = CallbackManager.Factory.create();
        FacebookDataCollector dataCollector = new FacebookDataCollector(this);

        fakeFacebookButton.setOnClickListener(this);
        facebookLoginButton.setReadPermissions("public_profile email");
        facebookLoginButton.registerCallback(facebookCallbackManager, dataCollector);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        facebookLoginButton.performClick();
    }

    public void logInWithFacebook() {
        intent = new Intent(this.getApplicationContext(), MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    /*@Override
    protected void onResume ()
    {
        super.onResume();
        //setVideo();
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
        //video =(VideoView)findViewById(R.id.videoView);
        final VideoView video =null;

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
    }*/
}
