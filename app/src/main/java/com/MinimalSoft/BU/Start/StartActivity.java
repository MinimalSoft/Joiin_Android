package com.MinimalSoft.BU.Start;

import com.MinimalSoft.BU.R;
import com.MinimalSoft.BU.Main.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.widget.ImageView;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;

public class StartActivity extends Activity implements Runnable {
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
        SharedPreferences settings = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);

        if (settings.getBoolean("LOGGED_IN", false)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}