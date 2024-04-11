package com.kdu.ibebackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Table to store transaction info of the user
 */
@Getter
@Setter
@Entity
@Table(name = "transaction_info")
public class TransactionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nightly_rate", nullable = false)
    private Double nightlyRate;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @Column(name = "taxes", nullable = false)
    private Double taxes;

    @Column(name = "vat", nullable = false)
    private Double vat;

    @Column(name = "total", nullable = false)
    private Double total;
}