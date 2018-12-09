package com.weather.go_jek.go_jek_assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abhisek on 25-11-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentLocationTemp implements Serializable {


    private double temp_c;

    public CurrentLocationTemp(@JsonProperty("temp_c") double temp_c ) {
                                this.temp_c = temp_c;
    }


    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

}
