package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TopicMaster.
 */
@Entity
@Table(name = "topic_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TopicMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "short_code", length = 50, nullable = false)
    private String shortCode;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "topicMasters", allowSetters = true)
    private SubSubjectMaster subSubjectMaster;

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

    public TopicMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubSubjectMaster getSubSubjectMaster() {
        return subSubjectMaster;
    }

    public TopicMaster subSubjectMaster(SubSubjectMaster subSubjectMaster) {
        this.subSubjectMaster = subSubjectMaster;
        return this;
    }

    public void setSubSubjectMaster(SubSubjectMaster subSubjectMaster) {
        this.subSubjectMaster = subSubjectMaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicMaster)) {
            return false;
        }
        return id != null && id.equals(((TopicMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
