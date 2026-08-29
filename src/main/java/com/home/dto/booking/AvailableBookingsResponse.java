package com.home.dto.booking;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AvailableBookingsResponse {
    private Integer bookingId;
    private Integer customerId;
    private Integer categoryId;
    private String categoryName;
    private String originalDescription;
    private String status;
    private LocalDateTime createdAt;
}
