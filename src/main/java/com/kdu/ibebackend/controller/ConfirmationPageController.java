package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.exceptions.custom.OtpException;
import com.kdu.ibebackend.service.EmailService;
import com.kdu.ibebackend.service.OtpService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class ConfirmationPageController {
    private EmailService emailService;
    private OtpService otpService;

    @Autowired
    public ConfirmationPageController(EmailService emailService, OtpService otpService) {
        this.emailService = emailService;
        this.otpService = otpService;
    }

    @GetMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestParam @NotNull @Email String email) {
        String otp = otpService.sendOtp(email);
        return new ResponseEntity<>(otp, HttpStatus.OK);
    }

    @GetMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam @NotNull @Email String email, @RequestParam @NotNull String otp) throws OtpException {
        String res = otpService.verifyOtp(email, otp);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
