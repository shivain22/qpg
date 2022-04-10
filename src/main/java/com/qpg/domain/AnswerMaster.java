package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AnswerMaster.
 */
@Entity
@Table(name = "answer_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnswerMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull
    @Column(name = "correct", nullable = false)
    private Boolean correct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "answerMasters", allowSetters = true)
    private QuestionMaster questionMaster;

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

    public AnswerMaster text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public AnswerMaster correct(Boolean correct) {
        this.correct = correct;
        return this;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public QuestionMaster getQuestionMaster() {
        return questionMaster;
    }

    public AnswerMaster questionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
        return this;
    }

    public void setQuestionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerMaster)) {
            return false;
        }
        return id != null && id.equals(((AnswerMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerMaster{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", correct='" + isCorrect() + "'" +
            "}";
    }
}
