package com.home.controller.booking;

import com.home.dto.booking.AvailableBookingsResponse;
import com.home.dto.booking.ProviderBookingRequest;
import com.home.dto.booking.ProviderBookingResponse;
import com.home.service.provider.ProviderBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/provider/bookings/")
public class ProviderBookingController {

    @Autowired
    private ProviderBookingService providerBookingService;

    @PutMapping("{bookingId}/status")
    public ProviderBookingResponse updateBooking(
            Principal principal,
            @PathVariable Integer bookingId,
            @RequestBody ProviderBookingRequest providerBookingRequest){
        return providerBookingService.updateResponse(principal.getName(), bookingId, providerBookingRequest);
    }

    @GetMapping
    public List<AvailableBookingsResponse> allBookings(Principal principal){
        return providerBookingService.getAllAvailableBookings(principal.getName());
    }
}
