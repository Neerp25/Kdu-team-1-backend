package com.kdu.ibebackend.dto.graphql.variables;

import lombok.Data;

@Data
public class CreateBookingVariables {
    private Integer adultCount;
    private Integer amountDueResort;
    private String checkInDate;
    private String checkOutDate;
    private Integer childCount;
    private Integer guestId;
    private Integer promotionId;
    private Integer totalCost;
}
