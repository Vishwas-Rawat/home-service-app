package com.home.service.booking;

import com.home.dto.catalog.CustomerBookingListResponse;
import com.home.dto.customer.CustomerBookingRequest;
import com.home.dto.customer.CustomerBookingResponse;
import com.home.model.booking.Booking;
import com.home.model.catalog.Category;
import com.home.model.customer.CustomerProfile;
import com.home.repository.booking.CustomerBookingRepository;
import com.home.repository.catalog.CategoryRepository;
import com.home.repository.customer.CustomerProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerBookingService {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerBookingRepository customerBookingRepository;

    public CustomerBookingResponse bookService(CustomerBookingRequest customerBookingRequest, String email){

        CustomerProfile customerProfile = customerProfileRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("Customer profile not found for " + email));

        Category category = categoryRepository.findById(customerBookingRequest.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found with id: "+ customerBookingRequest.getCategoryId()));

        Booking booking = new Booking();
        booking.setCustomerProfile(customerProfile);
        booking.setCategory(category);
        booking.setOriginalDescription(customerBookingRequest.getDescription());
        booking.setStatus("REQUESTED");
        Booking savedBooking = customerBookingRepository.save(booking);

        CustomerBookingResponse customerBookingResponse = new CustomerBookingResponse();
        customerBookingResponse.setBookingId(savedBooking.getBookingId());
        customerBookingResponse.setCategoryName(savedBooking.getCategory().getName());
        customerBookingResponse.setDescription(savedBooking.getOriginalDescription());
        customerBookingResponse.setStatus(savedBooking.getStatus());
        customerBookingResponse.setMessage("Booking created successfully!");
        return customerBookingResponse;
    }

    @Transactional
    public List<CustomerBookingListResponse> allMyBookings(String email){
        List<Booking> bookings = customerBookingRepository.findByCustomerProfileUserEmail(email);
        return  bookings.stream().map(booking -> {
            CustomerBookingListResponse customerBookingResponse = new CustomerBookingListResponse();
            customerBookingResponse.setBookingId(booking.getBookingId());
            customerBookingResponse.setCategoryName(booking.getCategory().getName());
            customerBookingResponse.setStatus(booking.getStatus());
            customerBookingResponse.setDescription(booking.getOriginalDescription());
            customerBookingResponse.setCreatedAt(booking.getCreatedAt());
            customerBookingResponse.setMessage("Fetched booking successfully");
            return customerBookingResponse;
        }).collect(Collectors.toList());
    }
}
