package com.ujjwal.blogapi.controller;

import com.ujjwal.blogapi.dto.CategoryDTO;
import com.ujjwal.blogapi.model.Category;
import com.ujjwal.blogapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("createCategory")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDto) {
        Category newCategory  = categoryService.createCategory(
                categoryDto.getCategoryName()
        );
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping("allCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("find/{categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable long categoryId, @RequestBody CategoryDTO categoryDto) {
        Category updatedCategory = categoryService.updateCategory(categoryId, categoryDto.getCategoryName());
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }

}
