package com.example.booktracker.security.permissions;


import com.example.booktracker.core.model.ReadingList;
import com.example.booktracker.core.repository.ReadingListRepository;
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
   private final ReadingListRepository readingListRepository;



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


    public boolean checkUserReadinListOwnership(Long readingListId) {
        String loggedInUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<ReadingList> readingListOptional = readingListRepository.findById(readingListId);

        if (readingListOptional.isPresent()) {
            ReadingList readingList = readingListOptional.get();

            return readingList.getUser().getUsername().equals(loggedInUserUsername);
        }
        return false;
    }



}
