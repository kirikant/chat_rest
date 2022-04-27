package com.chat.chat.controllers;

import com.chat.chat.dto.AuthenticationDto;
import com.chat.chat.entity.UserEntity;
import com.chat.chat.repositories.UserRepository;
import com.chat.chat.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationDto authenticationDto) throws EntityNotFoundException {
          String login = authenticationDto.getLogin();
        UserEntity user = userRepository.findByLogin(login)
                .orElseThrow(()->new EntityNotFoundException("User with login " +
                login + " not found"));
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(login,
                          authenticationDto.getPassword()));
          String token = jwtTokenProvider.createToken(user.getLogin(), user.getRole().name());
          Map<Object, Object> response = new HashMap<>();
          response.put("login", user.getLogin());
          response.put("token", token);
          return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
