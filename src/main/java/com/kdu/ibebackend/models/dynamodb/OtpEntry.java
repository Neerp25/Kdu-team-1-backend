package com.kdu.ibebackend.models.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "kdu-team1-ibe-otpholder")
public class OtpEntry {
    @DynamoDBHashKey(attributeName = "user_email")
    private String userEmail;

    @DynamoDBAttribute(attributeName = "EXPIRATION_TIME")
    private Long expirationTime;

    @DynamoDBAttribute(attributeName = "otp")
    private Integer otp;
}
