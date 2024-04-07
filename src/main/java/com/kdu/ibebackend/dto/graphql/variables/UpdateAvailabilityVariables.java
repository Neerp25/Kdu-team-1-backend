package com.kdu.ibebackend.dto.graphql.variables;

import lombok.Data;

@Data
public class UpdateAvailabilityVariables {
    private Integer bookingId;
    private Integer availabilityId;
}
