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
 * Criteria class for the {@link com.qpg.domain.QpBankUploadMaster} entity. This class is used
 * in {@link com.qpg.web.rest.QpBankUploadMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /qp-bank-upload-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QpBankUploadMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter subTopicMasterId;

    public QpBankUploadMasterCriteria() {
    }

    public QpBankUploadMasterCriteria(QpBankUploadMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.subTopicMasterId = other.subTopicMasterId == null ? null : other.subTopicMasterId.copy();
    }

    @Override
    public QpBankUploadMasterCriteria copy() {
        return new QpBankUploadMasterCriteria(this);
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

    public LongFilter getSubTopicMasterId() {
        return subTopicMasterId;
    }

    public void setSubTopicMasterId(LongFilter subTopicMasterId) {
        this.subTopicMasterId = subTopicMasterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QpBankUploadMasterCriteria that = (QpBankUploadMasterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(subTopicMasterId, that.subTopicMasterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        subTopicMasterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QpBankUploadMasterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (subTopicMasterId != null ? "subTopicMasterId=" + subTopicMasterId + ", " : "") +
            "}";
    }

}
