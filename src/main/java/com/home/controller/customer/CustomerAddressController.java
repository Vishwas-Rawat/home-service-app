package com.home.controller.customer;

import com.home.dto.customer.CustomerAddressRequest;
import com.home.dto.customer.CustomerAddressResponse;
import com.home.service.customer.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("home/customer/")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    @PostMapping("add/address")
    public CustomerAddressResponse addAddress(Principal principal, @RequestBody CustomerAddressRequest customerAddressRequest){
        return customerAddressService.addCustomerAddress(principal.getName(), customerAddressRequest);
    }

    @GetMapping("addresses")
    public List<CustomerAddressResponse> viewAddress(Principal principal){
        return customerAddressService.viewCustomerAddress(principal.getName());
    }
}
