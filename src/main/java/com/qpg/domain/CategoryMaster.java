package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoryMaster.
 */
@Entity
@Table(name = "category_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategoryMaster  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "categoryMasters", allowSetters = true)
    private CourseMaster courseMaster;

    @OneToMany(mappedBy = "categoryMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SubCategoryMaster> subCategoryMasters = new HashSet<>();

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

    public CategoryMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubCategoryMaster> getSubCategoryMasters() {
        return subCategoryMasters;
    }

    public CategoryMaster subCategoryMasters(Set<SubCategoryMaster> subCategoryMasters) {
        this.subCategoryMasters = subCategoryMasters;
        return this;
    }

    public CategoryMaster addSubCategoryMaster(SubCategoryMaster subCategoryMaster) {
        this.subCategoryMasters.add(subCategoryMaster);
        subCategoryMaster.setCategoryMaster(this);
        return this;
    }

    public CategoryMaster removeSubCategoryMaster(SubCategoryMaster subCategoryMaster) {
        this.subCategoryMasters.remove(subCategoryMaster);
        subCategoryMaster.setCategoryMaster(null);
        return this;
    }

    public void setSubCategoryMasters(Set<SubCategoryMaster> subCategoryMasters) {
        this.subCategoryMasters = subCategoryMasters;
    }

    public CourseMaster getCourseMaster() {
        return courseMaster;
    }

    public void setCourseMaster(CourseMaster courseMaster) {
        this.courseMaster = courseMaster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((CategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
