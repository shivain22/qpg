package com.qpg.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.qpg.domain.CategoryMaster} entity. This class is used
 * in {@link com.qpg.web.rest.CategoryMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /category-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExamQuestionPaperMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter examMasterId;

    public ExamQuestionPaperMasterCriteria() {
    }

    public ExamQuestionPaperMasterCriteria(ExamQuestionPaperMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.examMasterId = other.examMasterId == null ? null : other.examMasterId.copy();
    }

    @Override
    public ExamQuestionPaperMasterCriteria copy() {
        return new ExamQuestionPaperMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getSubCategoryMasterId() {
        return examMasterId;
    }

    public void setExamMasterId(LongFilter examMasterId) {
        this.examMasterId = examMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExamQuestionPaperMasterCriteria that = (ExamQuestionPaperMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(examMasterId, that.examMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
            examMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (examMasterId != null ? "examMasterId=" + examMasterId + ", " : "") +
            "}";
    }

}
