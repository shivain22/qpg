package com.qpg.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.qpg.domain.QuestionBluePrintDetail;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link QuestionBluePrintDetail} entity. This class is used
 * in {@link com.qpg.web.rest.QuestionBluePrintDetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-blue-print-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionBluePrintDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter totalQuestions;

    private LongFilter questionTypeMasterId;

    private LongFilter questionBluePrintMasterId;

    public QuestionBluePrintDetailsCriteria() {
    }

    public QuestionBluePrintDetailsCriteria(QuestionBluePrintDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalQuestions = other.totalQuestions == null ? null : other.totalQuestions.copy();
        this.questionTypeMasterId = other.questionTypeMasterId == null ? null : other.questionTypeMasterId.copy();
        this.questionBluePrintMasterId = other.questionBluePrintMasterId == null ? null : other.questionBluePrintMasterId.copy();
    }

    @Override
    public QuestionBluePrintDetailsCriteria copy() {
        return new QuestionBluePrintDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(IntegerFilter totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public LongFilter getQuestionTypeMasterId() {
        return questionTypeMasterId;
    }

    public void setQuestionTypeMasterId(LongFilter questionTypeMasterId) {
        this.questionTypeMasterId = questionTypeMasterId;
    }

    public LongFilter getQuestionBluePrintMasterId() {
        return questionBluePrintMasterId;
    }

    public void setQuestionBluePrintMasterId(LongFilter questionBluePrintMasterId) {
        this.questionBluePrintMasterId = questionBluePrintMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionBluePrintDetailsCriteria that = (QuestionBluePrintDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalQuestions, that.totalQuestions) &&
            Objects.equals(questionTypeMasterId, that.questionTypeMasterId) &&
            Objects.equals(questionBluePrintMasterId, that.questionBluePrintMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalQuestions,
        questionTypeMasterId,
        questionBluePrintMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionBluePrintDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalQuestions != null ? "totalQuestions=" + totalQuestions + ", " : "") +
                (questionTypeMasterId != null ? "questionTypeMasterId=" + questionTypeMasterId + ", " : "") +
                (questionBluePrintMasterId != null ? "questionBluePrintMasterId=" + questionBluePrintMasterId + ", " : "") +
            "}";
    }

}
