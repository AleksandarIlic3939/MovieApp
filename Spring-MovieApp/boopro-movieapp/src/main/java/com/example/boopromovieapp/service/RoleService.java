package com.example.boopromovieapp.service;

import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

}