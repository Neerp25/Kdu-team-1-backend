package com.kdu.ibebackend.dto.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdu.ibebackend.models.PromotionType;
import lombok.Data;

import java.util.List;

/**
 * Upper level DTO for ListPromotions GraphQL Query
 */
@Data
public class ListPromotions {
    @JsonProperty("data")
    public Res res;

    @Data
    public static class Res {
        @JsonProperty("listPromotions")
        private List<PromotionType> promotionTypeList;
    }
}
