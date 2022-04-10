package com.qpg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CollegeMaster.
 */
@Entity
@Table(name = "college_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CollegeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "name", length = 500, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "collegeMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DepartmentMaster> departmentMasters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CollegeMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DepartmentMaster> getDepartmentMasters() {
        return departmentMasters;
    }

    public CollegeMaster departmentMasters(Set<DepartmentMaster> departmentMasters) {
        this.departmentMasters = departmentMasters;
        return this;
    }

    public CollegeMaster addDepartmentMaster(DepartmentMaster departmentMaster) {
        this.departmentMasters.add(departmentMaster);
        departmentMaster.setCollegeMaster(this);
        return this;
    }

    public CollegeMaster removeDepartmentMaster(DepartmentMaster departmentMaster) {
        this.departmentMasters.remove(departmentMaster);
        departmentMaster.setCollegeMaster(null);
        return this;
    }

    public void setDepartmentMasters(Set<DepartmentMaster> departmentMasters) {
        this.departmentMasters = departmentMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollegeMaster)) {
            return false;
        }
        return id != null && id.equals(((CollegeMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollegeMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
