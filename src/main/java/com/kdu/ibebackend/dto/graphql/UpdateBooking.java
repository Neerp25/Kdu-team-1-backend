package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class UpdateBooking {
    @JsonProperty("data")
    private Res res;

    @Data
    public static class Res {
        @JsonProperty("updateBooking")
        private Booking booking;
    }

    @Data
    public static class Booking {
        @JsonProperty("booking_id")
        private String bookingId;
    }
}
