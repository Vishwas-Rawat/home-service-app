package com.home.dto.customer;

import lombok.Data;

@Data
public class CustomerBookingResponse {
    private Integer bookingId;
    private String categoryName;
    private String description;
    private String status;
    private String message;
}
