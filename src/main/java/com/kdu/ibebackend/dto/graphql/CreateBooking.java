package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class CreateBooking {
    @JsonProperty("data")
    private Res res;

    @Data
    public static class Res {
        @JsonProperty("createBooking")
        private Booking booking;
    }

    @Data
    @JsonIgnoreProperties
    public static class Booking {
        @JsonProperty("promotion_id")
        private Integer promotionId;

        @JsonProperty("booking_id")
        private Integer bookingId;
    }
}
