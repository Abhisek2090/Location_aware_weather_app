package com.weather.go_jek.go_jek_assignment.eventbus;

/**
 * Created by Abhisek on 04-02-2018.
 */

public interface EventBus {

    void subscribe(EventSubscriber subscriber);

    void unSubscribe();

    void postNext(Event event);

    void postError(Throwable t);

    void postCompletion();

    void postError(Throwable t, int requestCode);

    void postCompletion(int requestCode);
}
