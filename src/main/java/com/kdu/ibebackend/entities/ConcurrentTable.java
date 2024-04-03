package com.kdu.ibebackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Mock Table for booking which we will be using for handling concurrency.
 * Will implement locking on this one and successful bookings that have been written on this table will be created
 * in GraphQL by running the Mutation Queries
 */
@Getter
@Setter
@Entity
@Table(name = "concurrent_table")
public class ConcurrentTable {
    @Id
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;
}