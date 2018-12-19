package com.weather.com.location.application;

/**
 * Created by Abhisek on 25-11-2018.
 */

import android.support.multidex.MultiDexApplication;

import com.weather.com.location.receiver.ConnectivityReceiver;

public class GO_JEK_Weather_application extends MultiDexApplication {

    private static GO_JEK_Weather_application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized GO_JEK_Weather_application getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
