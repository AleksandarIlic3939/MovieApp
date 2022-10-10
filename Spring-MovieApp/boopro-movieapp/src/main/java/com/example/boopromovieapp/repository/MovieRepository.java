package com.example.boopromovieapp.repository;

import com.example.boopromovieapp.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Page<Movie> findAllByCategory_Id(Integer id, Pageable pageable);

}