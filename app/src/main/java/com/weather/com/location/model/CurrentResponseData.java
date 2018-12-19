package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Abhisek on 25-11-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentResponseData {

    public CurrentLocationTemp getCurrent() {
        return current;
    }

    private CurrentLocationTemp current;

    public CurrentResponseData(@JsonProperty("current")CurrentLocationTemp current) {
                                this.current = current;
    }

}
