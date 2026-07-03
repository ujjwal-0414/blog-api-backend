package com.ujjwal.blogapi.controller;

import com.ujjwal.blogapi.dto.CommentDTO;
import com.ujjwal.blogapi.model.Comment;
import com.ujjwal.blogapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("addComment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDto){
        Comment newComment = commentService.addComment(
                commentDto.getContent(),
                commentDto.getCommenterName(),
                commentDto.getPostId()
        );
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable long postId){
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new  ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("update/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable long commentId, @RequestBody CommentDTO commentDto){
        Comment newComment = commentService.updateComment(
                commentId,
                commentDto.getContent()
        );
        return new ResponseEntity<>(newComment, HttpStatus.OK);
    }

    @DeleteMapping("delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment deleted with id" + commentId, HttpStatus.OK);
    }

}
