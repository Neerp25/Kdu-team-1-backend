package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdu.ibebackend.models.PromotionType;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BookingStatus {
    @JsonProperty("data")
    private Res res;

    @Data
    @JsonIgnoreProperties
    public static class Res {
        @JsonProperty("getBooking")
        private GetBooking getBooking;
    }

    @Data
    @JsonIgnoreProperties
    public static class GetBooking {
        @JsonProperty("adult_count")
        private Integer adultCount;

        @JsonProperty("amount_due_at_resort")
        private Integer amountDueAtResort;

        @JsonProperty("check_in_date")
        private String checkInDate;

        @JsonProperty("check_out_date")
        private String checkOutDate;

        @JsonProperty("child_count")
        private Integer childCount;

        @JsonProperty("status_id")
        private Integer statusId;

        @JsonProperty("total_cost")
        private Integer totalCost;

        @JsonProperty("promotion_applied")
        private PromotionType promotionType;
    }
}