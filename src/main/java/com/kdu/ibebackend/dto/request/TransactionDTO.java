package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kdu.ibebackend.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TransactionDTO {
    @NotNull(message = ValidationConstants.NIGHTLY_RATE_NOT_NULL)
    @PositiveOrZero(message = ValidationConstants.NIGHTLY_RATE_POSITIVE_OR_ZERO)
    private Double nightlyRate;

    @NotNull(message = ValidationConstants.SUBTOTAL_NOT_NULL)
    @PositiveOrZero(message = ValidationConstants.SUBTOTAL_POSITIVE_OR_ZERO)
    private Double subtotal;

    @NotNull(message = ValidationConstants.TAXES_NOT_NULL)
    @PositiveOrZero(message = ValidationConstants.TAXES_POSITIVE_OR_ZERO)
    private Double taxes;

    @NotNull(message = ValidationConstants.VAT_NOT_NULL)
    @PositiveOrZero(message = ValidationConstants.VAT_POSITIVE_OR_ZERO)
    private Double vat;

    @NotNull(message = ValidationConstants.TOTAL_NOT_NULL)
    @PositiveOrZero(message = ValidationConstants.TOTAL_POSITIVE_OR_ZERO)
    private Double total;
}
