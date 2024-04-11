package com.kdu.ibebackend.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdu.ibebackend.constants.Constants;
import com.kdu.ibebackend.models.dynamodb.OtpEntry;
import com.kdu.ibebackend.models.dynamodb.RoomInfo;
import com.kdu.ibebackend.models.dynamodb.RoomReview;
import com.kdu.ibebackend.models.dynamodb.TenantConfig;
import com.kdu.ibebackend.utils.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository to perform CRUD Operations on DynamoDB
 */
@Repository
@Slf4j
public class DynamoRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    private Environment env;

    public DynamoRepository(AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public TenantConfig getTenantConfig(Number tenantId) throws JsonProcessingException {
        if(EnvUtils.localEnvironmentCheck(env.getProperty("spring.profiles.active"))) {
            log.info(env.getProperty("config"));
            ObjectMapper mapper = new ObjectMapper();
            TenantConfig tenantConfig = mapper.readValue(env.getProperty("config"), TenantConfig.class);
            return tenantConfig;
        }
        return dynamoDBMapper.load(TenantConfig.class, tenantId);
    }

    public RoomInfo getRoomInfo(Number roomTypeId) {
        return dynamoDBMapper.load(RoomInfo.class, roomTypeId);
    }

    public void updateRoomInfo(RoomInfo roomInfo) {
        dynamoDBMapper.save(roomInfo);
    }

    public void saveRoomReview(RoomReview roomReview) {
        dynamoDBMapper.save(roomReview);
    }

    public boolean findRoomReview(String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":key", new AttributeValue().withS(email));

        DynamoDBQueryExpression<RoomReview> queryExpression = new DynamoDBQueryExpression<RoomReview>()
                .withIndexName(Constants.ROOM_REVIEW_SECONDARY_INDEX)
                .withKeyConditionExpression("user_email = :key")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);

        List<RoomReview> reviews = dynamoDBMapper.query(RoomReview.class, queryExpression);

        return !reviews.isEmpty();
    }

    public List<RoomReview> findRoomReviews(String roomId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":key", new AttributeValue().withN(roomId));

        DynamoDBQueryExpression<RoomReview> queryExpression = new DynamoDBQueryExpression<RoomReview>()
                .withIndexName(Constants.ROOM_REVIEW_ID_SECONDARY_INDEX)
                .withKeyConditionExpression("room_type_id = :key")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);

        List<RoomReview> reviews = dynamoDBMapper.query(RoomReview.class, queryExpression);

        return reviews;
    }

    public void saveOtpEntry(OtpEntry otpEntry) {
        dynamoDBMapper.save(otpEntry);
    }

    public OtpEntry getOtpEntry(String email) {
        return dynamoDBMapper.load(OtpEntry.class, email);
    }

}
