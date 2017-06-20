package com.MinimalSoft.Joiin.Start;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.VideoView;

import com.MinimalSoft.Joiin.Joiin;
import com.MinimalSoft.Joiin.Main.MainActivity;
import com.MinimalSoft.Joiin.R;

public class LaunchActivity extends Activity implements Runnable {

    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        /*
        String videoPath;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            videoPath = "android.resource://" + getPackageName() + '/' + R.raw.vid_welcome_v5;
        } else {
            videoPath = "android.resource://" + getPackageName() + '/' + R.raw.vid_welcome;
        }

        VideoView videoView = (VideoView) findViewById(R.id.launch_videoView);
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(this, 2500);
*/

        mVideoView = (VideoView) findViewById(R.id.launch_videoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setVisibility(View.VISIBLE);
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
        int id = getSharedPreferences(Joiin.PREFERENCES, MODE_PRIVATE)
                .getInt(Joiin.USER_ID, Joiin.NO_VALUE);

        if (id == Joiin.NO_VALUE) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}