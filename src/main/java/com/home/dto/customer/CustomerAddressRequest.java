package com.home.dto.customer;

import lombok.Data;

@Data
public class CustomerAddressRequest {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private Boolean isDefault;
}
