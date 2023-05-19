package com.example.booktracker.security.service.impl;

import com.example.booktracker.security.model.User;
import com.example.booktracker.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j // for logs
public class UserServiceImpl {
    private final UserRepository userRepository;


    public List<User> getAllUsers() {
     return   userRepository.findAll();
    }

    public User getUserById(Long id) {
       return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found "));
    }

    public User saveUser(User user) {
      return  userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public boolean existsByEmail(String email) {
      return  userRepository.existsByEmail(email);
    }
}
