package com.home.dto.auth;

import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String email;
    private String message;
}
