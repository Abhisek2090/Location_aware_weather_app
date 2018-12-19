package com.weather.com.location.dependencyinjection;

import com.weather.com.location.presenter.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhisek on 25-11-2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, BinderModule.class})
public interface PresenterComponent {

    void inject (MainActivityPresenter presenter);
}

