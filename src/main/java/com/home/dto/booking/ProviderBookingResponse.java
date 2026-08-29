package com.home.dto.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProviderBookingResponse {
    private Integer bookingId;
    private Integer customerId;
    private String customerName;
    private Integer providerId;
    private String providerName;
    private Integer categoryId;
    private String categoryName;
    private String status;
    private String originalDescription;
    private String notes;
    private LocalDateTime updatedAt;
}
