package com.kdu.ibebackend.dto.graphql.variables;

import lombok.Data;

@Data
public class RoomAvailabilityVariables {
    private Integer bookingId;
    private String startDate;
    private String endDate;
}
