package com.home.controller.auth;

import com.home.dto.auth.UserRequest;
import com.home.dto.auth.UserResponse;
import com.home.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/auth/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@PathVariable UserRequest userRequest){
        return userService.register(userRequest);
    }
}
