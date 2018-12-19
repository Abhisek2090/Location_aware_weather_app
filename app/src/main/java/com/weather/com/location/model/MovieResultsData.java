package com.weather.com.location.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abhisek on 09-12-2018.
 */

public class MovieResultsData implements Serializable {

    public String getTitle() {
        return title;
    }

    public int getVote_count() {
        return vote_count;
    }

    private String title;
    private int vote_count;

    public String getPoster_path() {
        return poster_path;
    }

    private String poster_path;

    public MovieResultsData(@JsonProperty("title") String title,
                            @JsonProperty("vote_count") int vote_count,
                            @JsonProperty("poster_path") String poster_path) {
                    this.title = title;
                    this.vote_count = vote_count;
                    this.poster_path = poster_path;
    }
 }
