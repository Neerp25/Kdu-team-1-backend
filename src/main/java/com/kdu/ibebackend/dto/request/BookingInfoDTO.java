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

    @Positive(message = ValidationConstants.ADULT_COUNT_POSITIVE)
    @NotNull(message = ValidationConstants.ADULT_COUNT_NOT_NULL)
    private Integer adultCount;

    @NotNull(message = ValidationConstants.CHILD_COUNT_NOT_NULL)
    private Integer childCount;

    @NotNull(message = ValidationConstants.TOTAL_COST_NOT_NULL)
    private Double totalCost;

    @NotNull(message = ValidationConstants.AMOUNT_DUE_RESORT_NOT_NULL)
    private Double amountDueResort;

    @NotBlank(message = ValidationConstants.GUEST_NAME_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.GUEST_NAME_SIZE)
    private String guestName;

    @Positive(message = ValidationConstants.ROOM_TYPE_ID_POSITIVE)
    @NotNull(message = ValidationConstants.ROOM_TYPE_ID_NOT_NULL)
    private Integer roomTypeId;

    @Positive(message = ValidationConstants.ROOMS_POSITIVE)
    @NotNull(message = ValidationConstants.ROOMS_NOT_NULL)
    private Integer rooms;


    @Nullable
    private Integer promotionId;

    @Nullable
    private Integer promoCodeId;
}
