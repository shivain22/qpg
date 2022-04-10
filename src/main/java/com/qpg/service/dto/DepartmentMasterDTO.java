package com.qpg.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.qpg.domain.DepartmentMaster} entity.
 */
public class DepartmentMasterDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    private String name;


    private Long collegeMasterId;

    private String collegeMasterName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCollegeMasterId() {
        return collegeMasterId;
    }

    public void setCollegeMasterId(Long collegeMasterId) {
        this.collegeMasterId = collegeMasterId;
    }

    public String getCollegeMasterName() {
        return collegeMasterName;
    }

    public void setCollegeMasterName(String collegeMasterName) {
        this.collegeMasterName = collegeMasterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentMasterDTO)) {
            return false;
        }

        return id != null && id.equals(((DepartmentMasterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", collegeMasterId=" + getCollegeMasterId() +
            ", collegeMasterName='" + getCollegeMasterName() + "'" +
            "}";
    }
}
