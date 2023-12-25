package com.github.spring.example.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DefaultJDBCLockConfigs {

    private static final int TIME_TO_LIVE = 20 * 1000;

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean("defaultLockRepository")
    public LockRepository defaultLockRepository(
            DataSource dataSource,
            @Value(value = "${spring.jpa.properties.hibernate.default_schema}") String schema) {
        DefaultLockRepository defaultLockRepository = new DefaultLockRepository(dataSource);
        defaultLockRepository.setPrefix(schema + ".INT_");
        defaultLockRepository.setTimeToLive(TIME_TO_LIVE);
        return defaultLockRepository;
    }

    @Bean("defaultJdbcLockRegistry")
    public JdbcLockRegistry defaultJdbcLockRegistry(@Qualifier("defaultLockRepository") LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }
}