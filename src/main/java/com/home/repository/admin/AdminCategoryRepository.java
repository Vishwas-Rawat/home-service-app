package com.home.repository.admin;

import com.home.model.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminCategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String category);
}
