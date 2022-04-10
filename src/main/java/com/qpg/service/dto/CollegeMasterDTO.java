package com.qpg.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.qpg.domain.CollegeMaster} entity.
 */
public class CollegeMasterDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    private String name;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollegeMasterDTO)) {
            return false;
        }

        return id != null && id.equals(((CollegeMasterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollegeMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
