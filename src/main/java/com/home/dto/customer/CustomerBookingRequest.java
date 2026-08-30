package com.home.dto.customer;

import lombok.Data;

@Data
public class CustomerBookingRequest {
    private Integer categoryId;
    private Integer addressId;
    private String description;
}
