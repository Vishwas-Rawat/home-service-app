package com.home.controller.auth;

import com.home.dto.auth.UserRequest;
import com.home.dto.auth.UserResponse;
import com.home.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/auth/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest userRequest){
        return userService.register(userRequest);
    }
}
