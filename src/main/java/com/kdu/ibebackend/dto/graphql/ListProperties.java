package com.kdu.ibebackend.dto.graphql;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdu.ibebackend.models.Property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Upper level DTO for ListProperties GraphQL Query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListProperties {
    @JsonProperty("data")
    public Res res;

    @Data
    @NoArgsConstructor
    public static class Res {
        @JsonProperty("listProperties")
        private List<Property> properties;
    }
}
