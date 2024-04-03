package com.kdu.ibebackend.dto;

import lombok.Data;

/**
 * DTO for injecting variables in GraphQL mutation query
 */
@Data
public class GraphQLMutationVariables {
    private String guestName;
}
