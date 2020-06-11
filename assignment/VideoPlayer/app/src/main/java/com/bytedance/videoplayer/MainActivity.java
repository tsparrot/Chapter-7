package com.bytedance.videoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {

    private VideoView mvideoView;
    private int videoPosition = 0;
    private boolean isPlaying = false;


    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        mvideoView = findViewById(R.id.mvideoView);
        mvideoView.setVideoPath(getVideoPath(R.raw.mytest));
        MediaController videoController = new MediaController(this);
        mvideoView.setMediaController(videoController);
        videoController.setMediaPlayer(mvideoView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPosition = mvideoView.getCurrentPosition();
        isPlaying = mvideoView.isPlaying();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvideoView.seekTo(videoPosition);
        if (isPlaying)
            mvideoView.start();
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle state) {

        if (state != null) {
            int position = videoPosition;
            state.putInt("position", position);
            state.putBoolean("isPlaying", isPlaying);
        }
        super.onSaveInstanceState(state);
    }


    @Override
    protected void onRestoreInstanceState(@Nullable Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            int position = state.getInt("position");
            boolean isplaying = state.getBoolean("isPlaying");
            videoPosition = position;
            mvideoView.seekTo(position);
            isPlaying = isplaying;
            if (isPlaying)
                mvideoView.start();
        }

    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
