package com.github.spring.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "int_lock")
@IdClass(LockId.class)

public class IntLock {

    private String client_id;
    private Timestamp created_date;
    @Id
    private String region;
    @Id
    private String lock_key;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLock_key() {
        return lock_key;
    }

    public void setLock_key(String lock_key) {
        this.lock_key = lock_key;
    }
}