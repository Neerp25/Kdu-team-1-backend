package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.BillingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingInfoRepository extends JpaRepository<BillingInfo, Integer> {
}