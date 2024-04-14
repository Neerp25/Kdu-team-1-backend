package com.kdu.ibebackend.utils;

import com.kdu.ibebackend.entities.Tenant;
import com.kdu.ibebackend.repository.TenantRepository;
import com.kdu.ibebackend.service.DynamoDBService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initial Command line runner to execute functions before application starts running
 */
@Component
@Slf4j
public class InitRunner implements CommandLineRunner {
    private DynamoDBService dynamoDBService;
    private PasswordEncoder passwordEncoder;
    private TenantRepository tenantRepository;
    @Autowired
    public InitRunner(DynamoDBService dynamoDBService, PasswordEncoder passwordEncoder, TenantRepository tenantRepository) {
        this.dynamoDBService = dynamoDBService;
        this.passwordEncoder = passwordEncoder;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        RoomUtils.findRoomInfo(dynamoDBService);
        Tenant tenant = new Tenant();
        tenant.setEmail("asishmahapatra918@gmail.com");
        tenant.setPassword(passwordEncoder.encode("Hello"));
        tenantRepository.save(tenant);
        log.info("Application Initialized. Caches in place....");
    }
}