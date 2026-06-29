package com.home.dto.auth;

import com.home.model.auth.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String role;
}
