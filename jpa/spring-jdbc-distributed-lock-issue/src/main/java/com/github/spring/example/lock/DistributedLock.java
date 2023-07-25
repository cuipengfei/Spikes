package com.github.spring.example.lock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;

import javax.sql.DataSource;

@Configuration
public class DistributedLock {

    public static final int TIME_TO_LIVE = 20 * 1000;

    @Bean
    public LockRepository defaultLockRepository(DataSource dataSource, @Value(value = "${spring.jpa.properties.hibernate.default_schema}") String schema) {
        DefaultLockRepository defaultLockRepository = new DefaultLockRepository(dataSource);
        defaultLockRepository.setPrefix(schema + ".INT_");
        defaultLockRepository.setTimeToLive(TIME_TO_LIVE);
        return defaultLockRepository;
    }

    @Bean
    public JdbcLockRegistry jdbcLockRegistry(LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }
}