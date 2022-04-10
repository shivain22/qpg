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
 * Criteria class for the {@link com.qpg.domain.SubCategoryMaster} entity. This class is used
 * in {@link com.qpg.web.rest.SubCategoryMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-category-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubCategoryMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter subjectMasterId;

    private LongFilter categoryMasterId;

    private StringFilter shortName;

    public SubCategoryMasterCriteria() {
    }

    public SubCategoryMasterCriteria(SubCategoryMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.subjectMasterId = other.subjectMasterId == null ? null : other.subjectMasterId.copy();
        this.categoryMasterId = other.categoryMasterId == null ? null : other.categoryMasterId.copy();
    }

    @Override
    public SubCategoryMasterCriteria copy() {
        return new SubCategoryMasterCriteria(this);
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
        return subjectMasterId;
    }

    public void setSubjectMasterId(LongFilter subjectMasterId) {
        this.subjectMasterId = subjectMasterId;
    }

    public LongFilter getCategoryMasterId() {
        return categoryMasterId;
    }

    public void setCategoryMasterId(LongFilter categoryMasterId) {
        this.categoryMasterId = categoryMasterId;
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
        final SubCategoryMasterCriteria that = (SubCategoryMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(subjectMasterId, that.subjectMasterId) &&
            Objects.equals(categoryMasterId, that.categoryMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        subjectMasterId,
        categoryMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubCategoryMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (subjectMasterId != null ? "subjectMasterId=" + subjectMasterId + ", " : "") +
                (categoryMasterId != null ? "categoryMasterId=" + categoryMasterId + ", " : "") +
            "}";
    }

}
