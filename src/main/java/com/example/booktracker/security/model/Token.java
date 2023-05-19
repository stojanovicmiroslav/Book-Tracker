package com.example.booktracker.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="token_table")
public class Token {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // automatski generiše id da ga ne moramo mi praviti
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;


    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private boolean revoked;


    @ManyToOne // user can have multiple token when logged
    private User user;

}
