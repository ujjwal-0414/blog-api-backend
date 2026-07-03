package com.ujjwal.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "category name cannot be blank")
    private String categoryName;

    @ManyToMany(mappedBy = "categories") // helps in mapping it with categories
    // Breaking the bidirectional circular JSON reference loop between Post and Category
    @JsonIgnoreProperties("categories")
    private List<Post> posts;

}
