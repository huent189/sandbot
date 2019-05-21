package com.example.huenguyen.sanbot_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huenguyen.sanbot_app.R;
import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.RecognizeTextBean;
import com.sanbot.opensdk.function.unit.ProjectorManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;

/**
 * Created by HUE NGUYEN on 5/13/2019.
 */

public class MainActivity extends BindBaseActivity {
    private Button btn_playOnScreen;
    private Button btn_playOnProjector;
    private TextView tv_loggingText;
    private  TextView tv_recognize;

    private ProjectorManager projectorManager = (ProjectorManager)getUnitManager(FuncConstant.PROJECTOR_MANAGER);
    private boolean projectorState = false;
    private SpeechManager speechManager =(SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
    @Override
    protected void onMainServiceConnected() {
        //turn on / off projector
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
                // test lenh play video
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
            // onStartRecognize, onStopRecognize and onError work only on Chinese
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
        btn_playOnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
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
