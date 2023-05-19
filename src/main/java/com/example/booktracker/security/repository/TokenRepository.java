package com.example.booktracker.security.repository;


import com.example.booktracker.security.model.Token;
import com.example.booktracker.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByRevokedAndUser(boolean revoked, User user);

    // napravi nam metodu koja trazi token po atributi token
    Optional<Token> findByToken(String token);

}
