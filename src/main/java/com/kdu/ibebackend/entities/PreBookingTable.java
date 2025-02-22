package com.kdu.ibebackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Pre Booking Table which we will be using for handling concurrency.
 * Will implement locks and constraints on this one and successful bookings that have been written on this table will be created
 * in GraphQL by running the Mutation Queries
 */
@Getter
@Setter
@Entity
@Table(name = "pre_booking_table")
public class PreBookingTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;
}