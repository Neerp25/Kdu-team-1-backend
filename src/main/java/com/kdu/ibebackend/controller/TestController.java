package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.constants.Constants;
import com.kdu.ibebackend.constants.graphql.GraphQLFetch;
import com.kdu.ibebackend.constants.graphql.GraphQLMutations;
import com.kdu.ibebackend.dto.GraphQLMutationResponse;
import com.kdu.ibebackend.dto.GraphQLMutationVariables;
import com.kdu.ibebackend.dto.GraphQLResponse;
import com.kdu.ibebackend.service.GraphQLService;

import com.kdu.ibebackend.utils.GraphUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * A Test Controller that defines test endpoints for consuming in frontend. CORS
 * has been allowed on
 * this entire controller
 */
@RestController
@CrossOrigin("*")
@Slf4j
public class TestController {
    private final GraphQLService graphQLService;

    @Autowired
    public TestController(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }


    @Operation(summary = "Test Working Server",
            parameters = {
                    @Parameter(name = "X-Api-Key", description = "API Key", required = true, in = ParameterIn.HEADER)
            })
    @GetMapping("/test")
    public String testHealthEndpoint() {
        return Constants.SERVER_SUCCESS;
    }


    @GetMapping("/api/graphql")
    public ResponseEntity<GraphQLResponse> testGraphQL() {
        String graphqlQuery = GraphUtils.convertToGraphQLRequest(GraphQLFetch.testQuery);
        return graphQLService.executePostRequest(graphqlQuery, GraphQLResponse.class);
    }

    /**
     * A test endpoint to look at multi-threading and concurrent mutations in GraphQL and PostgresSQL
     * @param name
     * @return
     */
    @GetMapping("/concurrency_test")
    public ResponseEntity<GraphQLMutationResponse> testMutation(@RequestParam String name) {
        GraphQLMutationVariables graphQLMutationVariables = new GraphQLMutationVariables();
        graphQLMutationVariables.setGuestName(name);

        String graphQLMutation = GraphUtils.convertToVariableGraphQLRequest(GraphQLMutations.dummyMutation, graphQLMutationVariables);

        log.info(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return graphQLService.executePostRequest(graphQLMutation, GraphQLMutationResponse.class);
    }

    @GetMapping("/cpu-spike")
    public String cpuSpike() {
        Random random = new Random();
        long limit = 10000000L;
        for (long i = 0; i < limit; i++) {
            double randomNumber = random.nextDouble();
            double squareRoot = Math.sqrt(randomNumber);
            double sineValue = Math.sin(randomNumber);
            double cosineValue = Math.cos(randomNumber);
            double tangentValue = Math.tan(randomNumber);
            double logValue = Math.log(randomNumber);
        }
        return "CPU spike task completed";
    }
}
