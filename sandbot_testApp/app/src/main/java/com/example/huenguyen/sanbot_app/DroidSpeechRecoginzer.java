package com.example.huenguyen.sanbot_app;

import android.os.Bundle;
import android.widget.TextView;

import com.sanbot.opensdk.base.BindBaseActivity;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

/**
 * Created by HUE NGUYEN on 5/13/2019.
 */

public class DroidSpeechRecoginzer extends BindBaseActivity implements OnDSPermissionsListener {
    private TextView tv_droidText;
    private DroidSpeech droidSpeech;
    @Override
    protected void onMainServiceConnected() {
        droidSpeech = new DroidSpeech(this, getFragmentManager());

    }

    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(DroidSpeechRecoginzer.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droid_speech);
        tv_droidText = findViewById(R.id.tv_droidText);
    }
}
