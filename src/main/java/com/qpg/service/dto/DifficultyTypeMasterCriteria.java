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
 * Criteria class for the {@link com.qpg.domain.DifficultyTypeMaster} entity. This class is used
 * in {@link com.qpg.web.rest.DifficultyTypeMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /difficulty-type-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DifficultyTypeMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter questionMasterId;

    public DifficultyTypeMasterCriteria() {
    }

    public DifficultyTypeMasterCriteria(DifficultyTypeMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.questionMasterId = other.questionMasterId == null ? null : other.questionMasterId.copy();
    }

    @Override
    public DifficultyTypeMasterCriteria copy() {
        return new DifficultyTypeMasterCriteria(this);
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

    public LongFilter getQuestionMasterId() {
        return questionMasterId;
    }

    public void setQuestionMasterId(LongFilter questionMasterId) {
        this.questionMasterId = questionMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DifficultyTypeMasterCriteria that = (DifficultyTypeMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(questionMasterId, that.questionMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        questionMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DifficultyTypeMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (questionMasterId != null ? "questionMasterId=" + questionMasterId + ", " : "") +
            "}";
    }

}
