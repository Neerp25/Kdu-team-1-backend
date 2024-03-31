package com.kdu.ibebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PromoCodeDTO {
    @JsonProperty("promotion_title")
    private String promoTitle;

    @JsonProperty("promotion_description")
    private String promoDescription;

    @JsonProperty("price_factor")
    private Double priceFactor;

    @JsonProperty("room_type_id")
    private Integer roomTypeId;
}
