package com.github.spring.example.lockconfigs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonLockConfigs {
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setLockWatchdogTimeout(20 * 1000);
        config.useSingleServer()
                .setPassword("mypass")
                .setAddress("redis://127.0.0.1:6379");

        return Redisson.create(config);
    }
}