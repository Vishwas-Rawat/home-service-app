package com.home.service.booking;

import com.home.event.auth.booking.BookingCreatedEvent;
import com.home.model.booking.Booking;
import com.home.repository.booking.CustomerBookingRepository;
import com.home.service.ai.GeminiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BookingAiEventListener {

    @Autowired
    private GeminiClientService geminiClientService;

    @Autowired
    private CustomerBookingRepository customerBookingRepository;

    @Async // Runs this method on a separate background thread
    @EventListener // Tells Spring to trigger this method when BookingCreatedEvent is published
    public void handleBookingCreated(BookingCreatedEvent bookingCreatedEvent){
        Booking booking = bookingCreatedEvent.getBooking();

        System.out.println("AI Worker: Received Booking ID " + booking.getBookingId() + " in background thread.");

        // 1. Send description to Gemini to be optimized
        String optimized = geminiClientService.optimizeDescription(booking.getOriginalDescription());

        // 2. Update and save the booking with the improved description
        booking.setImprovedDescription(optimized);
        customerBookingRepository.save(booking);

        System.out.println("AI Worker: Finished optimizing Booking ID " + booking.getBookingId() + "!");
    }
}
