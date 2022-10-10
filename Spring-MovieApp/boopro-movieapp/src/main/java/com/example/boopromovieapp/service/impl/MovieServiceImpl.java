package com.example.boopromovieapp.service.impl;

import com.example.boopromovieapp.controller.dtos.MovieDTO;
import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.models.Movie;
import com.example.boopromovieapp.repository.CategoryRepository;
import com.example.boopromovieapp.repository.MovieRepository;
import com.example.boopromovieapp.service.MovieService;
import com.example.boopromovieapp.service.mappers.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final MovieMapper movieMapper;

    @Override
    public Page<Movie> getAllByCategory(Integer id, Pageable pageable) {
        return movieRepository.findAllByCategory_Id(id, pageable);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }

    private Movie getMovie(MovieDTO movieDTO) {
        Category category = categoryRepository.findById(movieDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Movie movie = movieMapper.mapFromMovieDTO(movieDTO, category);
        return movie;
    }

    @Override
    public void save(MovieDTO movieDTO) {
        Movie movie = getMovie(movieDTO);
        movieRepository.save(movie);
    }

    @Override
    public void update(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteById(Integer id) {
        movieRepository.deleteById(id);
    }

}