package com.MinimalSoft.BrujulaUniversitaria.Start;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.annotation.TargetApi;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;

public class StartActivity extends AppCompatActivity implements Runnable {
    //private AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView imageView = (ImageView) findViewById(R.id.start_animation);
        imageView.setBackgroundResource(R.drawable.animation_loading);
        AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();

        int imageDuration = getResources().getInteger(R.integer.image_duration);
        int framesCount = animation.getNumberOfFrames();
        int timeDelay = imageDuration * framesCount;

        new Handler().postDelayed(this, timeDelay);
        animation.start();
    }

    @Override
    public void run() {
        Intent intent;
        //animation.stop();

        SharedPreferences settings = this.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);

        if (settings.getBoolean("LOGGED_IN", false)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    /* Consider Review */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setColor ()
    {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}