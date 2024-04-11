package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.constants.EmailTemplate;
import com.kdu.ibebackend.dto.request.BookingDTO;
import com.kdu.ibebackend.dto.response.BookingResponse;
import com.kdu.ibebackend.exceptions.custom.BookingException;
import com.kdu.ibebackend.exceptions.custom.OtpException;
import com.kdu.ibebackend.service.*;
import com.kdu.ibebackend.utils.EmailUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

/**
 * All APIs defined for Booking flow
 */
@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class ConfirmationPageController {
    private EmailService emailService;
    private OtpService otpService;
    private BookingService bookingService;
    private GraphQLService graphQLService;
    private PreBookingService preBookingService;

    @Autowired
    public ConfirmationPageController(EmailService emailService, OtpService otpService, BookingService bookingService, GraphQLService graphQLService, PreBookingService preBookingService) {
        this.emailService = emailService;
        this.bookingService = bookingService;
        this.otpService = otpService;
        this.graphQLService = graphQLService;
        this.preBookingService = preBookingService;
    }

    @GetMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestParam @Valid @NotNull @Email String email) {
        String otp = otpService.sendOtp(email);
        return new ResponseEntity<>(otp, HttpStatus.OK);
    }

    @GetMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam @Valid @NotNull @Email String email, @RequestParam @NotNull String otp) throws OtpException {
        String res = otpService.verifyOtp(email, otp);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/verifyBooking")
    public ResponseEntity<BookingResponse> verifyBooking(@RequestParam @Valid String reservationId) {
        BookingResponse res = bookingService.verifyBooking(reservationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/booking")
    public ResponseEntity<String> booking(@RequestBody @Valid BookingDTO bookingDTO) throws BookingException, ParseException {
         log.info(bookingDTO.toString());
         UUID bookingId = bookingService.createFinalBooking(bookingDTO);
         return new ResponseEntity<>(bookingId.toString(), HttpStatus.OK);
    }

    @GetMapping("/deleteBooking")
    public ResponseEntity<String> deleteBooking(@RequestParam @Valid String reservationId) throws BookingException {
        bookingService.deleteBooking(reservationId);
        return new ResponseEntity<>("Booking Cancelled Successfully", HttpStatus.OK);
    }

    @GetMapping("bookingEmail")
    public ResponseEntity<String> sendMail(@RequestParam @Valid @NotNull @Email String email, @RequestParam String reservationId) {
        String templateData = EmailUtils.bookingEmailTemplateGenerator(reservationId);
        emailService.sendTemplatedEmail(EmailTemplate.BOOKING_TEMPLATE_NAME, email, templateData);
        return new ResponseEntity<>("Email Sent Successfully", HttpStatus.OK);
    }
}
