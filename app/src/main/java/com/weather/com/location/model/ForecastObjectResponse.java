package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhisek on 25-11-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastObjectResponse implements Serializable {


    private List<ForecastDayResponse> forecastday;



    public ForecastObjectResponse(@JsonProperty("forecastday") List<ForecastDayResponse> forecastday) {
                                    this.forecastday = forecastday;

    }

    public List<ForecastDayResponse> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<ForecastDayResponse> forecastday) {
        this.forecastday = forecastday;
    }





}

