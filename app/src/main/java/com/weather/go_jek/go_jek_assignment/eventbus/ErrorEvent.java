package com.weather.go_jek.go_jek_assignment.eventbus;

public class ErrorEvent implements Event {
    private final String TAG = ErrorEvent.class.getSimpleName();
    private Throwable t;
    private int requestCode;

    public ErrorEvent(Throwable t, int requestCode) {
        this.t = t;
        this.requestCode = requestCode;
    }

    public ErrorEvent(Throwable t) {
        this.t = t;
    }

    @Override
    public int getType() {
        return TYPE_ERROR;
    }

    @Override
    public int getRequestCode() {
        return requestCode;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Throwable getResult() {
        return t;
    }

}
