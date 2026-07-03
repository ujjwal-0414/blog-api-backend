package com.ujjwal.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long commentId; // id of a particular comment

    @Size(max=150)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    // Preventing Jackson JSON infinite recursion loops and ignores Hibernate lazy-loading proxy fields
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "categories"})
    private Post post; // parent blog post this comment belongs to

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable=false)
    private String commenterName;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

}
