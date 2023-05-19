package com.example.booktracker.core.repository;

import com.example.booktracker.core.model.Book;
import com.example.booktracker.core.model.ReadingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {



}
