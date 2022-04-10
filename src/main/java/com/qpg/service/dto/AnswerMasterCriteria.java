package com.qpg.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.qpg.domain.AnswerMaster} entity. This class is used
 * in {@link com.qpg.web.rest.AnswerMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /answer-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter text;

    private BooleanFilter correct;

    private LongFilter questionMasterId;

    public AnswerMasterCriteria() {
    }

    public AnswerMasterCriteria(AnswerMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.correct = other.correct == null ? null : other.correct.copy();
        this.questionMasterId = other.questionMasterId == null ? null : other.questionMasterId.copy();
    }

    @Override
    public AnswerMasterCriteria copy() {
        return new AnswerMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public BooleanFilter getCorrect() {
        return correct;
    }

    public void setCorrect(BooleanFilter correct) {
        this.correct = correct;
    }

    public LongFilter getQuestionMasterId() {
        return questionMasterId;
    }

    public void setQuestionMasterId(LongFilter questionMasterId) {
        this.questionMasterId = questionMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerMasterCriteria that = (AnswerMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(text, that.text) &&
            Objects.equals(correct, that.correct) &&
            Objects.equals(questionMasterId, that.questionMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        text,
        correct,
        questionMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (correct != null ? "correct=" + correct + ", " : "") +
                (questionMasterId != null ? "questionMasterId=" + questionMasterId + ", " : "") +
            "}";
    }

}
