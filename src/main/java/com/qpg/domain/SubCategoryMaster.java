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
 * A SubCategoryMaster.
 */
@Entity
@Table(name = "sub_category_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubCategoryMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "subCategoryMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SubjectMaster> subjectMasters = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "subCategoryMasters", allowSetters = true)
    private CategoryMaster categoryMaster;

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

    public SubCategoryMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubjectMaster> getSubjectMasters() {
        return subjectMasters;
    }

    public SubCategoryMaster subjectMasters(Set<SubjectMaster> subjectMasters) {
        this.subjectMasters = subjectMasters;
        return this;
    }

    public SubCategoryMaster addSubjectMaster(SubjectMaster subjectMaster) {
        this.subjectMasters.add(subjectMaster);
        subjectMaster.setSubCategoryMaster(this);
        return this;
    }

    public SubCategoryMaster removeSubjectMaster(SubjectMaster subjectMaster) {
        this.subjectMasters.remove(subjectMaster);
        subjectMaster.setSubCategoryMaster(null);
        return this;
    }

    public void setSubjectMasters(Set<SubjectMaster> subjectMasters) {
        this.subjectMasters = subjectMasters;
    }

    public CategoryMaster getCategoryMaster() {
        return categoryMaster;
    }

    public SubCategoryMaster categoryMaster(CategoryMaster categoryMaster) {
        this.categoryMaster = categoryMaster;
        return this;
    }

    public void setCategoryMaster(CategoryMaster categoryMaster) {
        this.categoryMaster = categoryMaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubCategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((SubCategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubCategoryMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
