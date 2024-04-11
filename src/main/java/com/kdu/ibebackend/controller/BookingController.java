package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.dto.graphql.BookingStatus;
import com.kdu.ibebackend.dto.response.PersonalBooking;
import com.kdu.ibebackend.models.dynamodb.RoomReview;
import com.kdu.ibebackend.service.BookingService;
import com.kdu.ibebackend.service.DynamoDBService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class BookingController {
    private BookingService bookingService;
    private DynamoDBService dynamoDBService;

    @Autowired
    private BookingController(BookingService bookingService, DynamoDBService dynamoDBService) {
        this.bookingService = bookingService;
        this.dynamoDBService = dynamoDBService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<Object> getBookings(@RequestParam @Email String email) {
        List<PersonalBooking> bookings = bookingService.fetchBookings(email);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/review")
    public ResponseEntity<List<RoomReview>> getReviews(@RequestParam @Min(1) Integer id) {
        List<RoomReview> roomReviews = dynamoDBService.findRoomReviewsById(id);
        return new ResponseEntity<>(roomReviews, HttpStatus.OK);
    }
}
