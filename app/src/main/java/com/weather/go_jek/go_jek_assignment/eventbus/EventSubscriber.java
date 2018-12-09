package com.weather.go_jek.go_jek_assignment.eventbus;

/**
 * Created by Abhisek on 04-02-2018.
 */

public interface EventSubscriber {

    void onEvent(Event event);
}
