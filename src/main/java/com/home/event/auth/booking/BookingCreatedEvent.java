package com.home.event.auth.booking;

import com.home.model.booking.Booking;
import lombok.Data;
 @Data
    public class BookingCreatedEvent {
        private final Booking booking;
        public BookingCreatedEvent(Booking booking){
            this.booking = booking;
        }
    }

