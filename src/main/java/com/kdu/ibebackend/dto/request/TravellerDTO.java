package com.kdu.ibebackend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TravellerDTO {
    private String firstName;
    private String lastName;
    private Integer phone;
    private String email;
}
