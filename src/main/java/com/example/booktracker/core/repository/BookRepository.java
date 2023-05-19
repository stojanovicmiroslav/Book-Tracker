package com.example.booktracker.core.repository;

import com.example.booktracker.core.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
