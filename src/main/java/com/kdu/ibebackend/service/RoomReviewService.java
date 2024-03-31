package com.kdu.ibebackend.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.kdu.ibebackend.constants.Constants;
import com.kdu.ibebackend.constants.EmailTemplate;
import com.kdu.ibebackend.constants.Errors;
import com.kdu.ibebackend.dto.request.RoomReviewDTO;
import com.kdu.ibebackend.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomReviewService {
    private DynamoDBService dynamoDBService;
    private EmailService emailService;
    @Autowired
    public void RoomReviewService(DynamoDBService dynamoDBService, EmailService emailService) {
        this.dynamoDBService = dynamoDBService;
        this.emailService = emailService;
    }

    public ResponseEntity<String> addRoomReview(RoomReviewDTO roomReviewDTO) {
        if(dynamoDBService.checkReview(roomReviewDTO.getUserEmail())) return new ResponseEntity<>(Errors.REVIEW_ALREADY_DONE, HttpStatus.OK);

        dynamoDBService.saveRoomReview(roomReviewDTO);

        return new ResponseEntity<>(Constants.DYNAMODB_SUCCESS, HttpStatus.OK);
    }

    public ResponseEntity<String> sendEmail(String email, Integer roomTypeId) {
        String link = EmailUtils.formLinkGenerator(email, roomTypeId);
        emailService.sendEmail(email, EmailTemplate.EMAIL_SUBJECT, EmailUtils.htmlLinkInjector(link));
        return new ResponseEntity<>(EmailTemplate.EMAIL_SUCCESS, HttpStatus.OK);
    }
}
