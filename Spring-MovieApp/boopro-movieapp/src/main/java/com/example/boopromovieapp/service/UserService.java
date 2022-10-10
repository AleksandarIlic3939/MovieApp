package com.example.boopromovieapp.service;

import com.example.boopromovieapp.controller.dtos.UserDTO;
import com.example.boopromovieapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> getAll(Pageable pageable);

    User getUserByEmail(String email);

    User getUserById(Integer id);

    void save(UserDTO userDTO);

    void update(User user);

    void deleteById(Integer id);

}