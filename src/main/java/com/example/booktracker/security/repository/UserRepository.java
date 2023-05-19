package com.example.booktracker.security.repository;

import com.example.booktracker.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserDetails> findByUsername(String username);
    boolean existsByEmail (String email);
}
