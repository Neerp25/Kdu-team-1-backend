package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Validated DTO for getting details for creating a booking
 */
@Data
@JsonIgnoreProperties
public class BookingDTO {
    @JsonProperty("booking_info")
    private BookingInfoDTO bookingInfoDTO;

    @JsonProperty("traveller_info")
    private TravellerDTO travellerDTO;

    @JsonProperty("billing_info")
    private BillingDTO billingDTO;

    @JsonProperty("transaction_info")
    private TransactionDTO transactionDTO;
}