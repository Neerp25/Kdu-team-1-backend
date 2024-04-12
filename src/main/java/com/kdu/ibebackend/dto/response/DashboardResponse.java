package com.kdu.ibebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class DashboardResponse {
    private Long totalBookingCount;
    private Double totalRevenue;
    private List<Double> graph;
    private Double bookingIncrease;
    private Double revenueIncrease;
    private Long currentMonthBookingCount;
}
