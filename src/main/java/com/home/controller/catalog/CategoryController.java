package com.home.controller.catalog;

import com.home.dto.customer.CategoryResponse;
import com.home.dto.customer.CategoryResponse;
import com.home.model.catalog.Category;
import com.home.service.catalog.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/catalog")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
