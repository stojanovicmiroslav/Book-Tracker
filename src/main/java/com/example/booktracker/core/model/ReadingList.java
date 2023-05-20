package com.example.booktracker.core.model;

import com.example.booktracker.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "readingList_table")
public class ReadingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty // Column must not be null and must not be empty
    @Size(min = 2, max = 30, message = "Reading List name must be between 2 and 30 characters")
    private String name; //    name: name of the Reading List
   // name: name of the reading list (e.g., "Currently Reading", "To Be Read", "Finished Reading")



    @ManyToOne
    @JoinColumn (name = "user_id")
    @JsonIgnore
    private User user;


    @OneToMany(cascade =  CascadeType.ALL)
    @JsonIgnore
    private Set<Book> book;

}
