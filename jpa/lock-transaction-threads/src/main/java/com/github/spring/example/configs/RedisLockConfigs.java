package com.github.spring.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.time.Duration;

@Configuration
public class RedisLockConfigs {
    private static final String LOCK_REGISTRY_REDIS_KEY = "MY_REDIS_KEY";
    private static final Duration RELEASE_TIME_DURATION = Duration.ofSeconds(20);

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(
                redisConnectionFactory,
                LOCK_REGISTRY_REDIS_KEY,
                RELEASE_TIME_DURATION.toMillis());
    }
}