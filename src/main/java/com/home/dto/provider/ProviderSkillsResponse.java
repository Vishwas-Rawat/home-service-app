package com.home.dto.provider;

import lombok.Data;

@Data
public class ProviderSkillsResponse {
    private Integer id;
    private Integer providerId;
    private Integer categoryId;
    private String categoryName;
}
