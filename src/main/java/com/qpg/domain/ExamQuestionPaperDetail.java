package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A SubExamQuestionPaperMaster.
 */
@Entity
@Table(name = "exam_question_paper_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamQuestionPaperDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    @NotNull
    private QuestionMaster questionMaster;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "subExamQuestionPaperDetails", allowSetters = true)
    private ExamQuestionPaperMaster examQuestionPaperMaster;

    @Transient
    private String choice;

    @Transient
    private QuestionMaster choiceEachQuestionMaster;

    @Transient
    private List<QuestionMaster> comboQuestions;

    @Transient
    private List<QuestionMaster> comboChoiceQuestions;

    public List<QuestionMaster> getComboChoiceQuestions() {
        return comboChoiceQuestions;
    }

    public void setComboChoiceQuestions(List<QuestionMaster> comboChoiceQuestions) {
        this.comboChoiceQuestions = comboChoiceQuestions;
    }

    public List<QuestionMaster> getComboQuestions() {
        return comboQuestions;
    }

    public void setComboQuestions(List<QuestionMaster> comboQuestions) {
        this.comboQuestions = comboQuestions;
    }

    public QuestionMaster getChoiceEachQuestionMaster() {
        return choiceEachQuestionMaster;
    }

    public void setChoiceEachQuestionMaster(QuestionMaster choiceEachQuestionMaster) {
        this.choiceEachQuestionMaster = choiceEachQuestionMaster;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ExamQuestionPaperDetail name(String name) {
        return this;
    }
    public QuestionMaster getQuestionMaster() {
        return this.questionMaster;
    }

    public ExamQuestionPaperDetail questionMaster(QuestionMaster questionMasters) {
        this.questionMaster = questionMasters;
        return this;
    }

    public void setQuestionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
    }

    public ExamQuestionPaperMaster getExamQuestionPaperMaster() {
        return examQuestionPaperMaster;
    }

    public ExamQuestionPaperDetail examQuestionPaperMaster(ExamQuestionPaperMaster examQuestionPaperMaster) {
        this.examQuestionPaperMaster = examQuestionPaperMaster;
        return this;
    }

    public void setExamQuestionPaperMaster(ExamQuestionPaperMaster examQuestionPaperMaster) {
        this.examQuestionPaperMaster = examQuestionPaperMaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamQuestionPaperDetail)) {
            return false;
        }
        return id != null && id.equals(((ExamQuestionPaperDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamQuestionPaperDetail{" +
            "id=" + getId() +
            ", name=''" +
            "}";
    }
}
