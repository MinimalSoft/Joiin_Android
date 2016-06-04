package com.MinimalSoft.BrujulaUniversitaria.Start;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.annotation.TargetApi;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;

public class StartActivity extends AppCompatActivity implements Runnable {
    private AnimationDrawable animation;
    private ImageView imageView;

    private SharedPreferences settings;
    private Handler handler;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_start);

        imageView = (ImageView) findViewById(R.id.animationView);
        imageView.setBackgroundResource(R.drawable.animation_loading);
        animation = (AnimationDrawable) imageView.getBackground();

        int imageDelay = this.getApplicationContext().getResources().getInteger(R.integer.image_delay);
        int imageCount = animation.getNumberOfFrames();
        int timeDelay = imageDelay * imageCount;

        handler = new Handler();

        animation.start();
        handler.postDelayed(this, timeDelay);
    }

    @Override
    public void run() {
        settings = this.getSharedPreferences("facebook_pref", 0);
        String id = settings.getString("userId", "NA");

        if (id.equals("NA")) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        this.finish();
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
