package com.github.spring.example.model;

import java.io.Serializable;

public class LockId implements Serializable {
    private String region;
    private String lock_key;

    public LockId(String region, String lock_key) {
        this.region = region;
        this.lock_key = lock_key;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockId lockId = (LockId) o;

        if (!region.equals(lockId.region)) return false;
        return lock_key.equals(lockId.lock_key);
    }

    @Override
    public int hashCode() {
        int result = region.hashCode();
        result = 31 * result + lock_key.hashCode();
        return result;
    }
}