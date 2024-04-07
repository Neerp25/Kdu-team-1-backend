package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BookingInfoDTO {
    private String checkInDate;
    private String checkOutDate;
    private Integer adultCount;
    private Integer childCount;
    private Double totalCost;
    private Double amountDueResort;
    private String guestName;
    private Integer roomTypeId;
    private Integer rooms;
    private Integer promotionId;
    private Integer promoCodeId;
}
