package com.kdu.ibebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TenantLoginCreds {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
