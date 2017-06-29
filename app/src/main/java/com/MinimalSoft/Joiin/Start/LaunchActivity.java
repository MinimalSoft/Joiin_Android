package com.MinimalSoft.Joiin.Start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Main.MainActivity;
import com.MinimalSoft.Joiin.R;

public class LaunchActivity extends Activity implements Animation.AnimationListener {
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        id = getSharedPreferences(Joiin.PREFERENCES, MODE_PRIVATE).getInt(Joiin.USER_ID, Joiin.NO_VALUE);
        int src = (id == Joiin.NO_VALUE ? R.anim.login_animation : R.anim.launch_animation);

        ImageView imageView = (ImageView) findViewById(R.id.launch_imageView);
        Animation animation = AnimationUtils.loadAnimation(this, src);

        animation.setAnimationListener(this);
        imageView.startAnimation(animation);

        /*ImageView imageView = (ImageView) findViewById(R.id.launch_imageView);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);
        imageView.startAnimation(alpha);*/
    }

    /**
     * <p>Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.</p>
     *
     * @param animation The animation which reached its end.
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        Class start = (id == Joiin.NO_VALUE ? LoginActivity.class : MainActivity.class);

        Intent intent = new Intent(this, start);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    /**
     * <p>Notifies the start of the animation.</p>
     *
     * @param animation The started animation.
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * <p>Notifies the repetition of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}