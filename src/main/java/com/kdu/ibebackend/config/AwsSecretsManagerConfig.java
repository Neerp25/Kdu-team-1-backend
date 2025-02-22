package com.kdu.ibebackend.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.secretsmanager.caching.SecretCache;
import com.amazonaws.secretsmanager.caching.SecretCacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for using Secrets Manager in SpringBoot with temporary cache to optimise retrieval costs
 */
@Configuration
public class AwsSecretsManagerConfig {
    private Environment env;

    @Autowired
    public AwsSecretsManagerConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public AWSSecretsManager awsSecretsManager() {

        return AWSSecretsManagerClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

    @Bean
    public SecretCache secretCache(AWSSecretsManager awsSecretsManager) {
        SecretCacheConfiguration cacheConfig = new SecretCacheConfiguration()
                .withMaxCacheSize(10)
                .withCacheItemTTL(TimeUnit.MINUTES.toMillis(Integer.parseInt(Objects.requireNonNull(env.getProperty("cache.ttl")))))
                .withClient(awsSecretsManager);
        return new SecretCache(cacheConfig);
    }
}
