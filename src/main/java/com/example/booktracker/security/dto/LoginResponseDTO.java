package com.example.booktracker.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


// preimenuj ovu klasu u LoginResponseDTO
// i promeni odgovrajuci kod da kada se korisnik prijavi da vrati i token i userId
@Data
@NoArgsConstructor
public class LoginResponseDTO {
    private String token;
    private long userId;
}
// kad se neko loginuje odgovor ce da bude {"token": "dadsadadfu98f9sd9"}