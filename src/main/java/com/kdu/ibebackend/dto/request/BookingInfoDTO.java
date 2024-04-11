package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kdu.ibebackend.constants.ValidationConstants;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BookingInfoDTO {
    @Pattern(regexp = ValidationConstants.DATE_FORMAT_REGEX, message = ValidationConstants.DATE_FORMAT_MESSAGE)
    @NotNull(message = ValidationConstants.START_DATE_NOT_NULL_MESSAGE)
    private String checkInDate;

    @Pattern(regexp = ValidationConstants.DATE_FORMAT_REGEX, message = ValidationConstants.DATE_FORMAT_MESSAGE)
    @NotNull(message = ValidationConstants.END_DATE_NOT_NULL_MESSAGE)
    private String checkOutDate;

    @Positive
    @NotNull
    private Integer adultCount;

    @NotNull
    private Integer childCount;

    @NotNull
    private Double totalCost;

    @NotNull
    private Double amountDueResort;

    @NotBlank
    @Size(min = 2)
    private String guestName;

    @NotNull
    @Positive
    private Integer roomTypeId;

    @NotNull
    @Positive
    private Integer rooms;

    @Nullable
    private Integer promotionId;

    @Nullable
    private Integer promoCodeId;
}
