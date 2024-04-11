package com.kdu.ibebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class PersonalBooking {
    @JsonProperty("adult_count")
    private Integer adultCount;

    @JsonProperty("check_in_date")
    private String checkInDate;

    @JsonProperty("check_out_date")
    private String checkOutDate;

    @JsonProperty("child_count")
    private Integer childCount;

    private Boolean status;

    @JsonProperty("total_cost")
    private Integer totalCost;
}
