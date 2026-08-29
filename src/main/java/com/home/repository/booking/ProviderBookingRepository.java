package com.home.repository.booking;

import com.home.dto.booking.AvailableBookingsResponse;
import com.home.dto.booking.ProviderBookingResponse;
import com.home.model.booking.Booking;
import com.home.model.booking.BookingStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderBookingRepository extends JpaRepository<Booking, Integer> {

    // Query to find available (REQUESTED & unassigned) bookings matching provider category IDs

    @Query("Select b from Booking b where b.status='REQUESTED' AND b.providerProfile IS NULL AND b.category.categoryId IN :categoryIds ")
    List<Booking> findAvailableBookings(@Param("categoryIds") List<Integer> categoryIds);
}
