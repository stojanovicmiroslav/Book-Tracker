package com.example.booktracker.security.permissions;


import com.example.booktracker.security.model.User;
import com.example.booktracker.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class Permission {


   private final UserRepository userRepository;



    public boolean checkUsersProfile(String username) {
        String loggedInUserUsername = SecurityContextHolder.getContext().getAuthentication().getName(); // izvlaci se username prijavljenog korisnika
        return username.equals(loggedInUserUsername);

    }


    public boolean checkUserIdOwnership(long userId) {
        String loggedInUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUsername().equals(loggedInUserUsername);
        }
        return false;
    }




}
