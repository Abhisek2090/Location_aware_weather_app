package com.weather.go_jek.go_jek_assignment.eventbus;

/**
 * Created by Abhisek on 04-02-2018.
 */

public class CompletionEvent implements Event {

    private int requestCode;

    public CompletionEvent(int requestCode) {
        this.requestCode = requestCode;
    }

    public CompletionEvent() {
    }

    @Override
    public int getType() {
        return TYPE_COMPLETION;
    }

    @Override
    public <T> T getResult() {
        return null;
    }

    @Override
    public int getRequestCode() {
        return requestCode;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

}
