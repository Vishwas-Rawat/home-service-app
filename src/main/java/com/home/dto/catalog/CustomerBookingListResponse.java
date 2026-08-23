package com.home.dto.catalog;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CustomerBookingListResponse {
    private Integer bookingId;
    private String categoryName;
    private String description;
    private String status;
    private String message;
    private LocalDateTime createdAt;
}
