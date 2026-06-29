package com.home.service.customer;

import com.home.event.auth.UserRegisteredEvent;
import com.home.model.customer.CustomerProfile;
import com.home.repository.customer.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerProfileEventListener  {
    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @EventListener
    public void customerEventListener(UserRegisteredEvent event){
        if("CUSTOMER".equalsIgnoreCase(event.getUser().getRole().getName())){
            CustomerProfile customerProfile = new CustomerProfile();
            customerProfile.setUser(event.getUser());
            customerProfile.setFirstName(event.getUser().getName());
            customerProfile.setLastName("");
            customerProfile.setPhoneNumber("");
            customerProfileRepository.save(customerProfile);
        }
    }
}