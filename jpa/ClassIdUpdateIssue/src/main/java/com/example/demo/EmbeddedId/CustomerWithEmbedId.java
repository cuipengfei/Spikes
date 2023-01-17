package com.example.demo.EmbeddedId;


import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when vip_number is not null then 'vip' else 'normal' end")
@DiscriminatorValue("normal")
public class CustomerWithEmbedId implements Serializable {

    @EmbeddedId
    private CustomerPK customerPK;

    private String firstName;
    private String lastName;

    protected CustomerWithEmbedId() {
    }

    public CustomerWithEmbedId(String firstName, String lastName) {
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
        if (customerPK == null) {
            customerPK = new CustomerPK();
        }
        this.customerPK.setVersionId(versionId);
    }

    public void setUnitId(Long unitId) {
        if (customerPK == null) {
            customerPK = new CustomerPK();
        }
        this.customerPK.setUnitId(unitId);
    }
}