package com.ujjwal.blogapi.controller;

import com.ujjwal.blogapi.dto.AuthResponse;
import com.ujjwal.blogapi.dto.LoginRequest;
import com.ujjwal.blogapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        // 1. Authenticate using the email address string context instead
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), // 🌟 Updated
                        loginRequest.getPassword()
                )
        );

        // 2. Load user details context via email string lookup
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail()); // 🌟 Updated

        // 3. Generate token string payload
        final String token = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}