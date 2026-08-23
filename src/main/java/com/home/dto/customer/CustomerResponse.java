package com.home.dto.customer;

import lombok.Data;

@Data
public class CustomerResponse {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
