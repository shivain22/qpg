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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.qpg.domain.ExamMaster} entity. This class is used
 * in {@link com.qpg.web.rest.ExamMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exam-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExamMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter startDate;

    private LongFilter questionBluePrintMasterId;

    public ExamMasterCriteria() {
    }

    public ExamMasterCriteria(ExamMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.questionBluePrintMasterId = other.questionBluePrintMasterId == null ? null : other.questionBluePrintMasterId.copy();
    }

    @Override
    public ExamMasterCriteria copy() {
        return new ExamMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
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
        final ExamMasterCriteria that = (ExamMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(questionBluePrintMasterId, that.questionBluePrintMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        startDate,
        questionBluePrintMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (questionBluePrintMasterId != null ? "questionBluePrintMasterId=" + questionBluePrintMasterId + ", " : "") +
            "}";
    }

}
