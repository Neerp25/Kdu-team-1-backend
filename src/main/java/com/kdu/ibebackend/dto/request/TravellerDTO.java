package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kdu.ibebackend.constants.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TravellerDTO {
    @NotBlank(message = ValidationConstants.FIRST_NAME_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.FIRST_NAME_SIZE)
    private String firstName;

    @NotBlank(message = ValidationConstants.LAST_NAME_NOT_BLANK)
    @Size(min = 2, message = ValidationConstants.LAST_NAME_SIZE)
    private String lastName;

    @NotBlank(message = ValidationConstants.PHONE_NOT_BLANK)
    @Pattern(regexp = ValidationConstants.PHONE_FORMAT_REGEX, message = ValidationConstants.PHONE_PATTERN)
    private String phone;

    @NotBlank(message = ValidationConstants.EMAIL_NOT_BLANK)
    @Email(message = ValidationConstants.EMAIL_FORMAT)
    private String email;
}
