package com.home.repository.customer;

import com.home.model.customer.CustomerAddress;
import com.home.model.customer.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
    List<CustomerAddress> findByCustomerProfile(CustomerProfile customerProfile);

}
