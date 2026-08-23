package com.home.dto.auth;

import lombok.Data;

@Data
public class RegisterResponse {
    private String name;
    private String email;
    private String message;
}
