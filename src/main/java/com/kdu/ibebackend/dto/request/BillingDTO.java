package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BillingDTO {
    private String firstName;
    private String lastName;
    private String mailingAddress1;
    private String mailingAddress2;
    private String country;
    private String city;
    private String state;
    private Integer zipcode;
    private String phone;
    private String email;
}
