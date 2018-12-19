package com.weather.com.location.restservice;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Looper;
import android.widget.Toast;


import com.weather.com.location.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;



public class NetworkInterceptor implements Interceptor {

    private final String TAG = NetworkInterceptor.class.getSimpleName();
    private Context context;
    NetworkInterceptor(Context context) {this.context = context;}

    @Override
    public Response intercept(Chain chain) throws IOException {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = false;
        if (connectivityManager != null) {
            isConnected = connectivityManager.getActiveNetworkInfo() != null;
        }

        if(isConnected) {

            return chain.proceed(chain.request());
        } else  {

            throw new NoInternetException(R.string.device_not_connected_msg);
        }



    }

    public class NoInternetException extends IOException {

        NoInternetException(int stringRes) {
            super(context.getString(stringRes));
            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (isAppOnForeground(context)) {
                        Toast.makeText(context, R.string.device_not_connected_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();


            if(appProcesses == null) {
                return false;
            }

            final String packageName = context.getPackageName();
            for(ActivityManager.RunningAppProcessInfo appProcess: appProcesses) {
                if(appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }


}


