package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kdu.ibebackend.constants.ValidationConstants;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BillingDTO {
    @NotBlank
    @Size(min = 2)
    private String firstName;

    @NotBlank
    @Size(min = 2)
    private String lastName;

    @NotBlank
    @Size(min = 2)
    private String mailingAddress1;

    private String mailingAddress2;

    @NotBlank
    @Size(min = 2)
    private String country;

    @NotBlank
    @Size(min = 2)
    private String city;

    @NotBlank
    @Size(min = 2)
    private String state;

    @Min(value = 10000)
    private Integer zipcode;

    @NotBlank
    @Pattern(regexp= ValidationConstants.PHONE_FORMAT_REGEX)
    private String phone;

    @NotBlank
    @Email
    private String email;
}
