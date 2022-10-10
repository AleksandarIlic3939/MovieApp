package com.example.boopromovieapp.service;

import com.example.boopromovieapp.controller.dtos.MovieDTO;
import com.example.boopromovieapp.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    Page<Movie> getAllByCategory(Integer id, Pageable pageable);

    Page<Movie> findAll(Pageable pageable);

    Movie getMovieById(Integer id);

    void save(MovieDTO movieDto);

    void update(Movie movie);

    void deleteById(Integer id);

}