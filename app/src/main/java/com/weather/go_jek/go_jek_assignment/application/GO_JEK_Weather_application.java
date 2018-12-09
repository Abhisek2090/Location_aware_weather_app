package com.weather.go_jek.go_jek_assignment.application;

/**
 * Created by Abhisek on 25-11-2018.
 */

import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.weather.go_jek.go_jek_assignment.receiver.ConnectivityReceiver;

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
