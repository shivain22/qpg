package com.qpg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.security.auth.Subject;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QbMaster.
 */
@Entity
@Table(name = "qb_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QbMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "qb_file", nullable = false)
    private byte[] qbFile;

    @Column(name = "qb_file_content_type", nullable = false)
    private String qbFileContentType;

    @Transient
    private CollegeMaster collegeMaster;

    @Transient
    private DepartmentMaster departmentMaster;

    @Transient
    private CourseMaster courseMaster;

    @Transient
    private CategoryMaster categoryMaster;

    @Transient
    private SubCategoryMaster subCategoryMaster;

    @Transient
    private SubjectMaster subjectMaster;

    @Transient
    private SubSubjectMaster subSubjectMaster;

    @Transient
    private TopicMaster topicMaster;

    @Transient
    private SubTopicMaster subTopicMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getQbFile() {
        return qbFile;
    }

    public QbMaster qbFile(byte[] qbFile) {
        this.qbFile = qbFile;
        return this;
    }

    public void setQbFile(byte[] qbFile) {
        this.qbFile = qbFile;
    }

    public String getQbFileContentType() {
        return qbFileContentType;
    }

    public QbMaster qbFileContentType(String qbFileContentType) {
        this.qbFileContentType = qbFileContentType;
        return this;
    }

    public void setQbFileContentType(String qbFileContentType) {
        this.qbFileContentType = qbFileContentType;
    }

    public CollegeMaster getCollegeMaster() {
        return collegeMaster;
    }

    public void setCollegeMaster(CollegeMaster collegeMaster) {
        this.collegeMaster = collegeMaster;
    }

    public DepartmentMaster getDepartmentMaster() {
        return departmentMaster;
    }

    public void setDepartmentMaster(DepartmentMaster departmentMaster) {
        this.departmentMaster = departmentMaster;
    }

    public CourseMaster getCourseMaster() {
        return courseMaster;
    }

    public void setCourseMaster(CourseMaster courseMaster) {
        this.courseMaster = courseMaster;
    }

    public CategoryMaster getCategoryMaster() {
        return categoryMaster;
    }

    public void setCategoryMaster(CategoryMaster categoryMaster) {
        this.categoryMaster = categoryMaster;
    }

    public SubCategoryMaster getSubCategoryMaster() {
        return subCategoryMaster;
    }

    public void setSubCategoryMaster(SubCategoryMaster subCategoryMaster) {
        this.subCategoryMaster = subCategoryMaster;
    }

    public SubjectMaster getSubjectMaster() {
        return subjectMaster;
    }

    public void setSubjectMaster(SubjectMaster subjectMaster) {
        this.subjectMaster = subjectMaster;
    }

    public SubSubjectMaster getSubSubjectMaster() {
        return subSubjectMaster;
    }

    public void setSubSubjectMaster(SubSubjectMaster subSubjectMaster) {
        this.subSubjectMaster = subSubjectMaster;
    }

    public TopicMaster getTopicMaster() {
        return topicMaster;
    }

    public void setTopicMaster(TopicMaster topicMaster) {
        this.topicMaster = topicMaster;
    }

    public SubTopicMaster getSubTopicMaster() {
        return subTopicMaster;
    }

    public void setSubTopicMaster(SubTopicMaster subTopicMaster) {
        this.subTopicMaster = subTopicMaster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QbMaster)) {
            return false;
        }
        return id != null && id.equals(((QbMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QbMaster{" +
            "id=" + getId() +
            ", qbFile='" + getQbFile() + "'" +
            ", qbFileContentType='" + getQbFileContentType() + "'" +
            ", collegeMaster='" + getCollegeMaster() + "'" +
            ", departmentMaster='" + getDepartmentMaster() + "'" +
            ", courseMaster='" + getCourseMaster() + "'" +
            ", categoryMaster='" + getCategoryMaster() + "'" +
            ", subCategoryMaster='" + getSubCategoryMaster() + "'" +
            ", subjectMaster='" + getSubjectMaster() + "'" +
            ", subSubjectMaster='" + getSubSubjectMaster() + "'" +
            ", topicMaster='" + getTopicMaster() + "'" +
            ", subTopicMaster='" + getSubTopicMaster() + "'" +
            "}";
    }
}
