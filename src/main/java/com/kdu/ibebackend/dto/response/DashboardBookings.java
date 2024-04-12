package com.kdu.ibebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties
public class DashboardBookings {
    private String name;
    private String email;
    private Double bookingAmount;
}
