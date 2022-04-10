package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ExamMaster.
 */
@Entity
@Table(name = "exam_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "title", length = 100, nullable = false, unique = true)
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

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
    private QuestionBluePrintMaster questionBluePrintMaster;

    @ManyToOne(optional = true)
    private  SubjectMaster subjectMaster;

    @ManyToOne(optional = true)
    private  SubSubjectMaster subSubjectMaster;

    @ManyToOne(optional = true)
    private  TopicMaster topicMaster;

    @ManyToOne(optional = true)
    private  SubTopicMaster subTopicMaster;

    public QuestionBluePrintMaster getQuestionBluePrintMaster() {
        return questionBluePrintMaster;
    }

    public void setQuestionBluePrintMaster(QuestionBluePrintMaster questionBluePrintMaster) {
        this.questionBluePrintMaster = questionBluePrintMaster;
    }

    public CollegeMaster getCollegeMaster() {
        if(this.getSubjectMaster()!=null){
            if(this.getSubjectMaster().getSubCategoryMaster()!=null){
                if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster()!=null){
                    if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster()!=null){
                        if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster().getDepartmentMaster()!=null){
                            if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster().getDepartmentMaster().getCollegeMaster()!=null) {
                                this.collegeMaster = this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster().getDepartmentMaster().getCollegeMaster();
                            }
                        }
                    }
                }
            }
        }
        return collegeMaster;
    }

    public void setCollegeMaster(CollegeMaster collegeMaster) {
        this.collegeMaster = collegeMaster;
    }

    public DepartmentMaster getDepartmentMaster() {
        if(this.getSubjectMaster()!=null){
            if(this.getSubjectMaster().getSubCategoryMaster()!=null){
                if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster()!=null){
                    if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster()!=null){
                        if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster().getDepartmentMaster()!=null){
                            this.departmentMaster = this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster().getDepartmentMaster();
                        }
                    }
                }
            }
        }
        return departmentMaster;
    }

    public void setDepartmentMaster(DepartmentMaster departmentMaster) {
        this.departmentMaster = departmentMaster;
    }

    public CourseMaster getCourseMaster() {
        if(this.getSubjectMaster()!=null){
            if(this.getSubjectMaster().getSubCategoryMaster()!=null){
                if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster()!=null){
                    if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster()!=null){
                            this.courseMaster = this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster().getCourseMaster();
                    }
                }
            }
        }
        return courseMaster;
    }

    public void setCourseMaster(CourseMaster courseMaster) {
        this.courseMaster = courseMaster;
    }

    public CategoryMaster getCategoryMaster() {
        if(this.getSubjectMaster()!=null){
            if(this.getSubjectMaster().getSubCategoryMaster()!=null){
                if(this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster()!=null){
                    this.categoryMaster = this.getSubjectMaster().getSubCategoryMaster().getCategoryMaster();
                }
            }
        }
        return categoryMaster;
    }

    public void setCategoryMaster(CategoryMaster categoryMaster) {
        this.categoryMaster = categoryMaster;
    }

    public SubCategoryMaster getSubCategoryMaster() {
        if(this.getSubjectMaster()!=null){
            if(this.getSubjectMaster().getSubCategoryMaster()!=null){
                    this.subCategoryMaster = this.getSubjectMaster().getSubCategoryMaster();
            }
        }
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

    public String getTitle() {
        return title;
    }

    public ExamMaster title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ExamMaster startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamMaster)) {
            return false;
        }
        return id != null && id.equals(((ExamMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamMaster{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            "}";
    }
}
