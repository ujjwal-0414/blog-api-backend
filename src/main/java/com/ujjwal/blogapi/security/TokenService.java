package com.ujjwal.blogapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService { // it takes an authenticated user's identity, packages it with expiration rules, and signs it with a cryptographic key to issue a secure token string for stateless API requests

    private final JwtEncoder jwtEncoder; // not autowired bcz using constructor injection

    // Spring Security will automatically inject the JwtEncoder bean we defined in SecurityConfig
    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    //Generates a cryptographically signed OAuth2 JWT token string
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        // Build the standard OAuth2 token payload (Claims)
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("blog-api")                               // Identifies who issued the token
                .issuedAt(now)                                    // Timestamp of creation
                .expiresAt(now.plus(1, ChronoUnit.DAYS))          // Token valid for exactly 24 hours
                .subject(authentication.getName())                // Sets the username as the token owner
                .build();

        // Sign the claims payload with your RSA private key and extract the string value
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
