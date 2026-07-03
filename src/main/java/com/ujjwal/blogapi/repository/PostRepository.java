package com.ujjwal.blogapi.repository;

import com.ujjwal.blogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    //it uses SEO friendly url to find specific post
    Optional<Post> findBySlug(String slug);

    //it finds post by specific author user id
    List<Post> findByAuthorUserId(long userId);

}
