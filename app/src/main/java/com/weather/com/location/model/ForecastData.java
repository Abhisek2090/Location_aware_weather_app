package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abhisek on 26-11-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastData implements Serializable {
    public ForecastObjectResponse getForecast() {
        return forecast;
    }

    public void setForecast(ForecastObjectResponse forecast) {
        this.forecast = forecast;
    }

    private ForecastObjectResponse forecast;

    public ForecastData (@JsonProperty("forecast") ForecastObjectResponse forecast) {
                        this.forecast = forecast;
    }
}
