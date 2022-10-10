package com.example.boopromovieapp.models.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieSummaries {

    @JsonProperty("results")
    private MovieSummary[] movieSummaries;

}