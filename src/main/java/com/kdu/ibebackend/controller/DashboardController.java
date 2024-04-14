package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.dto.TenantLoginCreds;
import com.kdu.ibebackend.dto.response.DashboardBookings;
import com.kdu.ibebackend.dto.response.DashboardResponse;
import com.kdu.ibebackend.exceptions.custom.InvalidTenantCreds;
import com.kdu.ibebackend.service.DashboardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class DashboardController {
    private DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/graph-stats")
    public ResponseEntity<DashboardResponse> getGraphStats() {
        DashboardResponse dashboardData = dashboardService.getDashBoardResponse();
        return new ResponseEntity<>(dashboardData, HttpStatus.OK);
    }

    @GetMapping("/latestBookings")
    public ResponseEntity<List<DashboardBookings>> getLatestBookings() {
        List<DashboardBookings> data = dashboardService.getLatestBookings();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> tenantLogin(@RequestBody @Valid TenantLoginCreds tenantLoginCreds) throws InvalidTenantCreds {
        return new ResponseEntity<>(dashboardService.verifyCreds(tenantLoginCreds), HttpStatus.OK);
    }
}
