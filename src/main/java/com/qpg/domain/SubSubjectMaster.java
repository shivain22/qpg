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
 * A SubSubjectMaster.
 */
@Entity
@Table(name = "sub_subject_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubSubjectMaster implements Serializable {

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
    @JsonIgnoreProperties(value = "subSubjectMasters", allowSetters = true)
    private SubjectMaster subjectMaster;

    @OneToMany(mappedBy = "subSubjectMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TopicMaster> topicMasters = new HashSet<>();

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

    public SubSubjectMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectMaster getSubjectMaster() {
        return subjectMaster;
    }

    public SubSubjectMaster subjectMaster(SubjectMaster subjectMaster) {
        this.subjectMaster = subjectMaster;
        return this;
    }

    public void setSubjectMaster(SubjectMaster subjectMaster) {
        this.subjectMaster = subjectMaster;
    }

    public Set<TopicMaster> getTopicMasters() {
        return topicMasters;
    }

    public SubSubjectMaster topicMasters(Set<TopicMaster> topicMasters) {
        this.topicMasters = topicMasters;
        return this;
    }

    public SubSubjectMaster addTopicMaster(TopicMaster topicMaster) {
        this.topicMasters.add(topicMaster);
        topicMaster.setSubSubjectMaster(this);
        return this;
    }

    public SubSubjectMaster removeTopicMaster(TopicMaster topicMaster) {
        this.topicMasters.remove(topicMaster);
        topicMaster.setSubSubjectMaster(null);
        return this;
    }

    public void setTopicMasters(Set<TopicMaster> topicMasters) {
        this.topicMasters = topicMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubSubjectMaster)) {
            return false;
        }
        return id != null && id.equals(((SubSubjectMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubSubjectMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
