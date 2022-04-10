package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QuestionBluePrintDetail.
 */
@Entity
@Table(name = "question_blue_print_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionBluePrintDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "questionBluePrintDetails", allowSetters = true)
    private DifficultyTypeMaster difficultyTypeMaster;

    @NotNull
    @Column(name = "num_of_choices", nullable = false)
    private Integer numOfChoices;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "questionBluePrintDetails", allowSetters = true)
    private QuestionChoiceMaster questionChoiceMaster;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "questionBluePrintDetails", allowSetters = true)
    private QuestionTypeMaster questionTypeMaster;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "questionBluePrintDetails", allowSetters = true)
    private QuestionBluePrintMaster questionBluePrintMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public QuestionBluePrintDetail totalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
        return this;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public QuestionTypeMaster getQuestionTypeMaster() {
        return questionTypeMaster;
    }

    public QuestionBluePrintDetail questionTypeMaster(QuestionTypeMaster questionTypeMaster) {
        this.questionTypeMaster = questionTypeMaster;
        return this;
    }

    public void setQuestionTypeMaster(QuestionTypeMaster questionTypeMaster) {
        this.questionTypeMaster = questionTypeMaster;
    }

    public QuestionBluePrintMaster getQuestionBluePrintMaster() {
        return questionBluePrintMaster;
    }

    public QuestionBluePrintDetail questionBluePrintMaster(QuestionBluePrintMaster questionBluePrintMaster) {
        this.questionBluePrintMaster = questionBluePrintMaster;
        return this;
    }

    public void setQuestionBluePrintMaster(QuestionBluePrintMaster questionBluePrintMaster) {
        this.questionBluePrintMaster = questionBluePrintMaster;
    }

    public DifficultyTypeMaster getDifficultyTypeMaster() {
        return difficultyTypeMaster;
    }

    public void setDifficultyTypeMaster(DifficultyTypeMaster difficultyTypeMaster) {
        this.difficultyTypeMaster = difficultyTypeMaster;
    }

    public Integer getNumOfChoices() {
        return numOfChoices;
    }

    public void setNumOfChoices(Integer numOfChoices) {
        this.numOfChoices = numOfChoices;
    }

    public QuestionChoiceMaster getQuestionChoiceMaster() {
        return questionChoiceMaster;
    }

    public void setQuestionChoiceMaster(QuestionChoiceMaster questionChoiceMaster) {
        this.questionChoiceMaster = questionChoiceMaster;
    }
// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionBluePrintDetail)) {
            return false;
        }
        return id != null && id.equals(((QuestionBluePrintDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionBluePrintDetail{" +
            "id=" + getId() +
            ", totalQuestions=" + getTotalQuestions() +
            "}";
    }
}
