package com.ujjwal.blogapi.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyManager rsaKeys; // not autowired bcz using constructor injection

    // injecting our RSA key manager bean
    public SecurityConfig(RsaKeyManager rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    // it will automatically hash raw password before saving to database using BCrypt
    @Bean // Password Encoder Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // it will act as a gatekeeper for all incoming HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disabling CSRF bcz REST APIs are stateless (using tokens, not sessions)
        http.csrf(csrf -> csrf.disable())

                //authorization rules
                .authorizeHttpRequests(auth -> auth
                // Anyone can reach these without a token(Public endpoint)
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                .requestMatchers("/api/posts/allPosts").permitAll()

                // Any other request requires valid OAuth2 JWT authentication(protected endpoint)
                .anyRequest().authenticated()
        )

                // to tell the app to act as an OAuth2 JWT Resource Server to process incoming Bearer tokens
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }

    // Spring Security uses this to read incoming Bearer tokens using the Public Key
    @Bean //it is native oAuth2 decoder
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }

    // it wraps the public and private keys using the Nimbus JOSE library so we can mint tokens during login
    @Bean // it is native oAuth2 encoder
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey())
                .privateKey(rsaKeys.getPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        // This clean approach bypasses DaoAuthenticationProvider entirely!
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            var userDetails = userDetailsService.loadUserByUsername(username);

            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userDetails, password, userDetails.getAuthorities()
                );
            }

            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
        };
    }

}
