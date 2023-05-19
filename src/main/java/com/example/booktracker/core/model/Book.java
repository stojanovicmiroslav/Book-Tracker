package com.example.booktracker.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_table")
public class Book {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @NotEmpty // Column must not be null and must not be empty
    @Size(min = 2, max = 30, message = "Book title name must be between 2 and 30 characters")
    private String title; //    title: title of the book


    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max = 30, message = "Author name must be between 2 and 30 characters")
    private String author; //    author: author of the book


    @Column(nullable = false)
    private Integer publicationYear; //    publicationYear: year the book was published


    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max = 30, message = "Publisher name must be between 2 and 30 characters")
    private String publisher; //    publisher: name of the publisher


    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max = 30, message = "ISBN  must be between 2 and 30 characters")
    private String isbn; //    isbn: International Standard Book Number (ISBN) of the book


    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max = 200, message = "URL  must be between 2 and 200 characters")
    private String coverImageUrl; //    coverImageUrl: URL of the book's cover image


    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max = 250, message = "ISBN  must be between 2 and 250 characters")
    private String description;//    description: a brief summary of the book



}
