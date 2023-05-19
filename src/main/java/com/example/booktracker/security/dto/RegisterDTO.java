package com.example.booktracker.security.dto;


import com.example.booktracker.security.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
public class RegisterDTO {

    @Column(nullable = false)
    @NotEmpty // ne sme biti null i ne sme biti prazno
    @Size(min = 2, max=20, message = "Your first name must be between 2 and 20 characters")
    private String firstName;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 2, max=20, message = "Your last name must be between 2 and 20 characters")
    private String lastName;

    @Email // samo proverava kada stizu podaci i validiraju se preko @Valid anotacije
    @NotEmpty // ovde validacije koristim preko starter- valudater is pom.xml file, to je ta biblioteka
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @Size(min = 2, max=20, message = "Your username must be between 2 and 20 characters")
    private String username;

    @Column(nullable = false)
    @Size(min=7, max=17, message = "Password should have between 7 or 17 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // preko Json ovaj atribut sluzi samo za slanje
    // i ne moze da se vraca nazad kao response
    private String password;


    private Set<Role> roles = new HashSet<>();
}
