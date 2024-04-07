package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TransactionDTO {
    private Double nightlyRate;

    private Double subtotal;

    private Double taxes;

    private Double vat;

    private Double total;
}
