package com.home.service.admin;

import com.home.dto.admin.AdminCategoryRequest;
import com.home.dto.admin.AdminCategoryResponse;
import com.home.model.catalog.Category;
import com.home.repository.admin.AdminCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminCategoryService {
    @Autowired
    private AdminCategoryRepository adminCategoryRepository;

    public AdminCategoryResponse createAdminCategory(AdminCategoryRequest adminCategoryRequest){
        if (adminCategoryRepository.existsByName(adminCategoryRequest.getName())) {
            throw new RuntimeException("Category already exist "+ adminCategoryRequest.getName());
        }

        Category category = new Category();
        category.setName(adminCategoryRequest.getName());
        category.setDescription(adminCategoryRequest.getDescription());
        Category saved = adminCategoryRepository.save(category);

        AdminCategoryResponse adminCategoryResponse = new AdminCategoryResponse();
        adminCategoryResponse.setCategoryId(saved.getCategoryId());
        adminCategoryResponse.setName(saved.getName());
        adminCategoryResponse.setDescription(saved.getDescription());
        adminCategoryResponse.setMessage("Category saved successfully");

        return adminCategoryResponse;
    }
}
