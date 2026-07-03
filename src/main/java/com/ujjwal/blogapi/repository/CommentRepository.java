package com.ujjwal.blogapi.repository;

import com.ujjwal.blogapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    // Looks inside the 'post' entity field and filters by its 'postId' field
    List<Comment> findByPostPostId(long postId);

}
