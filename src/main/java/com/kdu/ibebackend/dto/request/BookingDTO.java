package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * Validated DTO for getting details for creating a booking
 */
@Data
@JsonIgnoreProperties
public class BookingDTO {
    @Valid
    @JsonProperty("booking_info")
    private BookingInfoDTO bookingInfoDTO;

    @Valid
    @JsonProperty("traveller_info")
    private TravellerDTO travellerDTO;

    @Valid
    @JsonProperty("billing_info")
    private BillingDTO billingDTO;

    @Valid
    @JsonProperty("transaction_info")
    private TransactionDTO transactionDTO;
}