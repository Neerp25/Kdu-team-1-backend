package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class UpdateRoomAvailability {
    @JsonProperty("data")
    private Res res;

    @Data
    @JsonIgnoreProperties
    public static class Res {
        @JsonProperty("updateRoomAvailability")
        private Update update;
    }

    @Data
    public static class Update {
        @JsonProperty("availability_id")
        private String availabilityId;
    }
}
