package com.ujjwal.blogapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId; //used long type bcz it handles overflow situations as it takes 8 bytes.

    @NotBlank(message = "Username cannot be blank")
    @Size(min=4, max=50)
    @Column(unique=true) // it should be unique
    private String username;

    @Size(min=8, message = "Password must be of at least 8 characters")
    private String password;

    //used jakarta validation for email validation
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email")
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdOn;//using java.time to get time and date

    @PrePersist //automatically creates timestamp before saving to database
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assigning a basic role to all registered users
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // If we want users to log in using their email,then we can return this.email;
        // If we want them to log in using their username handle,then we can return this.username;
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
