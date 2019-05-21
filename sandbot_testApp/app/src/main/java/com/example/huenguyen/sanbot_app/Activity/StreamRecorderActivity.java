package com.example.huenguyen.sanbot_app.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.huenguyen.sanbot_app.Helper.AudioConvertor;
import com.example.huenguyen.sanbot_app.R;
import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.StreamOption;
import com.sanbot.opensdk.function.unit.HDCameraManager;
import com.sanbot.opensdk.function.unit.interfaces.media.MediaStreamListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HUE NGUYEN on 5/20/2019.
 */

public class StreamRecorderActivity extends BindBaseActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Button btnStartRecord;
    private Button btnStopRecord;

    private AudioConvertor convertor = new AudioConvertor();
    private HDCameraManager hdCameraManager;

    private  boolean record_state = false;
    //thong so de ghi ra file wav, can test lai do file xuat ra dang bi loi
    //file output nghe duoc nhung tieng bi lap lai
    private int sampleRate = 8000;
    private short channels = 1;
    private short BPS = 16;
    private int minBufferSize = 320;

    private File audioFile;
    private FileOutputStream wavOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(StreamRecorderActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_recorder_activity);
        btnStartRecord = findViewById(R.id.btn_startRecord);
        btnStopRecord = findViewById(R.id.btn_stopRecord);

        //get permission to write on storage
        verifyStoragePermissions(this);

        initHDCamera();
        //clear file before writing
        audioFile =  new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/record_1.wav");
        if(audioFile.exists() && audioFile.isFile()){
            audioFile.delete();
        }
        try {
            audioFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_state = true;
            }
        });

        btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_state = false;
                convertor.saveWaveFile(audioFile.getAbsolutePath(),
                                    android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/record_2.wav",
                                        sampleRate, channels, BPS, minBufferSize);

            }
        });
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void initHDCamera(){
        hdCameraManager = (HDCameraManager) getUnitManager(FuncConstant.HDCAMERA_MANAGER);
        hdCameraManager.openStream(new StreamOption(StreamOption.HARDWARE_DECODE, false, StreamOption.SUB_STREAM));
        hdCameraManager.setMediaListener(new MediaStreamListener() {
            @Override
            public void getVideoStream(int i, byte[] bytes, int i1, int i2) {

            }

            @Override
            public void getAudioStream(int i, byte[] bytes) {
                if(!record_state) return;
                try {
                    // log ra duong dan cua file ghi am
                    //dung adb de lay file ve may tinh, do chua tim duoc cho de xem file da luu tren robot
                    Log.d("huent", "record path " + audioFile.getAbsolutePath());
                    wavOut = new FileOutputStream(audioFile, true);
                    wavOut.write(bytes);
                    wavOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("huent", "getAudioStream: " + bytes.length);
            }
        });
    }
    @Override
    protected void onMainServiceConnected() {

    }






}
