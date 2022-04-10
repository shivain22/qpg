package com.qpg.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.qpg.domain.SubCategoryMaster} entity. This class is used
 * in {@link com.qpg.web.rest.SubCategoryMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-category-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExamQuestionPaperDetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter questionMasterId;

    private LongFilter examQuesitonPaperMasterId;

    private StringFilter shortName;

    public ExamQuestionPaperDetailCriteria() {
    }

    public ExamQuestionPaperDetailCriteria(ExamQuestionPaperDetailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.questionMasterId = other.questionMasterId == null ? null : other.questionMasterId.copy();
        this.examQuesitonPaperMasterId = other.examQuesitonPaperMasterId == null ? null : other.examQuesitonPaperMasterId.copy();
    }

    @Override
    public ExamQuestionPaperDetailCriteria copy() {
        return new ExamQuestionPaperDetailCriteria(this);
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

    public LongFilter getSubjectMasterId() {
        return questionMasterId;
    }

    public void setSubjectMasterId(LongFilter subjectMasterId) {
        this.questionMasterId = subjectMasterId;
    }

    public LongFilter getCategoryMasterId() {
        return examQuesitonPaperMasterId;
    }

    public void setCategoryMasterId(LongFilter categoryMasterId) {
        this.examQuesitonPaperMasterId = categoryMasterId;
    }

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExamQuestionPaperDetailCriteria that = (ExamQuestionPaperDetailCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(questionMasterId, that.questionMasterId) &&
            Objects.equals(examQuesitonPaperMasterId, that.examQuesitonPaperMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        questionMasterId,
        examQuesitonPaperMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubCategoryMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (questionMasterId != null ? "subjectMasterId=" + questionMasterId + ", " : "") +
                (examQuesitonPaperMasterId != null ? "examQuesitonPaperMasterId=" + examQuesitonPaperMasterId + ", " : "") +
            "}";
    }

}
