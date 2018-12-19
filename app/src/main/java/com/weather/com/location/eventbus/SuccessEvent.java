package com.weather.com.location.eventbus;

import retrofit2.Response;

public class SuccessEvent<T> implements Event {

    private final String TAG = SuccessEvent.class.getSimpleName();

    private T result;
    private int requestCode;
    private int statusCode;

    public SuccessEvent(T result, int requestCode) {
        this.result = result;
        this.requestCode = requestCode;
        this.statusCode = ((Response) result).code();
    }

    public SuccessEvent(T result) {
        this.result = result;
    }

    @Override
    public int getType() {
        return TYPE_SUCCESS;
    }

    @Override
    public T getResult() {
        return (T) ((Response) result).body();
    }

    @Override
    public int getRequestCode() {
        return requestCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

}
