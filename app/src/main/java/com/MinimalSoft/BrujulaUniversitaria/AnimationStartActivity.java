package com.MinimalSoft.BrujulaUniversitaria;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class AnimationStartActivity extends AppCompatActivity {

    boolean valor=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_start);

        setColor();

        ImageView animationView = (ImageView) findViewById(R.id.animationView);
        animationView.setBackgroundResource(R.drawable.animation_loading);
        AnimationDrawable animation = (AnimationDrawable) animationView.getBackground();
        animation.start();
        checkIfAnimationDone(animation);

    }

    private void checkIfAnimationDone(AnimationDrawable anim){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)) {
                    checkIfAnimationDone(a);
                } else {
                    iniciar();
                }
            }
        }, timeBetweenChecks);
    };

    protected void iniciar(){

        SharedPreferences settings = getSharedPreferences("facebook_pref", 0);
        String id = settings.getString("userId","NA");

        if ( id.equals("NA"))
        {
            Intent intent = new Intent(this, FBStartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setColor ()
    {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

}


