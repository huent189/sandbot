package com.example.huenguyen.sanbot_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.RecognizeTextBean;
import com.sanbot.opensdk.function.beans.speech.SpeakStatus;
import com.sanbot.opensdk.function.unit.ProjectorManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.SpeakListener;

/**
 * Created by HUE NGUYEN on 5/13/2019.
 */

public class MainActivity extends BindBaseActivity {
    private Button btn_playOnScreen;
    private Button btn_playOnProjector;
    private Button btn_droidRecognize;
    private TextView tv_loggingText;
    private  TextView tv_recognize;
    private ProjectorManager projectorManager = (ProjectorManager)getUnitManager(FuncConstant.PROJECTOR_MANAGER);
    private boolean projectorState = false;
    private SpeechManager speechManager =(SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
    @Override
    protected void onMainServiceConnected() {
        btn_playOnProjector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectorState = !projectorState;
                projectorManager.switchProjector(projectorState);
                Log.d("MainActivity", "onClick: " + projectorState);
            }
        });
        speechManager.startSpeak("Hello everyone, I'm Sandbot. Nice to meet you");
        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                tv_loggingText.setText("content recognized: " + grammar.getText());
                if(grammar.getText().toLowerCase().contains("play video")){
                    Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onRecognizeText(RecognizeTextBean recognizeTextBean) {
                tv_recognize.setText("RECOGNIZING");

            }

            @Override
            public void onRecognizeVolume(int i) {
                tv_recognize.setText(new Integer(i).toString());
            }

            @Override
            public void onStartRecognize() {

            }

            @Override
            public void onStopRecognize() {
            }

            @Override
            public void onError(int i, int i1) {


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_playOnScreen = findViewById(R.id.btn_playOnScreen);
        btn_playOnProjector = findViewById(R.id.btn_playUsingProjector);
        btn_droidRecognize = findViewById(R.id.btn_droidRecognize);
        btn_playOnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                startActivity(intent);
            }
        });
        btn_droidRecognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DroidSpeechRecoginzer.class);
                startActivity(intent);
            }
        });
        tv_loggingText = findViewById(R.id.tv_loggingText);
        tv_recognize = findViewById(R.id.tv_recognize);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tv_recognize.setText("null");
    }


}
