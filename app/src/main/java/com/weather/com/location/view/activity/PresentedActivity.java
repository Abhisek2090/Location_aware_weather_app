package com.weather.com.location.view.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;


import com.weather.com.location.dependencyinjection.ApplicationModule;
import com.weather.com.location.dependencyinjection.DaggerPresenterComponent;
import com.weather.com.location.dependencyinjection.PresenterComponent;
import com.weather.com.location.presenter.Presenter;


public abstract class PresentedActivity<T extends Presenter> extends AppCompatActivity {

    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("isAppOnForegroud: ", String.valueOf(isAppOnForeground(this)));

        presenter.onDestroy();
    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void inject() {
        presenter = onCreatePresenter();
        PresenterComponent presenterComponent = DaggerPresenterComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        injectPresenter(presenterComponent, presenter);
    }

    protected abstract T onCreatePresenter();

    protected abstract void injectPresenter(PresenterComponent component, T presenter);

}
