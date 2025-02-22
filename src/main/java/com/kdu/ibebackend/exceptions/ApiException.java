package com.kdu.ibebackend.exceptions;

import com.kdu.ibebackend.exceptions.custom.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Exception handler for all APIs
 */
@ControllerAdvice
@Slf4j
public class ApiException {
    @ExceptionHandler({ValidParamException.class})
    public ResponseEntity<String> paramsError(ValidParamException e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> validationError(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidPromoException.class})
    public ResponseEntity<String> promoError(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({OtpException.class})
    public ResponseEntity<String> otpError(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BookingException.class})
    public ResponseEntity<String> bookingError(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidTenantCreds.class})
    public ResponseEntity<String> tenantError(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> error(Exception e) {
        log.error(e.getMessage());
        log.error(e.getClass().toGenericString());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
