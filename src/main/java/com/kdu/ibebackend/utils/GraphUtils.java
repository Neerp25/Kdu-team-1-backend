package com.kdu.ibebackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kdu.ibebackend.dto.graphql.variables.RoomAvailabilityVariables;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphUtils {
    /**
     * Converts a GraphQL query string to POST Request body
     * @param query Normal GraphQL string
     * @return POST Request JSON Body
     */
    public static String convertToGraphQLRequest(String query) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("query", query);
        return requestBody.toString();
    }

    /**
     * Injects a GraphQL Query string with variables for conditional data fetching when needed.
     * @param query GraphQL Query string
     * @param variables Variable DTO
     * @return String with injected variables for POST Request to GraphQL Endpoint
     * @param <T> Generic class of Variable DTO
     */
    public static <T> String convertToVariableGraphQLRequest(String query, T variables) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("query", query);
        requestBody.putPOJO("variables", variables);
        return requestBody.toString();
    }

    /**
     * Injects all search params in the query to fetch data based on the requested params
     * @param query GraphQL Query string
     * @param startDate Start Date from frontend
     * @param endDate End Date from frontend
     * @param propertyId Property Id from frontend
     * @return String with injected variables for POST Request to GraphQL Endpoint
     */
    public static String injectSearchParamsQuery(String query, String startDate, String endDate, String propertyId) {
        RoomAvailabilityVariables roomAvailabilityVariables = new RoomAvailabilityVariables();
        roomAvailabilityVariables.setBookingId(0);
        roomAvailabilityVariables.setStartDate(startDate);
        roomAvailabilityVariables.setEndDate(endDate);

        return convertToVariableGraphQLRequest(query, roomAvailabilityVariables);
    }
}
