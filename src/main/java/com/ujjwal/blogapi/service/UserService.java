package com.ujjwal.blogapi.service;

import com.ujjwal.blogapi.dto.UserDTO;
import com.ujjwal.blogapi.model.User;
import com.ujjwal.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDto) {
        if(userRepo.existsByEmail(userDto.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already Exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        // Securely hashing the plain text password using BCrypt
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepo.save(user);
    }

    public User getUserById(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

}