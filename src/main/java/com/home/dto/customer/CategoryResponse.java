package com.home.dto.customer;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CategoryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer categoryId;
    private String name;
    private String description;
}
