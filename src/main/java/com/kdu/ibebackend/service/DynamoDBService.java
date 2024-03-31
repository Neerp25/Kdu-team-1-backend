package com.kdu.ibebackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kdu.ibebackend.constants.Errors;
import com.kdu.ibebackend.dto.mappers.RoomReviewMapper;
import com.kdu.ibebackend.dto.request.RoomReviewDTO;
import com.kdu.ibebackend.models.dynamodb.RoomInfo;
import com.kdu.ibebackend.models.dynamodb.RoomReview;
import com.kdu.ibebackend.models.dynamodb.TenantConfig;
import com.kdu.ibebackend.repository.DynamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 * Service Layer for interacting with DynamoDB
 */
@Service
public class DynamoDBService {

    private final DynamoRepository dynamoRepository;

    @Autowired
    public DynamoDBService(DynamoRepository dynamoRepository) {
        this.dynamoRepository = dynamoRepository;
    }

    public ResponseEntity<Object> fetchTenantConfig(Number tenantId) {
        try {
            TenantConfig tenantConfig = dynamoRepository.getTenantConfig(tenantId);
            return new ResponseEntity<>(tenantConfig, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(Errors.TENANT_CONFIG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public RoomInfo fetchRoomInfo(Number roomTypeId) {
            RoomInfo roomInfo = dynamoRepository.getRoomInfo(roomTypeId);
            return roomInfo;
    }

    public void saveRoomReview(RoomReviewDTO roomReviewDTO) {
        DecimalFormat df = new DecimalFormat("#.##");
        RoomReview roomReview = RoomReviewMapper.dtoToEntity(roomReviewDTO);
        RoomInfo roomInfo = dynamoRepository.getRoomInfo(roomReview.getRoomTypeId());

        Double newRating = (roomInfo.getRoomRating() + roomReview.getRating())/(roomInfo.getRoomReviews() + 1);
        Double formattedRating = Double.parseDouble(df.format(newRating));

        roomInfo.setRoomRating(formattedRating);
        roomInfo.setRoomReviews(roomInfo.getRoomReviews() + 1);

        dynamoRepository.updateRoomInfo(roomInfo);
        dynamoRepository.saveRoomReview(roomReview);
    }

    public boolean checkReview(String email) {
        return dynamoRepository.findRoomReview(email);
    }
}
