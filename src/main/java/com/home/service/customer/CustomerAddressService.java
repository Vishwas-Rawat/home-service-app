package com.home.service.customer;

import com.home.dto.customer.CustomerAddressRequest;
import com.home.dto.customer.CustomerAddressResponse;
import com.home.model.customer.CustomerAddress;
import com.home.model.customer.CustomerProfile;
import com.home.repository.customer.CustomerAddressRepository;
import com.home.repository.customer.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerAddressService {
    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    public CustomerAddressResponse addCustomerAddress(String email, CustomerAddressRequest customerAddressRequest){
       CustomerProfile customerProfile = customerProfileRepository.findByUserEmail(email)
                .orElseThrow(()->new RuntimeException("No customer found for email "+email));

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerProfile(customerProfile);
        customerAddress.setCreatedAt(LocalDateTime.now());
        customerAddress.setAddressLine1(customerAddressRequest.getAddressLine1());
        customerAddress.setAddressLine2(customerAddressRequest.getAddressLine2());
        customerAddress.setCity(customerAddressRequest.getCity());
        customerAddress.setState(customerAddressRequest.getState());
        customerAddress.setPostalCode(customerAddressRequest.getPostalCode());
        customerAddress.setIsDefault(customerAddressRequest.getIsDefault() != null ? customerAddressRequest.getIsDefault(): false);

        CustomerAddress savedAddress = customerAddressRepository.save(customerAddress);

        CustomerAddressResponse response = new CustomerAddressResponse();
        response.setId(savedAddress.getId());
        response.setAddressLine1(savedAddress.getAddressLine1());
        response.setAddressLine2(savedAddress.getAddressLine2());
        response.setCity(savedAddress.getCity());
        response.setPostalCode(savedAddress.getPostalCode());
        response.setState(savedAddress.getState());
        response.setIsDefault(savedAddress.getIsDefault());
        response.setCreatedAt(savedAddress.getCreatedAt());

        return response;

    }

    public List<CustomerAddressResponse> viewCustomerAddress(String email) {
        CustomerProfile customerProfile = customerProfileRepository.findByUserEmail(email)
                .orElseThrow(()->new RuntimeException("No customer found for email "+email));

        List<CustomerAddress> customerAddress = customerAddressRepository.findByCustomerProfile(customerProfile);

        return customerAddress.stream().map(address -> {
            CustomerAddressResponse customerAddressResponse = new CustomerAddressResponse();
            customerAddressResponse.setId(address.getId());
            customerAddressResponse.setAddressLine1(address.getAddressLine1());
            customerAddressResponse.setAddressLine2(address.getAddressLine2());
            customerAddressResponse.setCity(address.getCity());
            customerAddressResponse.setState(address.getState());
            customerAddressResponse.setPostalCode(address.getPostalCode());
            customerAddressResponse.setIsDefault(address.getIsDefault());
            customerAddressResponse.setCreatedAt(LocalDateTime.now());
            return customerAddressResponse;
        }).toList();
    }
}
