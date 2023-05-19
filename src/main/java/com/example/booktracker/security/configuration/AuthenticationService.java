package com.example.booktracker.security.configuration;



import com.example.booktracker.security.dto.LoginDTO;
import com.example.booktracker.security.dto.LoginResponseDTO;
import com.example.booktracker.security.dto.RegisterDTO;
import com.example.booktracker.security.model.Token;
import com.example.booktracker.security.model.User;
import com.example.booktracker.security.repository.TokenRepository;
import com.example.booktracker.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.*;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final  JwtService jwtService;

    private final UserDetailsService userDetailsService;


     private final PasswordEncoder passwordEncoder;

     private final UserRepository userRepository;

    private final TokenRepository tokenRepository;




     public User register(RegisterDTO registerDTO){
         User user = new User();
         user.setFirstName(registerDTO.getFirstName());
         user.setLastName(registerDTO.getLastName());
         user.setEmail(registerDTO.getEmail());
         user.setUsername(registerDTO.getUsername());
         user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
         user.setRoles(registerDTO.getRoles());
         User savedUser = userRepository.save(user);
         return savedUser;
     }

    public LoginResponseDTO login(LoginDTO loginDTO) throws AuthenticationException {
        User userDetails = (User) userDetailsService.loadUserByUsername(loginDTO.getUsername());



        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        Map<String, Object> claim = new HashMap<>();
        claim.put("role", userDetails.getRoles().iterator().next().getAuthority());
        // here with interator I am taking first role, in my app every user has only one role, it is mapped one to one


        String token = jwtService.generateToken(claim, userDetails);
        // here I must to put manually my Claims (in my case the role)
        // second parametar od method generateToken is userdetails

        List<Token> listAllValidTokes = tokenRepository.findByRevokedAndUser(false, userDetails);
        listAllValidTokes.forEach(token1 -> {token1.setRevoked(true);});
        tokenRepository.saveAll(listAllValidTokes);

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(userDetails);
        tokenEntity.setRevoked(false);

        tokenRepository.save(tokenEntity);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();



        loginResponseDTO.setToken(token);
        loginResponseDTO.setUserId(userDetails.getId());

       return loginResponseDTO;
    }

}
