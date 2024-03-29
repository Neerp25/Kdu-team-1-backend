package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.constants.AuthConstants;
import com.kdu.ibebackend.controller.RoomResultController;
import com.kdu.ibebackend.dto.graphql.ListRoomRateRoomTypeMappings;
import com.kdu.ibebackend.dto.request.SearchParamDTO;
import com.kdu.ibebackend.dto.response.RoomResultResponse;
import com.kdu.ibebackend.service.DynamoDBService;
import com.kdu.ibebackend.service.GraphQLService;
import com.kdu.ibebackend.service.PromotionService;
import com.kdu.ibebackend.service.RoomResultService;
import com.kdu.ibebackend.utils.RoomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomResultController.class)
@ComponentScan(value = "com.kdu.ibebackend.config")
@ActiveProfiles("test")
public class RoomResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomResultService roomResultService;

    @MockBean
    private GraphQLService graphQLService;

    @MockBean
    private DynamoDBService dynamoDBService;

    @MockBean
    private PromotionService promotionService;

    @MockBean
    private RoomUtils roomUtils;


    @Test
    public void searchResults_ReturnsPaginatedData() throws Exception {

        SearchParamDTO searchParamDTO = new SearchParamDTO();
        searchParamDTO.setRooms(1);
        searchParamDTO.setBeds(1);
        searchParamDTO.setPropertyId(1);
        searchParamDTO.setEndDate("2024-03-02T00:00:00.000Z");
        searchParamDTO.setStartDate("2024-03-01T00:00:00.000Z");
        searchParamDTO.setTotalGuests(3);
        searchParamDTO.setBedTypes(List.of("Single"));
        searchParamDTO.setRoomTypes(List.of(1));


        given(roomResultService.paginatedData(searchParamDTO, 0, 0))
                .willReturn(ResponseEntity.ok().build());

        // Perform the request
        mockMvc.perform(post("/api/v1/roomresult/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Key", AuthConstants.AUTH_TOKEN)
                        .content("{\"startDate\":\"2024-03-01T00:00:00.000Z\",\"endDate\":\"2024-03-02T00:00:00.000Z\",\"beds\":1,\"rooms\":1,\"propertyId\":1,\"totalGuests\":1,\"roomTypes\":[1],\"bedTypes\":[\"Single\"],\"priceSort\":true,\"areaSort\":false,\"ratingSort\":false,\"isSeniorCitizen\":false,\"isKDUMember\":false,\"isMilitary\":false}")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    public void finalResponseMap_ReturnsExpectedData() throws Exception {
        // Prepare the input
        SearchParamDTO searchParamDTO = new SearchParamDTO();
        searchParamDTO.setRooms(1);
        searchParamDTO.setBeds(1);
        searchParamDTO.setPropertyId(1);
        searchParamDTO.setEndDate("2024-03-02T00:00:00.000Z");
        searchParamDTO.setStartDate("2024-03-01T00:00:00.000Z");
        searchParamDTO.setTotalGuests(3);
        searchParamDTO.setBedTypes(List.of("Single"));
        searchParamDTO.setRoomTypes(List.of(1));

        // Perform the request
        mockMvc.perform(post("/api/v1/roomresult/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Api-Key", AuthConstants.AUTH_TOKEN)
                        .content("{\"startDate\":\"2024-03-01T00:00:00.000Z\",\"endDate\":\"2024-03-02T00:00:00.000Z\",\"beds\":1,\"rooms\":1,\"propertyId\":1,\"totalGuests\":1,\"roomTypes\":[1],\"bedTypes\":[\"Single\"],\"priceSort\":true,\"areaSort\":false,\"ratingSort\":false,\"isSeniorCitizen\":false,\"isKDUMember\":false,\"isMilitary\":false}")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }







}
