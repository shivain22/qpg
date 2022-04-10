package com.qpg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A QuestionTypeMaster.
 */
@Entity
@Table(name = "question_type_category_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionTypeCategoryMaster implements Serializable {

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

    @OneToMany(mappedBy = "questionTypeMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<QuestionMaster> questionMasters = new HashSet<>();

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

    public QuestionTypeCategoryMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QuestionMaster> getQuestionMasters() {
        return questionMasters;
    }

    public QuestionTypeCategoryMaster questionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
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
        if (!(o instanceof QuestionTypeCategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((QuestionTypeCategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionTypeCategoryMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
