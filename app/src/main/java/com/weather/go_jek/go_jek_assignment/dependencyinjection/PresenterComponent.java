package com.weather.go_jek.go_jek_assignment.dependencyinjection;

import com.weather.go_jek.go_jek_assignment.presenter.MainActivityPresenter;

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

