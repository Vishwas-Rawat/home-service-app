package com.home.dto.customer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerAddressResponse {
    private Integer id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private Boolean isDefault;
    private LocalDateTime createdAt;
}
