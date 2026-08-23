package com.home.controller.customer;

import com.home.dto.customer.CustomerResponse;
import com.home.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/home/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/profile")
    public CustomerResponse profile(Principal principal){
        // principal.getName() returns the email set by JwtAuthenticationFilter
        return customerService.getCustomerProfile(principal.getName());
    }
}
