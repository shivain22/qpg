package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SubjectMaster.
 */
@Entity
@Table(name = "subject_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubjectMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "subjectMasters", allowSetters = true)
    private SubCategoryMaster subCategoryMaster;

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

    public SubjectMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubCategoryMaster getSubCategoryMaster() {
        return subCategoryMaster;
    }

    public SubjectMaster subCategoryMaster(SubCategoryMaster subCategoryMaster) {
        this.subCategoryMaster = subCategoryMaster;
        return this;
    }

    public void setSubCategoryMaster(SubCategoryMaster subCategoryMaster) {
        this.subCategoryMaster = subCategoryMaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubjectMaster)) {
            return false;
        }
        return id != null && id.equals(((SubjectMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
