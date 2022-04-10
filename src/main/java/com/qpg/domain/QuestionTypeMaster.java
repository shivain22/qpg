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
 * A QuestionTypeMaster.
 */
@Entity
@Table(name = "question_type_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionTypeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "short_name", length = 50, nullable = false, unique = true)
    private String shortName;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "default_weightage", length = 50, nullable = false, unique = true)
    private Double defaultWeightage;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "default_duration", length = 50, nullable = false, unique = true)
    private Double defaultDuration;

    @OneToMany(mappedBy = "questionTypeMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<QuestionMaster> questionMasters = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = "subCategoryMasters", allowSetters = true)
    private QuestionTypeCategoryMaster questionTypeCategoryMaster;

    public QuestionTypeCategoryMaster getQuestionTypeCategoryMaster() {
        return questionTypeCategoryMaster;
    }

    public Double getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(Double defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public void setQuestionTypeCategoryMaster(QuestionTypeCategoryMaster questionTypeCategoryMaster) {
        this.questionTypeCategoryMaster = questionTypeCategoryMaster;
    }
// jhipster-needle-entity-add-field - JHipster will add fields here

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public QuestionTypeMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QuestionMaster> getQuestionMasters() {
        return questionMasters;
    }

    public QuestionTypeMaster questionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
        return this;
    }

    public QuestionTypeMaster addQuestionMaster(QuestionMaster questionMaster) {
        this.questionMasters.add(questionMaster);
        questionMaster.setQuestionTypeMaster(this);
        return this;
    }

    public QuestionTypeMaster removeQuestionMaster(QuestionMaster questionMaster) {
        this.questionMasters.remove(questionMaster);
        questionMaster.setQuestionTypeMaster(null);
        return this;
    }

    public void setQuestionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
    }

    public Double getDefaultWeightage() {
        return defaultWeightage;
    }

    public void setDefaultWeightage(Double defaultWeightage) {
        this.defaultWeightage = defaultWeightage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionTypeMaster)) {
            return false;
        }
        return id != null && id.equals(((QuestionTypeMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionTypeMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
