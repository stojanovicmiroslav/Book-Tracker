package com.example.booktracker.security.configuration;

import com.example.booktracker.security.model.Token;
import com.example.booktracker.security.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

// /logout - POST
@Service
@AllArgsConstructor
public class LogoutService implements LogoutHandler {


    private final  TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
      String authHeader = request.getHeader("Authorization");
      if(authHeader == null || !authHeader.startsWith("Bearer ")){
          return;
      }
      String jwt = authHeader.substring(7);
      Optional<Token> tokenOptional = tokenRepository.findByToken(jwt);
      if(tokenOptional.isPresent()) {
          Token token = tokenOptional.get();
          token.setRevoked(true);
          tokenRepository.save(token);
      }
    }
}
