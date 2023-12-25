package com.github.spring.example.configs;

import com.github.spring.example.service.BaseService;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class CustomJDBCLockConfigs {

    private final Logger cusLogger = LoggerFactory.getLogger(this.getClass());

    public static final int TIME_TO_LIVE = 20 * 1000;

    @Bean("distributedLockTransactionManager")
    public PlatformTransactionManager transactionManagerForLock(HikariDataSource dataSource) {
        final SQLExceptionTranslator exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);

        return new DataSourceTransactionManager(dataSource) {
            @Override
            protected void doCommit(DefaultTransactionStatus status) {
                try {
                    super.doCommit(status);
                } catch (TransactionSystemException e) {
                    if (e.getCause() instanceof SQLException) {
                        DataAccessException exception = exTranslator
                                .translate("commit", null, (SQLException) e.getCause());
                        if (!(exception instanceof UncategorizedSQLException)) {
                            cusLogger.info("do commit method of custom datasource trans manager hit", exception);
                            throw exception;
                        }
                    }
                    throw e;
                }
            }
        };
    }

    @Bean("customLockRepository")
    public LockRepository customLockRepository(
            DataSource dataSource,
            @Value(value = "${spring.jpa.properties.hibernate.default_schema}") String schema) {
        DefaultLockRepository defaultLockRepository = new DefaultLockRepository(dataSource) {
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
                cusLogger.info("acquire method of custom lock repo, current transaction is {}",
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
        defaultLockRepository.setPrefix(schema + ".INT_");
        defaultLockRepository.setTimeToLive(TIME_TO_LIVE);
        return defaultLockRepository;
    }

    @Bean("customLockRegistry")
    public JdbcLockRegistry customLockRegistry(@Qualifier("customLockRepository") LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }
}