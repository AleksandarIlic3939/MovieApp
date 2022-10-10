package com.example.boopromovieapp.service;

import com.example.boopromovieapp.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Page<Category> getAll(Pageable pageable);

    Category getById(Integer id);

    void save(Category category);

    void deleteById(Integer id);

}