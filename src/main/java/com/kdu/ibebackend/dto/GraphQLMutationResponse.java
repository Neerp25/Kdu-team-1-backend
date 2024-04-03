package com.kdu.ibebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Dummy DTO to test Mutation Response of GraphQL queries
 */
@Data
public class GraphQLMutationResponse {
    @JsonProperty("data")
    public Res res;

    @Data
    public static class Res {
        @JsonProperty("updateGuest")
        public UpdateGuest updateGuest;
    }

    @Data
    public static class UpdateGuest {
        @JsonProperty("guest_id")
        private String guestId;

        @JsonProperty("guest_name")
        private String guestName;
    }
}
