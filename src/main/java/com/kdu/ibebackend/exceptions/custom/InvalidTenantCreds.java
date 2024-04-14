package com.kdu.ibebackend.exceptions.custom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidTenantCreds extends Exception {
    public InvalidTenantCreds(String errorMessage) {
        super(errorMessage);
        log.error(errorMessage);
    }
}
