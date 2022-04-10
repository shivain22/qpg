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
 * Criteria class for the {@link com.qpg.domain.CourseMaster} entity. This class is used
 * in {@link com.qpg.web.rest.CourseMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter departmentMasterId;

    private LongFilter categoryMasterId;

    public CourseMasterCriteria() {
    }

    public CourseMasterCriteria(CourseMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.departmentMasterId = other.departmentMasterId == null ? null : other.departmentMasterId.copy();
        this.categoryMasterId = other.categoryMasterId == null ? null : other.categoryMasterId.copy();
    }

    @Override
    public CourseMasterCriteria copy() {
        return new CourseMasterCriteria(this);
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

    public LongFilter getDepartmentMasterId() {
        return departmentMasterId;
    }

    public void setDepartmentMasterId(LongFilter departmentMasterId) {
        this.departmentMasterId = departmentMasterId;
    }

    public LongFilter getCategoryMasterId() {
        return categoryMasterId;
    }

    public void setCategoryMasterId(LongFilter categoryMasterId) {
        this.categoryMasterId = categoryMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourseMasterCriteria that = (CourseMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(departmentMasterId, that.departmentMasterId) &&
            Objects.equals(categoryMasterId, that.categoryMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        departmentMasterId,
        categoryMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (departmentMasterId != null ? "departmentMasterId=" + departmentMasterId + ", " : "") +
                (categoryMasterId != null ? "categoryMasterId=" + categoryMasterId + ", " : "") +
            "}";
    }

}
