package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateGuest {
    @JsonProperty("data")
    private Res res;

    @Data
    public static class Res {
        @JsonProperty("createGuest")
        private Guest guest;
    }

    @Data
    public static class Guest {
        @JsonProperty("guest_id")
        private String guestId;

        @JsonProperty("guest_name")
        private String guestName;
    }
}
