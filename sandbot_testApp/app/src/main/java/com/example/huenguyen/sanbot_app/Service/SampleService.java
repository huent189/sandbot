package com.example.huenguyen.sanbot_app.Service;

import com.sanbot.opensdk.base.BindBaseService;

/**
 * Created by HUE NGUYEN on 5/13/2019.
 */

public class SampleService extends BindBaseService {
    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    public void onCreate() {
        register(SampleService.class);
        super.onCreate();
    }
}
