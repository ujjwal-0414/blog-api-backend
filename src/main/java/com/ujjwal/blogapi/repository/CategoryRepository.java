package com.ujjwal.blogapi.repository;

import com.ujjwal.blogapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    // to find category by its name
    Optional<Category> findByCategoryName(String categoryName);

}
