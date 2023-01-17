package com.example.demo.IdClass;


import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when vip_number is not null then 'vip' else 'normal' end")
@DiscriminatorValue("normal")
@IdClass(CustomerPK.class)
public class CustomerWithIdClass implements Serializable {

    private String firstName;
    private String lastName;

    @Id
    private Long versionId;
    @Id
    private Long unitId;

    protected CustomerWithIdClass() {
    }

    public CustomerWithIdClass(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}