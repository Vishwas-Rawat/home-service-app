package com.home.service.customer;

import com.home.dto.customer.CustomerResponse;
import com.home.model.customer.CustomerProfile;
import com.home.repository.customer.CustomerProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Transactional
    public CustomerResponse getCustomerProfile(String email){
        CustomerProfile profile = customerProfileRepository.findByUserEmail(email).orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerResponse response = new CustomerResponse();
        response.setCustomerId(profile.getCustomerId());
        response.setFirstName(profile.getFirstName());
        response.setLastName(profile.getLastName());
        response.setPhoneNumber(profile.getPhoneNumber());
        response.setEmail(profile.getUser().getEmail());

        return response;
    }
}
