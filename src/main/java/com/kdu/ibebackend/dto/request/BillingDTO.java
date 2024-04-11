package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kdu.ibebackend.constants.ValidationConstants;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BillingDTO {
    @NotBlank(message = ValidationConstants.FIRST_NAME_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.FIRST_NAME_SIZE)
    private String firstName;

    @NotBlank(message = ValidationConstants.LAST_NAME_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.LAST_NAME_SIZE)
    private String lastName;

    @NotBlank(message = ValidationConstants.MAILING_ADDRESS1_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.MAILING_ADDRESS1_SIZE)
    private String mailingAddress1;

    private String mailingAddress2;

    @NotBlank(message = ValidationConstants.COUNTRY_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.COUNTRY_SIZE)
    private String country;

    @NotBlank(message = ValidationConstants.CITY_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.CITY_SIZE)
    private String city;

    @NotBlank(message = ValidationConstants.STATE_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.STATE_SIZE)
    private String state;

    @Min(value = 10000, message = ValidationConstants.ZIPCODE_MIN)
    private Integer zipcode;

    @NotBlank(message = ValidationConstants.PHONE_NOT_BLANK)
    @Pattern(regexp = ValidationConstants.PHONE_FORMAT_REGEX, message = ValidationConstants.PHONE_PATTERN)
    private String phone;

    @NotBlank(message = ValidationConstants.EMAIL_NOT_BLANK)
    @Email(message = ValidationConstants.EMAIL_FORMAT)
    private String email;
}
