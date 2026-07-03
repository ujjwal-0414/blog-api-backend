package com.ujjwal.blogapi.dto;

import lombok.Data;

@Data
public class UserDTO {

    //data container class to safely catch JSON request payload from the client
    private String username;
    private String email;
    private String password;
    private String roles;

}
