package com.github.spring.example.configs;

import com.github.spring.example.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DefaultJDBCLockConfigs {

    private static final int TIME_TO_LIVE = 20 * 1000;

    @Bean("transactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean("defaultLockRepository")
    public LockRepository defaultLockRepository(
            DataSource dataSource,
            @Value(value = "${spring.jpa.properties.hibernate.default_schema}") String schema) {
        DefaultLockRepository defaultLockRepository = new DefaultLockRepository(dataSource) {
            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
            @Override
            public boolean acquire(String lock) {
                logger.info("acquire method of default lock repo, current transaction is {}",
                        BaseService.getCurrentTransactionName());
                return super.acquire(lock);
            }
        };
        defaultLockRepository.setPrefix(schema + ".INT_");
        defaultLockRepository.setTimeToLive(TIME_TO_LIVE);
        return defaultLockRepository;
    }

    @Bean("defaultJdbcLockRegistry")
    public JdbcLockRegistry defaultJdbcLockRegistry(@Qualifier("defaultLockRepository") LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }
}