package com.weather.go_jek.go_jek_assignment.dependencyinjection;

import android.app.Activity;

import com.weather.go_jek.go_jek_assignment.eventbus.RxBus;
import com.weather.go_jek.go_jek_assignment.restservice.RestService;

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
