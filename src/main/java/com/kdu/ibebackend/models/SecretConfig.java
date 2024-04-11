package com.kdu.ibebackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SecretConfig {
    @JsonProperty("graphql.url")
    private String graphqlUrl;

    @JsonProperty("graphql.api.key")
    private String graphqlApiKey;

    @JsonProperty("currency.api.url")
    private String currencyApiUrl;

    @JsonProperty("currency.api.key")
    private String currencyApiKey;

    @JsonProperty("db.url")
    private String dbUrl;

    @JsonProperty("db.username")
    private String dbUsername;

    @JsonProperty("db.password")
    private String dbPassword;
}
