package com.example.booktracker.core.controller;

import com.example.booktracker.core.model.Book;
import com.example.booktracker.core.model.ReadingList;
import com.example.booktracker.core.service.Impl.ReadingListServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/reading-lists")
@AllArgsConstructor
public class ReadingListController {


    private final ReadingListServiceImpl readingListService;

    // Get all reading lists
    @GetMapping
    public ResponseEntity<List<ReadingList>> getAllReadingLists() {
        List<ReadingList> readingLists = readingListService.getAllReadingLists();
        return new ResponseEntity<>(readingLists, HttpStatus.OK);
    }

    // Get a single reading list by id
    @GetMapping("/{id}")
    @PreAuthorize("!@permission.checkUserIdOwnership(#id)")
    public ResponseEntity<ReadingList> getReadingListById(@PathVariable Long id) {
        ReadingList readingList = readingListService.getReadingListById(id);
        if (readingList != null) {
            return new ResponseEntity<>(readingList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing reading list
    @PutMapping("/{id}")
    @PreAuthorize("!@permission.checkUserIdOwnership(#id)")
    public ResponseEntity<ReadingList> updateReadingList(@PathVariable Long id, @RequestBody ReadingList readingList) {
        ReadingList existingReadingList = readingListService.getReadingListById(id);
        if (existingReadingList != null) {
            readingList.setId(id);
            ReadingList savedReadingList = readingListService.saveReadingList(readingList);
            return new ResponseEntity<>(savedReadingList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an existing reading list by id
    @DeleteMapping("/{id}")
    @PreAuthorize("!@permission.checkUserIdOwnership(#id)")
    public ResponseEntity<ReadingList> deleteReadingListById(@PathVariable Long id) {
        ReadingList existingReadingList = readingListService.getReadingListById(id);
        if (existingReadingList != null) {
            readingListService.deleteReadingListById(id);
            return new ResponseEntity<>(existingReadingList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a book to a reading list
    @PostMapping("/{readingListId}/addBokk")
    public ResponseEntity<Book> addBookToReadingList(@PathVariable Long readingListId, @RequestBody Book book) {
        ReadingList readingList = readingListService.getReadingListById(readingListId);
        if (readingList != null) {
            Set<Book> books = readingList.getBook();
            books.add(book);
            readingList.setBook(books);
            readingListService.saveReadingList(readingList);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
