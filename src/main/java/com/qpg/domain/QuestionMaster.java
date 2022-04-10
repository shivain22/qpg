package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A QuestionMaster.
 */
@Entity
@Table(name = "question_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "weightage")
    private Double weightage;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "questionMasters", allowSetters = true)
    private QuestionTypeMaster questionTypeMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = "questionMasters", allowSetters = true)
    private DifficultyTypeMaster difficultyTypeMaster;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "questionMasters", allowSetters = true)
    private SubTopicMaster subTopicMaster;

    @ManyToOne
    private QuestionMaster parentQuestionMaster;

    @OneToMany(mappedBy = "parentQuestionMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<QuestionMaster> questionMasters = new HashSet<>();

    @OneToMany(mappedBy = "questionMaster")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Fetch(FetchMode.JOIN)
    private Set<AnswerMaster> answerMasters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public QuestionMaster text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getWeightage() {
        return weightage;
    }

    public QuestionMaster weightage(Double weightage) {
        this.weightage = weightage;
        return this;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }

    public QuestionTypeMaster getQuestionTypeMaster() {
        return questionTypeMaster;
    }

    public QuestionMaster questionTypeMaster(QuestionTypeMaster questionTypeMaster) {
        this.questionTypeMaster = questionTypeMaster;
        return this;
    }

    public void setQuestionTypeMaster(QuestionTypeMaster questionTypeMaster) {
        this.questionTypeMaster = questionTypeMaster;
    }

    public DifficultyTypeMaster getDifficultyTypeMaster() {
        return difficultyTypeMaster;
    }

    public QuestionMaster difficultyTypeMaster(DifficultyTypeMaster difficultyTypeMaster) {
        this.difficultyTypeMaster = difficultyTypeMaster;
        return this;
    }

    public void setDifficultyTypeMaster(DifficultyTypeMaster difficultyTypeMaster) {
        this.difficultyTypeMaster = difficultyTypeMaster;
    }

    public SubTopicMaster getSubTopicMaster() {
        return subTopicMaster;
    }

    public QuestionMaster subTopicMaster(SubTopicMaster subTopicMaster) {
        this.subTopicMaster = subTopicMaster;
        return this;
    }

    public void setSubTopicMaster(SubTopicMaster subTopicMaster) {
        this.subTopicMaster = subTopicMaster;
    }

    public QuestionMaster getParentQuestionMaster() {
        return parentQuestionMaster;
    }

    public QuestionMaster parentQuestionMaster(QuestionMaster questionMaster) {
        this.parentQuestionMaster = questionMaster;
        return this;
    }

    public void setParentQuestionMaster(QuestionMaster questionMaster) {
        this.parentQuestionMaster = questionMaster;
    }

    public Set<QuestionMaster> getQuestionMasters() {
        return questionMasters;
    }

    public QuestionMaster questionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
        return this;
    }

    public QuestionMaster addQuestionMaster(QuestionMaster questionMaster) {
        this.questionMasters.add(questionMaster);
        questionMaster.setParentQuestionMaster(this);
        return this;
    }

    public QuestionMaster removeQuestionMaster(QuestionMaster questionMaster) {
        this.questionMasters.remove(questionMaster);
        questionMaster.setParentQuestionMaster(null);
        return this;
    }

    public void setQuestionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
    }

    public Set<AnswerMaster> getAnswerMasters() {
        return answerMasters;
    }

    public QuestionMaster answerMasters(Set<AnswerMaster> answerMasters) {
        this.answerMasters = answerMasters;
        return this;
    }

    public QuestionMaster addAnswerMaster(AnswerMaster answerMaster) {
        this.answerMasters.add(answerMaster);
        answerMaster.setQuestionMaster(this);
        return this;
    }

    public QuestionMaster removeAnswerMaster(AnswerMaster answerMaster) {
        this.answerMasters.remove(answerMaster);
        answerMaster.setQuestionMaster(null);
        return this;
    }

    public void setAnswerMasters(Set<AnswerMaster> answerMasters) {
        this.answerMasters = answerMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionMaster)) {
            return false;
        }
        return id != null && id.equals(((QuestionMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionMaster{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", weightage=" + getWeightage() +
            "}";
    }
}
