package com.kdu.ibebackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Table to map the booking_id of the provided GraphQL schema with travel info and billing info
 * defined by us. Also contains transaction id
 */
@Getter
@Setter
@Entity
@Table(name = "booking_extension_mapper")
public class BookingExtensionMapper {
    @Id
    @Column(name = "booking_id", nullable = false)
    private Integer bookingId;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "billing_id", referencedColumnName = "id")
    private BillingInfo billingInfo;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "traveller_id", referencedColumnName = "id")
    private TravelInfo travelInfo;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;
}