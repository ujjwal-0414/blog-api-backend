package com.ujjwal.blogapi.controller;

import com.ujjwal.blogapi.dto.PostDTO;
import com.ujjwal.blogapi.model.Post;
import com.ujjwal.blogapi.repository.PostRepository;
import com.ujjwal.blogapi.repository.UserRepository;
import com.ujjwal.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("createPost") //RequestBody is used bcz data can be large text
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDto){ //data is coming from DTO class
        Post newPost = postService.createPost(
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getSummary(),
                postDto.getAuthorId(),
                postDto.getCategoryIds() // Added to forward category IDs from DTO to service
        );
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("slug/{slug}")
    public ResponseEntity<Post> getPostBySlug(@PathVariable String slug){
        Post post = postService.getPostBySlug(slug);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("allPosts")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,@RequestBody PostDTO postDto){
        Post updatedPost = postService.updatePost(
                id,
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getSummary(),
                postDto.getCategoryIds() // Added to forward updated category IDs from DTO to service
        );
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted with id" + id, HttpStatus.OK);
    }

}