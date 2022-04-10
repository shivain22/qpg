package com.qpg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DifficultyTypeMaster.
 */
@Entity
@Table(name = "difficulty_type_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DifficultyTypeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "difficultyTypeMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<QuestionMaster> questionMasters = new HashSet<>();

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

    public DifficultyTypeMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QuestionMaster> getQuestionMasters() {
        return questionMasters;
    }

    public DifficultyTypeMaster questionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
        return this;
    }

    public DifficultyTypeMaster addQuestionMaster(QuestionMaster questionMaster) {
        this.questionMasters.add(questionMaster);
        questionMaster.setDifficultyTypeMaster(this);
        return this;
    }

    public DifficultyTypeMaster removeQuestionMaster(QuestionMaster questionMaster) {
        this.questionMasters.remove(questionMaster);
        questionMaster.setDifficultyTypeMaster(null);
        return this;
    }

    public void setQuestionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DifficultyTypeMaster)) {
            return false;
        }
        return id != null && id.equals(((DifficultyTypeMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DifficultyTypeMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
