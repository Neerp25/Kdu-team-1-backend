package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TransactionDTO {
    @NotNull
    @NotBlank
    private Double nightlyRate;

    @NotNull
    @NotBlank
    private Double subtotal;

    @NotNull
    @NotBlank
    private Double taxes;

    @NotNull
    @NotBlank
    private Double vat;

    @NotNull
    @NotBlank
    private Double total;
}
