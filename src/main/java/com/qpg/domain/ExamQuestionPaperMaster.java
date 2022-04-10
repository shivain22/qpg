package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoryMaster.
 */
@Entity
@Table(name = "exam_question_paper_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamQuestionPaperMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "file_name", length = 50, nullable = false, unique = true)
    private String fileName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "categoryMasters", allowSetters = true)
    private ExamMaster examMaster;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<ExamQuestionPaperDetail> getExamQuestionPaperDetails() {
        return examQuestionPaperDetails;
    }

    public void setExamQuestionPaperDetails(Set<ExamQuestionPaperDetail> examQuestionPaperDetails) {
        this.examQuestionPaperDetails = examQuestionPaperDetails;
    }

    @OneToMany(mappedBy = "examQuestionPaperMaster", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy("questionMaster.questionTypeMaster.id")
    private Set<ExamQuestionPaperDetail> examQuestionPaperDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ExamQuestionPaperMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

     public ExamQuestionPaperMaster examQuestionPaperDetails(Set<ExamQuestionPaperDetail> examQuestionPaperDetails) {
        this.examQuestionPaperDetails = examQuestionPaperDetails;
        return this;
    }

    public ExamQuestionPaperMaster addExamQuestionPaperDetails(ExamQuestionPaperDetail examQuestionPaperDetail) {
        this.examQuestionPaperDetails.add(examQuestionPaperDetail);
        examQuestionPaperDetail.setExamQuestionPaperMaster(this);
        return this;
    }

    public ExamQuestionPaperMaster removeExamQuestionPaperDetails(ExamQuestionPaperDetail examQuestionPaperDetail) {
        this.examQuestionPaperDetails.remove(examQuestionPaperDetail);
        examQuestionPaperDetail.setExamQuestionPaperMaster(null);
        return this;
    }

    public ExamMaster getExamMaster() {
        return examMaster;
    }

    public void setExamMaster(ExamMaster examMaster) {
        this.examMaster = examMaster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamQuestionPaperMaster)) {
            return false;
        }
        return id != null && id.equals(((ExamQuestionPaperMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamQuestionPaperMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
