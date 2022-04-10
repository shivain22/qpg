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
 * A CourseMaster.
 */
@Entity
@Table(name = "course_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CourseMaster implements Serializable {

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
    @JsonIgnoreProperties(value = "courseMasters", allowSetters = true)
    private DepartmentMaster departmentMaster;

    @OneToMany(mappedBy = "courseMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CategoryMaster> categoryMasters = new HashSet<>();

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

    public CourseMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentMaster getDepartmentMaster() {
        return departmentMaster;
    }

    public CourseMaster departmentMaster(DepartmentMaster departmentMaster) {
        this.departmentMaster = departmentMaster;
        return this;
    }

    public void setDepartmentMaster(DepartmentMaster departmentMaster) {
        this.departmentMaster = departmentMaster;
    }

    public Set<CategoryMaster> getCategoryMasters() {
        return categoryMasters;
    }

    public CourseMaster categoryMasters(Set<CategoryMaster> categoryMasters) {
        this.categoryMasters = categoryMasters;
        return this;
    }

    public CourseMaster addCategoryMaster(CategoryMaster categoryMaster) {
        this.categoryMasters.add(categoryMaster);
        categoryMaster.setCourseMaster(this);
        return this;
    }

    public CourseMaster removeCategoryMaster(CategoryMaster categoryMaster) {
        this.categoryMasters.remove(categoryMaster);
        categoryMaster.setCourseMaster(null);
        return this;
    }

    public void setCategoryMasters(Set<CategoryMaster> categoryMasters) {
        this.categoryMasters = categoryMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseMaster)) {
            return false;
        }
        return id != null && id.equals(((CourseMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
