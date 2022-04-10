package com.qpg.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.qpg.domain.CourseMaster} entity.
 */
public class CourseMasterDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    private String name;


    private Long departmentMasterId;

    private String departmentMasterName;
    
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

    public Long getDepartmentMasterId() {
        return departmentMasterId;
    }

    public void setDepartmentMasterId(Long departmentMasterId) {
        this.departmentMasterId = departmentMasterId;
    }

    public String getDepartmentMasterName() {
        return departmentMasterName;
    }

    public void setDepartmentMasterName(String departmentMasterName) {
        this.departmentMasterName = departmentMasterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseMasterDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseMasterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", departmentMasterId=" + getDepartmentMasterId() +
            ", departmentMasterName='" + getDepartmentMasterName() + "'" +
            "}";
    }
}
