package com.weather.com.location.dependencyinjection;



import com.weather.com.location.eventbus.EventBus;
import com.weather.com.location.eventbus.RxBus;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Abhisek on 05-02-2018.
 */

@Module
public abstract class BinderModule {
    @Binds
    abstract EventBus bindEventBus(RxBus rxBus);
}
