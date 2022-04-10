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
 * Criteria class for the {@link com.qpg.domain.QuestionMaster} entity. This class is used
 * in {@link com.qpg.web.rest.QuestionMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter text;

    private DoubleFilter weightage;

    private LongFilter questionTypeMasterId;

    private LongFilter difficultyTypeMasterId;

    private LongFilter subTopicMasterId;

    private LongFilter parentQuestionMasterId;

    private LongFilter questionMasterId;

    private LongFilter answerMasterId;

    public QuestionMasterCriteria() {
    }

    public QuestionMasterCriteria(QuestionMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.weightage = other.weightage == null ? null : other.weightage.copy();
        this.questionTypeMasterId = other.questionTypeMasterId == null ? null : other.questionTypeMasterId.copy();
        this.difficultyTypeMasterId = other.difficultyTypeMasterId == null ? null : other.difficultyTypeMasterId.copy();
        this.subTopicMasterId = other.subTopicMasterId == null ? null : other.subTopicMasterId.copy();
        this.parentQuestionMasterId = other.parentQuestionMasterId == null ? null : other.parentQuestionMasterId.copy();
        this.questionMasterId = other.questionMasterId == null ? null : other.questionMasterId.copy();
        this.answerMasterId = other.answerMasterId == null ? null : other.answerMasterId.copy();
    }

    @Override
    public QuestionMasterCriteria copy() {
        return new QuestionMasterCriteria(this);
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

    public DoubleFilter getWeightage() {
        return weightage;
    }

    public void setWeightage(DoubleFilter weightage) {
        this.weightage = weightage;
    }

    public LongFilter getQuestionTypeMasterId() {
        return questionTypeMasterId;
    }

    public void setQuestionTypeMasterId(LongFilter questionTypeMasterId) {
        this.questionTypeMasterId = questionTypeMasterId;
    }

    public LongFilter getDifficultyTypeMasterId() {
        return difficultyTypeMasterId;
    }

    public void setDifficultyTypeMasterId(LongFilter difficultyTypeMasterId) {
        this.difficultyTypeMasterId = difficultyTypeMasterId;
    }

    public LongFilter getSubTopicMasterId() {
        return subTopicMasterId;
    }

    public void setSubTopicMasterId(LongFilter subTopicMasterId) {
        this.subTopicMasterId = subTopicMasterId;
    }

    public LongFilter getParentQuestionMasterId() {
        return parentQuestionMasterId;
    }

    public void setParentQuestionMasterId(LongFilter parentQuestionMasterId) {
        this.parentQuestionMasterId = parentQuestionMasterId;
    }

    public LongFilter getQuestionMasterId() {
        return questionMasterId;
    }

    public void setQuestionMasterId(LongFilter questionMasterId) {
        this.questionMasterId = questionMasterId;
    }

    public LongFilter getAnswerMasterId() {
        return answerMasterId;
    }

    public void setAnswerMasterId(LongFilter answerMasterId) {
        this.answerMasterId = answerMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionMasterCriteria that = (QuestionMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(text, that.text) &&
            Objects.equals(weightage, that.weightage) &&
            Objects.equals(questionTypeMasterId, that.questionTypeMasterId) &&
            Objects.equals(difficultyTypeMasterId, that.difficultyTypeMasterId) &&
            Objects.equals(subTopicMasterId, that.subTopicMasterId) &&
            Objects.equals(parentQuestionMasterId, that.parentQuestionMasterId) &&
            Objects.equals(questionMasterId, that.questionMasterId) &&
            Objects.equals(answerMasterId, that.answerMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        text,
        weightage,
        questionTypeMasterId,
        difficultyTypeMasterId,
        subTopicMasterId,
        parentQuestionMasterId,
        questionMasterId,
        answerMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (weightage != null ? "weightage=" + weightage + ", " : "") +
                (questionTypeMasterId != null ? "questionTypeMasterId=" + questionTypeMasterId + ", " : "") +
                (difficultyTypeMasterId != null ? "difficultyTypeMasterId=" + difficultyTypeMasterId + ", " : "") +
                (subTopicMasterId != null ? "subTopicMasterId=" + subTopicMasterId + ", " : "") +
                (parentQuestionMasterId != null ? "parentQuestionMasterId=" + parentQuestionMasterId + ", " : "") +
                (questionMasterId != null ? "questionMasterId=" + questionMasterId + ", " : "") +
                (answerMasterId != null ? "answerMasterId=" + answerMasterId + ", " : "") +
            "}";
    }

}
