package com.home.dto.customer;

import lombok.Data;

@Data
public class CustomerBookingRequest {
    private Integer categoryId;
    private String description;
}
