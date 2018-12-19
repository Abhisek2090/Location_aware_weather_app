package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abhisek on 25-11-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DayObject implements Serializable {


    private double avgtemp_c;

    public DayObject(@JsonProperty("avgtemp_c") double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }



}
