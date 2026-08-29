package com.home.service.provider;

import com.home.dto.booking.AvailableBookingsResponse;
import com.home.dto.booking.ProviderBookingRequest;
import com.home.dto.booking.ProviderBookingResponse;
import com.home.model.booking.Booking;
import com.home.model.booking.BookingStatusHistory;
import com.home.model.catalog.Category;
import com.home.model.provider.ProviderProfile;
import com.home.model.provider.ProviderSkill;
import com.home.repository.booking.ProviderBookingRepository;
import com.home.repository.provider.ProviderProfileRepository;
import com.home.repository.booking.BookingStatusHistoryRepository;
import com.home.repository.provider.ProviderSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderBookingService {

    @Autowired
    private ProviderBookingRepository providerBookingRepository;

    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired
    private BookingStatusHistoryRepository bookingStatusHistoryRepository;

    @Autowired
    private ProviderSkillRepository providerSkillRepository;

    @Transactional
    public ProviderBookingResponse updateResponse(String email, Integer bookingId, ProviderBookingRequest providerBookingRequest) {
        // 1. Fetch provider profile
        ProviderProfile providerProfile = providerProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("No provider found for email " + email));

        // 2. Fetch the booking
        Booking booking = providerBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID " + bookingId));

        // 3. Update booking status, assign provider, and update timestamp
        booking.setStatus(providerBookingRequest.getStatus());
        booking.setProviderProfile(providerProfile);
        booking.setUpdatedAt(LocalDateTime.now());
        Booking updateBooking = providerBookingRepository.save(booking);

        // 4. Save history record using BookingStatusHistoryRepository
        BookingStatusHistory bookingStatusHistory = new BookingStatusHistory();
        bookingStatusHistory.setBooking(updateBooking);
        bookingStatusHistory.setStatus(updateBooking.getStatus());
        bookingStatusHistory.setChangedBy(providerProfile.getUser());
        bookingStatusHistory.setNotes(providerBookingRequest.getNotes());
        bookingStatusHistoryRepository.save(bookingStatusHistory);

        // 5. Map fields to ProviderBookingResponse and return
        ProviderBookingResponse response = new ProviderBookingResponse();
        response.setBookingId(updateBooking.getBookingId());
        response.setCustomerId(updateBooking.getCustomerProfile().getCustomerId());
        response.setCustomerName(updateBooking.getCustomerProfile().getFirstName() + " " + updateBooking.getCustomerProfile().getLastName());
        response.setProviderId(providerProfile.getProviderId());
        response.setProviderName(providerProfile.getFirstName() + " " + providerProfile.getLastName());
        response.setCategoryId(updateBooking.getCategory().getCategoryId());
        response.setCategoryName(updateBooking.getCategory().getName());
        response.setOriginalDescription(updateBooking.getOriginalDescription());
        response.setStatus(updateBooking.getStatus());
        response.setNotes(providerBookingRequest.getNotes());
        response.setUpdatedAt(updateBooking.getUpdatedAt());

        return response;
    }

    @Transactional
    public List<AvailableBookingsResponse> getAllAvailableBookings(String email){
        ProviderProfile providerProfile = providerProfileRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("No user found for email "+email));

        // Fetch provider's registered skills
        List<ProviderSkill> skills = providerSkillRepository.findByProviderProfile_ProviderId(providerProfile.getProviderId());

        // If the provider has registered no skills, return an empty list immediately
        if(skills.isEmpty()){
            return new ArrayList<>();
        }

        // Fetch skills/category which are not approved
        List<Integer> categoryIds = skills.stream().map(skill -> skill.getCategory().getCategoryId()).toList();

        // Fetch available bookings matching those category IDs
        List<Booking> bookings = providerBookingRepository.findAvailableBookings(categoryIds);

        // Map matching bookings to DTOs
        return bookings.stream().map(booking -> {
            AvailableBookingsResponse response = new AvailableBookingsResponse();
            response.setBookingId(booking.getBookingId());
            response.setCustomerId(booking.getCustomerProfile().getCustomerId());
            response.setCategoryId(booking.getCategory().getCategoryId());
            response.setCategoryName(booking.getCategory().getName());
            response.setOriginalDescription(booking.getOriginalDescription());
            response.setStatus(booking.getStatus());
            response.setCreatedAt(booking.getCreatedAt());
            return response;
        }).collect(Collectors.toList());
    }
}