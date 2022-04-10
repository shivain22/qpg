package com.qpg.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QuestionBankMaster.
 */
@Entity
@Table(name = "question_bank_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionBankMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "question_bank_file", nullable = false)
    private byte[] questionBankFile;

    @Column(name = "question_bank_file_content_type", nullable = false)
    private String questionBankFileContentType;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  CollegeMaster collegeMaster;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  DepartmentMaster departmentMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  CourseMaster courseMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  CategoryMaster categoryMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  SubCategoryMaster subCategoryMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  SubjectMaster subjectMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  SubSubjectMaster subSubjectMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  TopicMaster topicMaster;
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private  SubTopicMaster subTopicMaster;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getQuestionBankFile() {
        return questionBankFile;
    }

    public QuestionBankMaster questionBankFile(byte[] questionBankFile) {
        this.questionBankFile = questionBankFile;
        return this;
    }

    public void setQuestionBankFile(byte[] questionBankFile) {
        this.questionBankFile = questionBankFile;
    }

    public String getQuestionBankFileContentType() {
        return questionBankFileContentType;
    }

    public QuestionBankMaster questionBankFileContentType(String questionBankFileContentType) {
        this.questionBankFileContentType = questionBankFileContentType;
        return this;
    }

    public void setQuestionBankFileContentType(String questionBankFileContentType) {
        this.questionBankFileContentType = questionBankFileContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionBankMaster)) {
            return false;
        }
        return id != null && id.equals(((QuestionBankMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionBankMaster{" +
            "id=" + getId() +
            ", questionBankFile='" + getQuestionBankFile() + "'" +
            ", questionBankFileContentType='" + getQuestionBankFileContentType() + "'" +
            "}";
    }
}
