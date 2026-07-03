package com.ujjwal.blogapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "Title cannot be blanked")
    @Column(nullable = false)
    private String title;

   // @Lob // as it will store long text
    @Column(columnDefinition = "TEXT")
    private String content;

    private String slug;
    @Size(max=250,message = "Summary cannot exceed 250 characters")
    private String summary;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    // why not fetch eager 10 individual separate queries to fetch the user details for each post author.That is 11 database queries just to show 10 posts
    //Lazy ->Just fetch the post details right now.Do not look up the Author's details in the database unless I explicitly call post.getAuthor() in my Java code
    @ManyToOne(fetch = FetchType.LAZY) //as one user can have many posts
    @JoinColumn(name = "user_id",nullable =false) //A Join Table creates a completely separate, third mapping table in your database just to hold the links between users and posts
    private User author;                          //where as It simply creates a Foreign Key column directly inside your existing posts table.

    @ManyToMany // ManyToMany uses FetchType.LAZY by default
    @JoinTable( // @JoinTable is used because a Many-to-Many relationship requires a completely separate, third linking table to connect rows, whereas @JoinColumn only creates a direct foreign key column inside an existing table.
            name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories; // used to match categories to posts

    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){ //protected bcz Keeps methods hidden from external layers, but open for Hibernate proxies and entity inheritance.
        this.updatedOn = LocalDateTime.now();
    }

}
