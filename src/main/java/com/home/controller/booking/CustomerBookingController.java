package com.home.controller.booking;

import com.home.dto.catalog.CustomerBookingListResponse;
import com.home.dto.customer.CustomerBookingRequest;
import com.home.dto.customer.CustomerBookingResponse;
import com.home.service.booking.CustomerBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home/customer")
public class CustomerBookingController {

    @Autowired
    private CustomerBookingService customerBookingService;

    @PostMapping("/service/book")
    public CustomerBookingResponse bookService(@RequestBody CustomerBookingRequest customerBookingRequest, Principal principal){
        return customerBookingService.bookService(customerBookingRequest, principal.getName());
    }

    @GetMapping("/service/bookings")
    public List<CustomerBookingListResponse> getMyBookings(Principal principal) {
        return customerBookingService.allMyBookings(principal.getName());
    }

}
