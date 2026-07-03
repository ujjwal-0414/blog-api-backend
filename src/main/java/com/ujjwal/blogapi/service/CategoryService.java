package com.ujjwal.blogapi.service;

import com.ujjwal.blogapi.model.Category;
import com.ujjwal.blogapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    public Category createCategory(String  categoryName){
        // Cleaning the input to prevent matching issues due to trailing spaces
        String cleanedName = categoryName.trim();

        // Checks if a category with this exact name already exists in PostgreSQL
        if (categoryRepo.findByCategoryName(cleanedName).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category '" + cleanedName + "' already exists!"
            );
        }
        Category category = new Category();
        category.setCategoryName(cleanedName);
        return categoryRepo.save(category);
    }

    // to get single category by its id
    public Category getCategoryById(long categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with ID: " + categoryId
                ));
    }

    // to get all categories
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category updateCategory(long categoryId, String newName) {
        // Finding the target category
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with ID: " + categoryId
                ));

        String cleanedName = newName.trim();

        // Checks if the new name is already taken by another category
        if (categoryRepo.findByCategoryName(cleanedName).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category name '" + cleanedName + "' is already taken!"
            );
        }

        // updating the category
        category.setCategoryName(cleanedName);
        return categoryRepo.save(category);
    }

    public void deleteCategory(long categoryId) {
        // Checks if the category exists first
        if (!categoryRepo.existsById(categoryId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category not found with ID: " + categoryId
            );
        }

        // Safely deleting the category. Hibernate automatically cleans up the corresponding rows in the 'post_categories' join table first!
        categoryRepo.deleteById(categoryId);
    }

}
