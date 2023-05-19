package com.example.booktracker.core.controller;

import com.example.booktracker.core.model.ReadingList;
import com.example.booktracker.core.model.Review;
import com.example.booktracker.security.model.User;
import com.example.booktracker.core.service.Impl.ReadingListServiceImpl;
import com.example.booktracker.core.service.Impl.ReviewServiceImpl;
import com.example.booktracker.security.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;
    private final ReadingListServiceImpl readingListService;
    private final ReviewServiceImpl reviewService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a single user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User userOptional = userService.getUserById(id);
        if (userOptional != null) {
            return new ResponseEntity<>(userOptional, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User userOptional = userService.getUserById(id);
        if (userOptional != null) {
            user.setId(id);
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an existing user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        User userOptional = userService.getUserById(id);
        if (userOptional != null) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a reading list
    @PostMapping("/{userId}/reading-lists")
    public ResponseEntity<ReadingList> addReadingListToUser(@PathVariable Long userId, @RequestBody ReadingList readingList) {
        User user = userService.getUserById(userId);
        if (user != null) {
            readingList.setUser(user);
            ReadingList savedReadingList = readingListService.saveReadingList(readingList);
            return new ResponseEntity<>(savedReadingList, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a review
    @PostMapping("/{userId}/reviews")
    public ResponseEntity<Review> addReviewToUser(@PathVariable Long userId, @RequestBody Review review) {
        User user = userService.getUserById(userId);
        if (user != null) {
            review.setUser(user);
            Review savedReview = reviewService.saveReview(review);
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
