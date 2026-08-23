package com.home.repository.booking;

import com.home.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerBookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerProfileUserEmail(String email);
}
