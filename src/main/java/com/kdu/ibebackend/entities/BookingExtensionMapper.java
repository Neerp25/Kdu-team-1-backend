package com.kdu.ibebackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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

    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    @Column(name = "room_type_id", nullable = false)
    private Integer roomTypeId;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "billing_id", referencedColumnName = "id")
    private BillingInfo billingInfo;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "traveller_id", referencedColumnName = "id")
    private TravelInfo travelInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "promo_code_id", referencedColumnName = "id")
    private PromoCode promoCode;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private TransactionInfo transactionInfo;
}