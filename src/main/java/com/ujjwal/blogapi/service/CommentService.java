package com.ujjwal.blogapi.service;

import com.ujjwal.blogapi.model.Comment;
import com.ujjwal.blogapi.model.Post;
import com.ujjwal.blogapi.repository.CommentRepository;
import com.ujjwal.blogapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    public Comment addComment(String content,String commenterName,long postId){
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found"));
        Comment comment = new Comment();
        comment.setPost(post); // setting the post object here to establish the Foreign Key relationship in our database
        comment.setContent(content);
        comment.setCommenterName(commenterName);
        return commentRepo.save(comment);
    }

    // returns all comments of a particular post
    public List<Comment> getCommentsByPostId(long postId){
        return commentRepo.findByPostPostId(postId);
    }

    public Comment updateComment(long commentId,String content){
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found"));
        comment.setContent(content);
        return commentRepo.save(comment);
    }

    public void  deleteComment(long commentId){
        if(!commentRepo.existsById(commentId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found");
        }
        commentRepo.deleteById(commentId);
    }

}
