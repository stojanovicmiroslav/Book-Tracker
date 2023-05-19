package com.example.booktracker.security.configuration;


import com.example.booktracker.security.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class ApplicationConfig { // ovde ne mora da ima nuzno veze sa sekurity
    // uzeli smo binove koje na trebaju i posle koristimo
    // ovde stavljam moje bean-ove, i tu ih konfigurisem kako mi budu posle trebali, kao naprimer Authentikation provider
    // tamo gde mi budu trebali, ja ih zovem sa Autowired i oni su vec spremljeni za koriscenje


   private  final UserDetailsServiceImpl userDetailsService;
   // I have Created in my configuration package UserDetailsServiceImpl, and there I call
    // from userRepository user.findByUsername



    @Bean
    // je sinonim za objekat
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   @Bean
    public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // imaju i ostali provideri, da li se koriste?
       authProvider.setUserDetailsService(userDetailsService);
       authProvider.setPasswordEncoder(passwordEncoder());
       return authProvider;

   }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        //configuration.setAllowedOrigins(List.of("https://www.yourdomain.com")); // www - obligatory
        configuration.setAllowedOriginPatterns(List.of("*"));  //set access from all domains
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    // bean znaci objekat neke klase

    // bean stoji iznad metoda koji vracaju taj objekat

    // i svugde gde stoji autowired za taj tip u te atribute ce se staviti ti objekti(iz metoda koje imaju bean iznad)
}
