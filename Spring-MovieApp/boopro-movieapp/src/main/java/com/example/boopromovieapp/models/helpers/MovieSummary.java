package com.example.boopromovieapp.models.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieSummary {

    private Integer id;
    private String title;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("genre_ids")
    private Integer[] genreIds;

}
