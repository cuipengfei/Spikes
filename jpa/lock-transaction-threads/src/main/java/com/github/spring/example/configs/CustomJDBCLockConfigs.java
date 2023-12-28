package com.github.spring.example.configs;

import com.github.spring.example.service.BaseService;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class CustomJDBCLockConfigs {

    @Value("${lock.registry.name}")
    String registryName;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    public static final int TIME_TO_LIVE = 20 * 1000;

    @Bean("distributedLockTransactionManager")
    public PlatformTransactionManager transactionManagerForLock(HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("customLockRepository")
    public LockRepository customLockRepository(
            DataSource dataSource,
            @Value(value = "${spring.jpa.properties.hibernate.default_schema}") String schema) {
        DefaultLockRepository customLockRepository = new DefaultLockRepository(dataSource) {
            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "distributedLockTransactionManager")
            @Override
            public void close() {
                super.close();
            }

            @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "distributedLockTransactionManager")
            @Override
            public void delete(String lock) {
                super.delete(lock);
            }

            @Transactional(
                    propagation = Propagation.REQUIRES_NEW,
                    isolation = Isolation.SERIALIZABLE,
                    transactionManager = "distributedLockTransactionManager"
            )
            @Override
            public boolean acquire(String lock) {
                logger.info("acquire method of custom lock repo, current transaction is {}",
                        BaseService.getCurrentTransactionName());
                return super.acquire(lock);
            }

            @Transactional(
                    propagation = Propagation.REQUIRES_NEW,
                    readOnly = true,
                    transactionManager = "distributedLockTransactionManager"
            )
            @Override
            public boolean isAcquired(String lock) {
                return super.isAcquired(lock);
            }

            @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "distributedLockTransactionManager")
            @Override
            public void deleteExpired() {
                super.deleteExpired();
            }

            @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "distributedLockTransactionManager")
            @Override
            public boolean renew(String lock) {
                return super.renew(lock);
            }
        };
        customLockRepository.setPrefix(schema + ".INT_");
        customLockRepository.setTimeToLive(TIME_TO_LIVE);
        return customLockRepository;
    }

    @Bean("customLockRegistry")
    public LockRegistry customLockRegistry(@Qualifier("customLockRepository") LockRepository lockRepository) {
        if (Objects.equals(registryName, "jdbc")) {
            return new JdbcLockRegistry(lockRepository);
        } else {
            return redisLockRegistry;
        }
    }
}