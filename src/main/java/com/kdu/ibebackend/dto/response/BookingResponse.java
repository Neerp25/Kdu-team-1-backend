package com.kdu.ibebackend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties
public class BookingResponse {
    private Boolean cancelled;
    private String roomTypeName;
    private Integer roomTypeId;
    private String reservationId;
    private Date checkInDate;
    private Date checkOutDate;
    private String promotionTitle;
    private String promotionDescription;
    private Integer adults;
    private Integer children;
    private Double nightlyRate;
    private Double subtotal;
    private Double taxes;
    private Double vat;
    private Integer total;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String billingFirstName;
    private String billingLastName;
    private String mailingAddress1;
    private String mailingAddress2;
    private String country;
    private String city;
    private String state;
    private Integer zipcode;
    private String billingPhone;
    private String billingEmail;
    private String transactionId;
    private String imageUrl;
}
