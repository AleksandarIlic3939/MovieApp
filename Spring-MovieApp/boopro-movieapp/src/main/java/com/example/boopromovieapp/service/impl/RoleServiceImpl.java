package com.example.boopromovieapp.service.impl;

import com.example.boopromovieapp.models.Role;
import com.example.boopromovieapp.repository.RoleRepository;
import com.example.boopromovieapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}