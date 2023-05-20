package com.example.booktracker.security.controller;


import com.example.booktracker.security.configuration.AuthenticationService;
import com.example.booktracker.security.dto.LoginDTO;
import com.example.booktracker.security.dto.LoginResponseDTO;
import com.example.booktracker.security.dto.RegisterDTO;
import com.example.booktracker.security.model.Role;
import com.example.booktracker.security.model.User;
import com.example.booktracker.security.service.impl.RoleServiceImpl;
import com.example.booktracker.security.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {


   private final AuthenticationService authenticationService;

   private final UserServiceImpl userService;
   private final RoleServiceImpl roleService;

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@Valid @RequestBody RegisterDTO registerDTO){ // anotacija Valid je obavezna ako hocu da validiram Validacije iz modela


        if (userService.existsByEmail(registerDTO.getEmail())) {
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Role user_role = roleService.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(user_role);
        registerDTO.setRoles(roles);

        User savedUser = authenticationService.register(registerDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // 201 statis CREATED
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws AuthenticationException {

        LoginResponseDTO loginResponseDTO = authenticationService.login(loginDTO);
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK); // ovde se vraca nazad token

    }





}
