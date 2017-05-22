package com.MinimalSoft.BUniversitaria.Start;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.VideoView;

import com.MinimalSoft.BUniversitaria.BU;
import com.MinimalSoft.BUniversitaria.Main.MainActivity;
import com.MinimalSoft.BUniversitaria.R;

public class LaunchActivity extends Activity implements Runnable {
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + '/' + R.raw.vid_welcome);
        videoView = (VideoView) findViewById(R.id.launch_videoView);
        videoView.setVideoURI(uri);
        videoView.start();

        new Handler().postDelayed(this, 2500);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Intent intent;
        int id = getSharedPreferences(BU.PREFERENCES, MODE_PRIVATE)
                .getInt(BU.USER_ID, BU.NO_VALUE);

        if (id == BU.NO_VALUE) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}