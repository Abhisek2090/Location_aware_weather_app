package com.weather.com.location.eventbus;

/**
 * Created by Abhisek on 04-02-2018.
 */

public interface Event {
    int TYPE_SUCCESS =1;
    int TYPE_ERROR = 2;
    int TYPE_COMPLETION = 3;

    int getType();

    <T> T getResult();

    int getRequestCode();

    int getStatusCode();
}
