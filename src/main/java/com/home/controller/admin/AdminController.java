package com.home.controller.admin;

import com.home.dto.admin.AdminCategoryRequest;
import com.home.dto.admin.AdminCategoryResponse;
import com.home.service.admin.AdminCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/home/admin")
public class AdminController {

    @Autowired
    private AdminCategoryService adminCategoryService;

    @PostMapping("/categories")
    public AdminCategoryResponse addAdminCategory(@RequestBody AdminCategoryRequest adminCategoryRequest){
        return adminCategoryService.createAdminCategory(adminCategoryRequest);
    }
}
