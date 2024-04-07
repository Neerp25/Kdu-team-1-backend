package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdu.ibebackend.dto.response.RoomType;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class ListRoomTypes {
    @JsonProperty("data")
    private Res res;

    @Data
    @JsonIgnoreProperties
    public static class Res {
        @JsonProperty("listRoomTypes")
        private List<RoomType> roomTypes;
    }
}
