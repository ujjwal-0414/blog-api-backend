package com.ujjwal.blogapi.repository;

import com.ujjwal.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //Spring will automatically generate the SQL for these methods
    //we created these bcz by default JPA finds data by their id only so by this we can search by different fields
    Optional<User> findByUsername(String username);

    //Optional is used bcz the data may or may not be present

    Optional<User> findByEmail(String email);

    //checks the email exist in database or not
    boolean existsByEmail(String email);
}
