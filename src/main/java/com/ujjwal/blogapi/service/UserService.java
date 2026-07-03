package com.ujjwal.blogapi.service;

import com.ujjwal.blogapi.model.User;
import com.ujjwal.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
//{
//    "username": "code_explorer",
//    "password": "secure_pass_456"
//}

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        if(userRepo.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already Exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        // Hashing the plain-text password before it hits PostgreSQL
        user.setPassword(passwordEncoder.encode(password));

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