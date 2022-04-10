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
 * Criteria class for the {@link com.qpg.domain.SubTopicMaster} entity. This class is used
 * in {@link com.qpg.web.rest.SubTopicMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-topic-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubTopicMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter topicMasterId;

    private LongFilter questionMasterId;

    public SubTopicMasterCriteria() {
    }

    public SubTopicMasterCriteria(SubTopicMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.topicMasterId = other.topicMasterId == null ? null : other.topicMasterId.copy();
        this.questionMasterId = other.questionMasterId == null ? null : other.questionMasterId.copy();
    }

    @Override
    public SubTopicMasterCriteria copy() {
        return new SubTopicMasterCriteria(this);
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

    public LongFilter getTopicMasterId() {
        return topicMasterId;
    }

    public void setTopicMasterId(LongFilter topicMasterId) {
        this.topicMasterId = topicMasterId;
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
        final SubTopicMasterCriteria that = (SubTopicMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(topicMasterId, that.topicMasterId) &&
            Objects.equals(questionMasterId, that.questionMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        topicMasterId,
        questionMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubTopicMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (topicMasterId != null ? "topicMasterId=" + topicMasterId + ", " : "") +
                (questionMasterId != null ? "questionMasterId=" + questionMasterId + ", " : "") +
            "}";
    }

}
