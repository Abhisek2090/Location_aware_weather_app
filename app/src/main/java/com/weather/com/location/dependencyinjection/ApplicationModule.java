package com.weather.com.location.dependencyinjection;

import android.app.Activity;

import com.weather.com.location.eventbus.RxBus;
import com.weather.com.location.restservice.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhisek on 25-11-2018.
 */

@Module
public class ApplicationModule {
    private Activity activity;

    public ApplicationModule(Activity activity) {this.activity = activity;}

    @Singleton
    @Provides
    RxBus getEventBus() { return new RxBus(); }

    @Provides
    RestService getRestService(RxBus eventBus) {return  new RestService(activity, eventBus);}
}
