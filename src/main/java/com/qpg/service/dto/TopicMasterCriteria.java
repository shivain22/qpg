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
 * Criteria class for the {@link com.qpg.domain.TopicMaster} entity. This class is used
 * in {@link com.qpg.web.rest.TopicMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /topic-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TopicMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter subSubjectMasterId;

    public TopicMasterCriteria() {
    }

    public TopicMasterCriteria(TopicMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.subSubjectMasterId = other.subSubjectMasterId == null ? null : other.subSubjectMasterId.copy();
    }

    @Override
    public TopicMasterCriteria copy() {
        return new TopicMasterCriteria(this);
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

    public LongFilter getSubSubjectMasterId() {
        return subSubjectMasterId;
    }

    public void setSubSubjectMasterId(LongFilter subSubjectMasterId) {
        this.subSubjectMasterId = subSubjectMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TopicMasterCriteria that = (TopicMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(subSubjectMasterId, that.subSubjectMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        subSubjectMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopicMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (subSubjectMasterId != null ? "subSubjectMasterId=" + subSubjectMasterId + ", " : "") +
            "}";
    }

}
