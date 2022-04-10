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
 * Criteria class for the {@link com.qpg.domain.QbMaster} entity. This class is used
 * in {@link com.qpg.web.rest.QbMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /qb-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QbMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter collegeMaster;

    private StringFilter departmentMaster;

    private StringFilter courseMaster;

    private StringFilter categoryMaster;

    private StringFilter subCategoryMaster;

    private StringFilter subjectMaster;

    private StringFilter subSubjectMaster;

    private StringFilter topicMaster;

    private StringFilter subTopicMaster;

    public QbMasterCriteria() {
    }

    public QbMasterCriteria(QbMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.collegeMaster = other.collegeMaster == null ? null : other.collegeMaster.copy();
        this.departmentMaster = other.departmentMaster == null ? null : other.departmentMaster.copy();
        this.courseMaster = other.courseMaster == null ? null : other.courseMaster.copy();
        this.categoryMaster = other.categoryMaster == null ? null : other.categoryMaster.copy();
        this.subCategoryMaster = other.subCategoryMaster == null ? null : other.subCategoryMaster.copy();
        this.subjectMaster = other.subjectMaster == null ? null : other.subjectMaster.copy();
        this.subSubjectMaster = other.subSubjectMaster == null ? null : other.subSubjectMaster.copy();
        this.topicMaster = other.topicMaster == null ? null : other.topicMaster.copy();
        this.subTopicMaster = other.subTopicMaster == null ? null : other.subTopicMaster.copy();
    }

    @Override
    public QbMasterCriteria copy() {
        return new QbMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCollegeMaster() {
        return collegeMaster;
    }

    public void setCollegeMaster(StringFilter collegeMaster) {
        this.collegeMaster = collegeMaster;
    }

    public StringFilter getDepartmentMaster() {
        return departmentMaster;
    }

    public void setDepartmentMaster(StringFilter departmentMaster) {
        this.departmentMaster = departmentMaster;
    }

    public StringFilter getCourseMaster() {
        return courseMaster;
    }

    public void setCourseMaster(StringFilter courseMaster) {
        this.courseMaster = courseMaster;
    }

    public StringFilter getCategoryMaster() {
        return categoryMaster;
    }

    public void setCategoryMaster(StringFilter categoryMaster) {
        this.categoryMaster = categoryMaster;
    }

    public StringFilter getSubCategoryMaster() {
        return subCategoryMaster;
    }

    public void setSubCategoryMaster(StringFilter subCategoryMaster) {
        this.subCategoryMaster = subCategoryMaster;
    }

    public StringFilter getSubjectMaster() {
        return subjectMaster;
    }

    public void setSubjectMaster(StringFilter subjectMaster) {
        this.subjectMaster = subjectMaster;
    }

    public StringFilter getSubSubjectMaster() {
        return subSubjectMaster;
    }

    public void setSubSubjectMaster(StringFilter subSubjectMaster) {
        this.subSubjectMaster = subSubjectMaster;
    }

    public StringFilter getTopicMaster() {
        return topicMaster;
    }

    public void setTopicMaster(StringFilter topicMaster) {
        this.topicMaster = topicMaster;
    }

    public StringFilter getSubTopicMaster() {
        return subTopicMaster;
    }

    public void setSubTopicMaster(StringFilter subTopicMaster) {
        this.subTopicMaster = subTopicMaster;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QbMasterCriteria that = (QbMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(collegeMaster, that.collegeMaster) &&
            Objects.equals(departmentMaster, that.departmentMaster) &&
            Objects.equals(courseMaster, that.courseMaster) &&
            Objects.equals(categoryMaster, that.categoryMaster) &&
            Objects.equals(subCategoryMaster, that.subCategoryMaster) &&
            Objects.equals(subjectMaster, that.subjectMaster) &&
            Objects.equals(subSubjectMaster, that.subSubjectMaster) &&
            Objects.equals(topicMaster, that.topicMaster) &&
            Objects.equals(subTopicMaster, that.subTopicMaster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        collegeMaster,
        departmentMaster,
        courseMaster,
        categoryMaster,
        subCategoryMaster,
        subjectMaster,
        subSubjectMaster,
        topicMaster,
        subTopicMaster
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QbMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (collegeMaster != null ? "collegeMaster=" + collegeMaster + ", " : "") +
                (departmentMaster != null ? "departmentMaster=" + departmentMaster + ", " : "") +
                (courseMaster != null ? "courseMaster=" + courseMaster + ", " : "") +
                (categoryMaster != null ? "categoryMaster=" + categoryMaster + ", " : "") +
                (subCategoryMaster != null ? "subCategoryMaster=" + subCategoryMaster + ", " : "") +
                (subjectMaster != null ? "subjectMaster=" + subjectMaster + ", " : "") +
                (subSubjectMaster != null ? "subSubjectMaster=" + subSubjectMaster + ", " : "") +
                (topicMaster != null ? "topicMaster=" + topicMaster + ", " : "") +
                (subTopicMaster != null ? "subTopicMaster=" + subTopicMaster + ", " : "") +
            "}";
    }

}
