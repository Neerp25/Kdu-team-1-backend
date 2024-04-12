package com.kdu.ibebackend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdu.ibebackend.models.SecretConfig;
import com.kdu.ibebackend.service.SecretsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    private final SecretConfig secretConfig;
    private SecretsManagerService secretsManagerService;

    @Autowired
    public DataSourceConfig(SecretsManagerService secretsManagerService, @Value("${awsSecretName}") String secretName) throws JsonProcessingException {
        this.secretsManagerService = secretsManagerService;
        String awsSecret = secretsManagerService.getSecretValue(secretName);
        ObjectMapper mapper = new ObjectMapper();
        secretConfig = mapper.readValue(awsSecret, SecretConfig.class);
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(secretConfig.getDbUrl());
        dataSourceBuilder.username(secretConfig.getDbUsername());
        dataSourceBuilder.password(secretConfig.getDbPassword());
        return dataSourceBuilder.build();
    }
}