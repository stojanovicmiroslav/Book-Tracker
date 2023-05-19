package com.example.booktracker.core.repository;

import com.example.booktracker.core.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
