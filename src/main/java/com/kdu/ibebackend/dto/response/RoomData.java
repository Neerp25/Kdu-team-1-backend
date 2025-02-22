package com.kdu.ibebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomData {
    @JsonProperty("date")
    private String date;

    @JsonProperty("room")
    private Room room;

    @Data
    public static class Room {
        @JsonProperty("room_id")
        private Integer roomId;

        @JsonProperty("room_type_id")
        private Integer roomTypeId;

        @JsonProperty("room_type")
        private RoomType roomType;
    }
}
