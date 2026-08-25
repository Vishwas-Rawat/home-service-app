package com.home.dto.provider;

import lombok.Data;

@Data
public class ProviderProfileResponse {
    private Integer providerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String shopName;
    private String shopDetails;
    private Boolean isAvailable;
    private String email;
}
