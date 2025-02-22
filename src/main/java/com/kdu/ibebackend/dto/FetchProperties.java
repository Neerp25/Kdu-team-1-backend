package com.kdu.ibebackend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdu.ibebackend.models.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for fetching properties from GraphQL API
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchProperties {
    @JsonProperty("data")
    public Res res;

    @Data
    @NoArgsConstructor
    public static class Res {
        @JsonProperty("listProperties")
        private List<Property> properties;
    }
}
