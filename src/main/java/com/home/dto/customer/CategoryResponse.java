package com.home.dto.customer;

import lombok.Data;

@Data
public class CategoryResponse {
    private Integer categoryId;
    private String name;
    private String description;
}
