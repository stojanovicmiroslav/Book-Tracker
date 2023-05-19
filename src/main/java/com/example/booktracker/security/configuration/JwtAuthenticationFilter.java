package com.example.booktracker.security.configuration;
// klijenti  ->  filter -> filter2->  kontroler


import com.example.booktracker.security.model.Token;
import com.example.booktracker.security.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {  // ovaj se najvise koristi


    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // izvucemo token iz zahteva, i proveravamo da li je token validan itd.

        String authHeader = request.getHeader("Authorization");
        // http has a header and body, in the header is the token
        // in body is json to be sent
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = authHeader.substring(7);
        // the token in header begin from index 7


        String username = jwtService.extractUsername(jwt);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);


            Optional<Token> tokenOptional = tokenRepository.findByToken(jwt);
            boolean isTokenValid = true;
            if(tokenOptional.isEmpty()){
                isTokenValid = false;
            }
            else{
                isTokenValid = !tokenOptional.get().isRevoked();

            }


            // here below I check if my filter is still valid or revoked
            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // In SecurityContextHolder are stored data of current logged user,
                // and I kad receive info about him
            }

        }
        filterChain.doFilter(request, response);
        // after checking the Token is going to the next filter


    }
}
