package com.ujjwal.blogapi.controller;

import com.ujjwal.blogapi.dto.UserDTO;
import com.ujjwal.blogapi.model.User;
import com.ujjwal.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDto){
        User newUser = userService.registerUser(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword()
        );
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
