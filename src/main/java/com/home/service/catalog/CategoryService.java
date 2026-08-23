package com.home.service.catalog;

import com.home.dto.customer.CategoryResponse;
import com.home.dto.customer.CategoryResponse;
import com.home.model.catalog.Category;
import com.home.repository.catalog.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category->{
            CategoryResponse customerCategoryResponse = new CategoryResponse();
            customerCategoryResponse.setCategoryId(category.getCategoryId());
            customerCategoryResponse.setName(category.getName());
            customerCategoryResponse.setDescription(category.getDescription());
            return customerCategoryResponse;
        }).collect(Collectors.toList());
    }
}
