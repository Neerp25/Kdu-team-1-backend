package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.dto.request.RoomReviewDTO;
import com.kdu.ibebackend.dto.request.SearchParamDTO;
import com.kdu.ibebackend.dto.response.PromoCodeDTO;
import com.kdu.ibebackend.exceptions.custom.InvalidPromoException;
import com.kdu.ibebackend.service.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/roomresult")
public class
RoomResultController {
    private RoomResultService roomResultService;
    private PromotionService promotionService;
    private RoomReviewService roomReviewService;
    @Autowired
    public RoomResultController(RoomResultService roomResultService, PromotionService promotionService, RoomReviewService roomReviewService) {
        this.roomResultService = roomResultService;
        this.promotionService = promotionService;
        this.roomReviewService = roomReviewService;
    }

    @PostMapping("/search")
    public ResponseEntity<Object> searchResults(@RequestBody @Valid SearchParamDTO searchParamDTO,
                                                @RequestParam(defaultValue = "0") @Min(0) @NotNull @Valid int page,
                                                @RequestParam(defaultValue = "10") @Min(1) @NotNull @Valid int size) {

        return roomResultService.paginatedData(searchParamDTO, page, size);
    }

    @GetMapping("/promotion")
    public ResponseEntity<PromoCodeDTO> promoCode(@RequestParam @NotNull @Valid String promoCode, @RequestParam @NotNull @Min(1) @Max(6) Integer roomTypeId) throws InvalidPromoException {
        return promotionService.validatePromoCode(promoCode, roomTypeId);
    }

    @PostMapping("/review")
    public ResponseEntity<String> addRoomReview(@RequestBody @Valid RoomReviewDTO roomReviewDTO) {
        return  roomReviewService.addRoomReview(roomReviewDTO);
    }

    @GetMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam @NotNull @Email String email, @RequestParam @NotNull @Min(1) @Max(6) Integer roomTypeId) {
        return roomReviewService.sendEmail(email, roomTypeId);
    }
}
