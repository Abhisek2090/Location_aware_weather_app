package com.weather.com.location.eventbus;


import com.weather.com.location.restservice.NetworkInterceptor;

import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;



public class RxBus implements EventBus {

    private final String TAG = RxBus.class.getSimpleName();

    private final Subject<Event, Event> rxBus = new SerializedSubject<>(PublishSubject.<Event>create());
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public void postNext(Event event) {

        try {

            rxBus.onNext(event);
        }catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void postError(Throwable t) {
        try {

            t.printStackTrace();
            if(t instanceof NetworkInterceptor.NoInternetException) {
                rxBus.onNext(new CompletionEvent());
            } else {

                rxBus.onNext(new ErrorEvent(t));
            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postCompletion() {
        try {

            rxBus.onNext(new CompletionEvent());
        }catch (Throwable t) {
            t.printStackTrace();
        }
    }


    @Override
    public void postError(Throwable t, int requestCode) {
        try{

            t.printStackTrace();
            if(t instanceof NetworkInterceptor.NoInternetException) {
                rxBus.onNext(new CompletionEvent(requestCode));
            } else  {
                rxBus.onNext(new ErrorEvent(t, requestCode));
            }
        }catch (Throwable e) {
            e.printStackTrace();

        }

    }

    @Override
    public void postCompletion(int requestCode) {

        try {

            rxBus.onNext(new CompletionEvent(requestCode));
        }catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void subscribe(final EventSubscriber subscriber) {
        subscriptions.add(rxBus.subscribe(new Subscriber<Event>() {
            @Override
            public void onCompleted() {

                subscriber.onEvent(new CompletionEvent());

            }

            @Override
            public void onError(Throwable e) {

                subscriber.onEvent(new ErrorEvent(e));
                e.printStackTrace();

            }

            @Override
            public void onNext(Event event) {
                try {

                    subscriber.onEvent(event);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    @Override
    public void unSubscribe() {

        subscriptions.unsubscribe();
    }

}
