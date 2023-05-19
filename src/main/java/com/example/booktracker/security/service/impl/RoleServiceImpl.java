package com.example.booktracker.security.service.impl;

import com.example.booktracker.security.model.Role;
import com.example.booktracker.security.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl {
    private final RoleRepository roleRepository;


    public Role findByName(String name) {

        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found "));
    }

    public Role save(Role role) {
      return   roleRepository.save(role);
    }
}
