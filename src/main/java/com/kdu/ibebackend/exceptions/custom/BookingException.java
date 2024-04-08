package com.kdu.ibebackend.exceptions.custom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingException extends Exception {
    public BookingException(String errorMessage) {
        super(errorMessage);
        log.error(errorMessage);
    }
}
