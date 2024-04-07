package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.constants.EmailTemplate;
import com.kdu.ibebackend.constants.graphql.GraphQLFetch;
import com.kdu.ibebackend.dto.graphql.ListRoomTypes;
import com.kdu.ibebackend.dto.request.BookingDTO;
import com.kdu.ibebackend.dto.response.BookingResponse;
import com.kdu.ibebackend.dto.response.RoomType;
import com.kdu.ibebackend.exceptions.custom.OtpException;
import com.kdu.ibebackend.service.BookingService;
import com.kdu.ibebackend.service.EmailService;
import com.kdu.ibebackend.service.GraphQLService;
import com.kdu.ibebackend.service.OtpService;
import com.kdu.ibebackend.utils.EmailUtils;
import com.kdu.ibebackend.utils.GraphUtils;
import com.kdu.ibebackend.utils.OtpUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class ConfirmationPageController {
    private EmailService emailService;
    private OtpService otpService;
    private BookingService bookingService;
    private GraphQLService graphQLService;

    @Autowired
    public ConfirmationPageController(EmailService emailService, OtpService otpService, BookingService bookingService, GraphQLService graphQLService) {
        this.emailService = emailService;
        this.bookingService = bookingService;
        this.otpService = otpService;
        this.graphQLService = graphQLService;
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

    @GetMapping("/verifyBooking")
    public ResponseEntity<BookingResponse> verifyBooking(@RequestParam String reservationId) {
        BookingResponse res = bookingService.verifyBooking(reservationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/booking")
    public ResponseEntity<String> booking(@RequestBody BookingDTO bookingDTO) {
         log.info(bookingDTO.toString());
         UUID bookingId = bookingService.createFinalBooking(bookingDTO);
         return new ResponseEntity<>(bookingId.toString(), HttpStatus.OK);
    }

    @GetMapping("/deleteBooking")
    public ResponseEntity<String> deleteBooking(@RequestParam String reservationId) {
        bookingService.deleteBooking(reservationId);
        return new ResponseEntity<>("Booking Cancelled Successfully", HttpStatus.OK);
    }

    @GetMapping("bookingEmail")
    public ResponseEntity<String> sendMail(@RequestParam String email, @RequestParam String reservationId) {
        String templateData = EmailUtils.bookingEmailTemplateGenerator(reservationId);
        emailService.sendTemplatedEmail(EmailTemplate.BOOKING_TEMPLATE_NAME, email, templateData);
        return new ResponseEntity<>("Email Sent Successfully", HttpStatus.OK);
    }
}
