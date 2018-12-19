package com.weather.com.location.eventbus;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;



public interface SchedulerProvider {

    SchedulerProvider DEFAULT = new SchedulerProvider() {

        @Override
        public <T> Observable.Transformer<T, T> applySchedulers() {
            return new Observable.Transformer<T, T>() {
                @Override
                public Observable<T> call(Observable<T> observable) {
                    return observable.observeOn(AndroidSchedulers.mainThread());
                }
            };
        }

    };

    <T>Observable.Transformer<T, T> applySchedulers();
}
