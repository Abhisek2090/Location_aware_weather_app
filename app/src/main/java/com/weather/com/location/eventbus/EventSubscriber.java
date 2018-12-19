package com.weather.com.location.eventbus;

/**
 * Created by Abhisek on 04-02-2018.
 */

public interface EventSubscriber {

    void onEvent(Event event);
}
