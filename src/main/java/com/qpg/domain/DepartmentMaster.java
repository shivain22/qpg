package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DepartmentMaster.
 */
@Entity
@Table(name = "department_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DepartmentMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "name", length = 500, nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "departmentMasters", allowSetters = true)
    private CollegeMaster collegeMaster;

    @OneToMany(mappedBy = "departmentMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CourseMaster> courseMasters = new HashSet<>();

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

    public DepartmentMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CollegeMaster getCollegeMaster() {
        return collegeMaster;
    }

    public DepartmentMaster collegeMaster(CollegeMaster collegeMaster) {
        this.collegeMaster = collegeMaster;
        return this;
    }

    public void setCollegeMaster(CollegeMaster collegeMaster) {
        this.collegeMaster = collegeMaster;
    }

    public Set<CourseMaster> getCourseMasters() {
        return courseMasters;
    }

    public DepartmentMaster courseMasters(Set<CourseMaster> courseMasters) {
        this.courseMasters = courseMasters;
        return this;
    }

    public DepartmentMaster addCourseMaster(CourseMaster courseMaster) {
        this.courseMasters.add(courseMaster);
        courseMaster.setDepartmentMaster(this);
        return this;
    }

    public DepartmentMaster removeCourseMaster(CourseMaster courseMaster) {
        this.courseMasters.remove(courseMaster);
        courseMaster.setDepartmentMaster(null);
        return this;
    }

    public void setCourseMasters(Set<CourseMaster> courseMasters) {
        this.courseMasters = courseMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentMaster)) {
            return false;
        }
        return id != null && id.equals(((DepartmentMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
