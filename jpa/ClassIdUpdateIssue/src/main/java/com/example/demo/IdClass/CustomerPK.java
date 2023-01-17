package com.example.demo.IdClass;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CustomerPK implements Serializable {

    private Long unitId;

    private Long versionId;

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
}