package com.ujjwal.blogapi.service;

import com.ujjwal.blogapi.model.Category;
import com.ujjwal.blogapi.model.Post;
import com.ujjwal.blogapi.model.User;
import com.ujjwal.blogapi.repository.CategoryRepository;
import com.ujjwal.blogapi.repository.PostRepository;
import com.ujjwal.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    // Updated to accept categoryIds list alongside existing parameters
    public Post createPost(String title, String content, String summary, Long authorId, List<Long> categoryIds){
        //finds author if not then return 404 error
        User author = userRepo.findById(authorId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setSummary(summary);
        post.setAuthor(author);

        // Map and attach the category entities if provided
        List<Category> attachedCategories = new ArrayList<>();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long catId : categoryIds) {
                Category category = categoryRepo.findById(catId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found with ID: " + catId
                        ));
                attachedCategories.add(category);
            }
        }
        post.setCategories(attachedCategories);

        post.setSlug(generateSlug(title)); // generates and set SEO-friendly slug
        return postRepo.save(post);
    }

    // Method to generate slug
    // this private bcz it is an internal administrative step to save post.Post controller does not need its implementation
    private String generateSlug(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return title.trim().toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    public Post getPostBySlug(String slug){
        return postRepo.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found with slug " + slug));
    }

    public List<Post> getAllPosts(){
        return postRepo.findAll();
    }

    // Updated to handle updating category links during editing
    public Post updatePost(Long id, String title, String content, String summary, List<Long> categoryIds){
        Post existingPost = postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found"));
        //checks for the title change
        if (title != null && !title.isBlank() && !title.equals(existingPost.getTitle())) {
            existingPost.setTitle(title);
            existingPost.setSlug(generateSlug(title)); //generate new slug for new title
        }
        existingPost.setContent(content);
        existingPost.setSummary(summary);

        // If category mapping arrays are sent, overwrite the old links in the join table
        if (categoryIds != null) {
            List<Category> updatedCategories = new ArrayList<>();
            for (Long catId : categoryIds) {
                Category category = categoryRepo.findById(catId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found with ID: " + catId
                        ));
                updatedCategories.add(category);
            }
            existingPost.setCategories(updatedCategories);
        }

        return postRepo.save(existingPost);
    }

    public void deletePost(long id){
        //Optional<Post> post = postRepo.findByUrlSlug(slug); not used bcz it is used when a method might return a piece of data that is missing (null)
        if(!postRepo.existsById(id)){ //existsById does not return data,it just answers a Yes/No question
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found with id " + id);
        }
        postRepo.deleteById(id);
    }

}