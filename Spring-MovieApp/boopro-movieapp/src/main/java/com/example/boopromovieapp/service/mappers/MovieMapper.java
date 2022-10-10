package com.example.boopromovieapp.service.mappers;

import com.example.boopromovieapp.controller.dtos.MovieDTO;
import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.models.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    public Movie mapFromMovieDTO(MovieDTO movieDTO, Category category) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setCategory(category);
        movie.setImageSrc(movieDTO.getImageSrc());
        return movie;
    }

    public MovieDTO mapToMovieDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setName(movie.getName());
        movieDTO.setCategoryId(movie.getCategory().getId());
        movieDTO.setImageSrc(movie.getImageSrc());
        return movieDTO;
    }

}
