package com.home.dto.admin;

import lombok.Data;

@Data
public class AdminCategoryResponse {
    private Integer categoryId;
    private String name;
    private String description;
    private String message;
}
