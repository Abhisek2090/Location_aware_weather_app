package com.weather.com.location.restservice;

import android.content.Context;
import android.util.SparseArray;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weather.com.location.BuildConfig;
import com.weather.com.location.eventbus.RxBus;
import com.weather.com.location.eventbus.SchedulerProvider;
import com.weather.com.location.eventbus.SuccessEvent;
import com.weather.com.location.model.CurrentResponseData;
import com.weather.com.location.model.ForecastData;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Abhisek on 25-11-2018.
 */

public class RestService {

    private final String TAG = RestService.class.getSimpleName();

    private RestInterface restInterface;
    private RxBus eventBus;
    private Context context;

    private SparseArray<Subscription> subscriptions;

    public RestService(Context context, RxBus eventBus) {
        this.eventBus = eventBus;
        this.context = context;
        this.subscriptions = new SparseArray<>();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);
      //  clientBuilder.addInterceptor(new NetworkInterceptor(context));


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        restInterface = new Retrofit.Builder()
                .baseUrl(RestInterface.BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(RestInterface.class);
    }


    public RestService(Context context) {
        this(context, null);
    }

    private <T> Subscriber<T> createSubscriber() {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {

                eventBus.postCompletion();

            }

            @Override
            public void onError(Throwable e) {

                eventBus.postError(e);
            }

            @Override
            public void onNext(T t) {

                eventBus.postNext(new SuccessEvent<>(t));
            }
        };
    }

    private <T> Subscriber<T> createSubscriber(final int requestCode) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                subscriptions.remove(requestCode);
                if(eventBus!=null) {
                    eventBus.postCompletion(requestCode);
                }
            }

            @Override
            public void onError(Throwable e) {
                subscriptions.remove(requestCode);
                if(eventBus!= null) {
                    eventBus.postError(e, requestCode);
                }
            }

            @Override
            public void onNext(T t) {

                if(eventBus !=null) {
                    eventBus.postNext(new SuccessEvent<>(t, requestCode));
                }
            }
        };
    }

    public class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }

    public void getForecastTemperature(String key, String location, int requestcode) {
        restInterface.getForecastTemperature(key, location)
                .compose(SchedulerProvider.DEFAULT.<Response<ForecastData>> applySchedulers())
                .subscribe(this.<Response<ForecastData>>createSubscriber(requestcode));
    }

    public void getCurrentTemperature(String key, String location, int requestcode) {
        restInterface.getCurrentTemperature(key, location)
                .compose(SchedulerProvider.DEFAULT.<Response<CurrentResponseData>> applySchedulers())
                .subscribe(this.<Response<CurrentResponseData>>createSubscriber(requestcode));
    }

}
