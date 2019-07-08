package com.example.joe.qrfirefight.application;

import android.app.Application;

import com.example.joe.qrfirefight.utils.Utils;
import com.example.joe.qrfirefight.webservice.NetManager;

/**
 * Created by Joe on 2019-05-24.
 */

public class QrApplication extends Application {
    private final String TAG = "QrApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.getInstance().init(this);
        NetManager.getInstance().init(this);
    }
}
