package com.weather.go_jek.go_jek_assignment.restservice;



import com.weather.go_jek.go_jek_assignment.model.CurrentResponseData;
import com.weather.go_jek.go_jek_assignment.model.ForecastData;
import com.weather.go_jek.go_jek_assignment.model.ForecastObjectResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Abhisek on 25-11-2018.
 */

interface RestInterface {

    String BASE_URL ="https://api.apixu.com/v1/";

    @GET("current.json?")
    Observable<Response<CurrentResponseData>> getCurrentTemperature(@Query("key") String key, @Query("q") String location);


    @GET("forecast.json?")
    Observable<Response<ForecastData>> getForecastTemperature(@Query("key") String key, @Query("q") String location);

}
