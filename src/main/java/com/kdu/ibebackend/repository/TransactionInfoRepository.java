package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.TransactionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, UUID> {
}