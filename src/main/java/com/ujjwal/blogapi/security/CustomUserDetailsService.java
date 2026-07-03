package com.ujjwal.blogapi.security;

import com.ujjwal.blogapi.model.User;
import com.ujjwal.blogapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // it fetches a user account from your PostgreSQL database and converts it into a standard format that Spring Security can understand to verify credentials
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // not autowired bcz using constructor injection

    // Inject your existing database repository
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch the user entity from your existing PostgreSQL table
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Translate your model into a standard Spring Security UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),     // This must be saved as a BCrypt hash in your DB!
                Collections.emptyList() // No roles or authorities required for this basic setup
        );
    }

}
