package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhisek on 09-12-2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieObject implements Serializable {

    public List<MovieResultsData> getMovieResultsData() {
        return movieResultsData;
    }

    private List<MovieResultsData> movieResultsData;

    public MovieObject(@JsonProperty("results") List<MovieResultsData> movieResultsData) {
            this.movieResultsData = movieResultsData;
    }
}
