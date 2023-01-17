package com.example.demo.IdClass;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("vip")
public class VipCustomerWithIdClass extends CustomerWithIdClass {
    private String vipNumber;

    public VipCustomerWithIdClass() {
    }

    public VipCustomerWithIdClass(String firstName, String lastName, String vipNumber) {
        super(firstName, lastName);
        this.vipNumber = vipNumber;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }
}