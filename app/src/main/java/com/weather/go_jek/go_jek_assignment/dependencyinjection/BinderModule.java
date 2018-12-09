package com.weather.go_jek.go_jek_assignment.dependencyinjection;



import com.weather.go_jek.go_jek_assignment.eventbus.EventBus;
import com.weather.go_jek.go_jek_assignment.eventbus.RxBus;

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
