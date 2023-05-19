package com.example.booktracker.security.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User  implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//    id: unique identifier for the user

    @Column(nullable = false)
    @NotEmpty // Column must not be null and must not be empty
    @Size(min = 2, max = 30, message = "First Name must be between 2 and 30 characters")
    private String firstName; //    username: username of the user

    @Column(nullable = false)
    @NotEmpty // Column must not be null and must not be empty
    @Size(min = 2, max = 30, message = "Last Name must be between 2 and 30 characters")
    private String lastName; //    username: username of the user

    @Column(nullable = false, unique = true) //   username must be unique for the user
    @NotEmpty // Column must not be null and must not be empty
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters")
    private String username; //    username: username of the user


    @Column(nullable = false, unique = true) //   email must be unique for the user
    @NotEmpty
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    @Email // email jakarta validation, email must be valid when come to the user creation
    private String email; //    email: email address of the user


    @Column(nullable = false)
//    @Pattern(message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character:",
//            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[+#?!@$%^&*-]).{8,}$")
    // lombok annotation indicates which fields have to be excluded in the string representation of the object
    @NotEmpty(message = "Password can't be empty")
    private String password;//    password: password for the user's account


    @ManyToMany(fetch = FetchType.EAGER) // Eager sve procitava, za razliku od LAZY,
    // Eager se koristi za malu kolicinu podataka
    @JoinTable(name = "user_role_table")
    private Set<Role> roles = new HashSet<>();




    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}