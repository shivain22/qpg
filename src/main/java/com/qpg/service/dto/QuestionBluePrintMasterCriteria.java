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
 * Criteria class for the {@link com.qpg.domain.QuestionBluePrintMaster} entity. This class is used
 * in {@link com.qpg.web.rest.QuestionBluePrintMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-blue-print-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionBluePrintMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private LongFilter examMasterId;

    public QuestionBluePrintMasterCriteria() {
    }

    public QuestionBluePrintMasterCriteria(QuestionBluePrintMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.examMasterId = other.examMasterId == null ? null : other.examMasterId.copy();
    }

    @Override
    public QuestionBluePrintMasterCriteria copy() {
        return new QuestionBluePrintMasterCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getExamMasterId() {
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
        final QuestionBluePrintMasterCriteria that = (QuestionBluePrintMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(examMasterId, that.examMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        examMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionBluePrintMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (examMasterId != null ? "examMasterId=" + examMasterId + ", " : "") +
            "}";
    }

}
