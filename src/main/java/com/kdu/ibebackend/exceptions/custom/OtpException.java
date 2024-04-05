package com.kdu.ibebackend.exceptions.custom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtpException extends Exception {
    public OtpException(String errorMessage) {
        super(errorMessage);
        log.error(errorMessage);
    }
}
