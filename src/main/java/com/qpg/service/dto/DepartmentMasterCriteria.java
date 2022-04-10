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
 * Criteria class for the {@link com.qpg.domain.DepartmentMaster} entity. This class is used
 * in {@link com.qpg.web.rest.DepartmentMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /department-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartmentMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter collegeMasterId;

    private LongFilter courseMasterId;

    public DepartmentMasterCriteria() {
    }

    public DepartmentMasterCriteria(DepartmentMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.collegeMasterId = other.collegeMasterId == null ? null : other.collegeMasterId.copy();
        this.courseMasterId = other.courseMasterId == null ? null : other.courseMasterId.copy();
    }

    @Override
    public DepartmentMasterCriteria copy() {
        return new DepartmentMasterCriteria(this);
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

    public LongFilter getCollegeMasterId() {
        return collegeMasterId;
    }

    public void setCollegeMasterId(LongFilter collegeMasterId) {
        this.collegeMasterId = collegeMasterId;
    }

    public LongFilter getCourseMasterId() {
        return courseMasterId;
    }

    public void setCourseMasterId(LongFilter courseMasterId) {
        this.courseMasterId = courseMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepartmentMasterCriteria that = (DepartmentMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(collegeMasterId, that.collegeMasterId) &&
            Objects.equals(courseMasterId, that.courseMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        collegeMasterId,
        courseMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (collegeMasterId != null ? "collegeMasterId=" + collegeMasterId + ", " : "") +
                (courseMasterId != null ? "courseMasterId=" + courseMasterId + ", " : "") +
            "}";
    }

}
