package com.github.spring.example.service;

import com.github.spring.example.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class Problem2GoodFixDelegateService extends BaseService {

    @Autowired
    @Qualifier("customLockRegistry")
    private JdbcLockRegistry jdbcLockRegistry;

    @Override
    protected JdbcLockRegistry getJdbcLockRegistry() {
        return jdbcLockRegistry;
    }

    @Autowired
    private CatRepository catRepository;

    @Transactional
    public void doBiz(String location) {
        logCurrentTransaction(location);
        catRepository.findById(UUID.randomUUID());
    }
}
