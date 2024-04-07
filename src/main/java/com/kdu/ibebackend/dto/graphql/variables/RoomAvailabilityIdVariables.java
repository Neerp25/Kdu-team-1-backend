package com.kdu.ibebackend.dto.graphql.variables;

import lombok.Data;

@Data
public class RoomAvailabilityIdVariables {
    private String startDate;
    private String endDate;
    private Integer roomTypeId;
}
