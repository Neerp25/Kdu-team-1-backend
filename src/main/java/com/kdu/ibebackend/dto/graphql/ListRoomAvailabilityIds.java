package com.kdu.ibebackend.dto.graphql;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ListRoomAvailabilityIds {
    @JsonProperty("data")
    private Res res;

    @Data
    public static class Res {
        @JsonProperty("listRoomAvailabilities")
        private List<Availability> availabilities;
    }

    @Data
    public static class Availability {
        @JsonProperty("availability_id")
        private Integer roomAvailabilityId;

        @JsonProperty("room_id")
        private Integer roomId;

        @JsonProperty("date")
        private String date;
    }
}
