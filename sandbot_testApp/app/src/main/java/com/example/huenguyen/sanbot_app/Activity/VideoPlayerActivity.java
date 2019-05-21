package com.example.huenguyen.sanbot_app.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.huenguyen.sanbot_app.R;
import com.sanbot.opensdk.base.BindBaseActivity;

/**
 * Created by HUE NGUYEN on 5/13/2019.
 */

public class VideoPlayerActivity extends BindBaseActivity {
    private VideoView videoView;
    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(VideoPlayerActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoView = findViewById(R.id.videoView);
        String uriPath = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        videoView.setVideoURI(Uri.parse(uriPath));
        videoView.requestFocus();
        videoView.start();
    }
}
