package com.example.booktracker.security.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {

    // DTO classes must also have same annotation as normal model classes


    @Column(nullable = false, unique = true)
    @NotEmpty
    @Size(min = 2, max=20, message = "Your username must be between 2 and 20 characters")
    private String username;

    @Column(nullable = false)
    @Size(min=7, max=17, message = "Password should have between 7 or 17 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // preko Json ovaj atribut sluzi samo za slanje
    // i ne moze da se vraca nazad kao response
    private String password;



}
