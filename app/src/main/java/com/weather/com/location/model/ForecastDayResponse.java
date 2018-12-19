package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abhisek on 25-11-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDayResponse implements Serializable {



    private String date;
    private DayObject day;

    public ForecastDayResponse(@JsonProperty("date") String date,
                               @JsonProperty("day") DayObject day) {
                    this.date = date;
                    this.day = day;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DayObject getDay() {
        return day;
    }

    public void setDay(DayObject day) {
        this.day = day;
    }
}
