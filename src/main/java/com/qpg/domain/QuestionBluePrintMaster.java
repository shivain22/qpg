package com.qpg.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A QuestionBluePrintMaster.
 */
@Entity
@Table(name = "question_blue_print_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionBluePrintMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "total_marks")
    private Long totalMarks;

    @Column(name = "parta_duration")
    private Long partaDuration;

    @Column(name = "partb_duration")
    private Long partbDuration;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String bpMasterLabel;

    public Long getPartaDuration() {
        return partaDuration;
    }

    public void setPartaDuration(Long partaDuration) {
        this.partaDuration = partaDuration;
    }

    public Long getPartbDuration() {
        return partbDuration;
    }

    public void setPartbDuration(Long partbDuration) {
        this.partbDuration = partbDuration;
    }

    public String getBpMasterLabel() {
        this.bpMasterLabel = this.name;
        bpMasterLabel+=" - Total Marks: "+this.totalMarks;
        if(this.questionBluePrintDetails!=null){
            for(QuestionBluePrintDetail qbpd:this.questionBluePrintDetails){
                bpMasterLabel+=" - "+qbpd.getQuestionTypeMaster().getShortName()+": "+qbpd.getTotalQuestions();
            }
        }
        return bpMasterLabel;
    }

    public void setBpMasterLabel(String bpMasterLabel) {
        this.bpMasterLabel = bpMasterLabel;
    }

    @OneToMany(mappedBy = "questionBluePrintMaster",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.ALL},fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy("questionTypeMaster.id asc")
    private Set<QuestionBluePrintDetail> questionBluePrintDetails = new HashSet<>();

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

    public QuestionBluePrintMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public QuestionBluePrintMaster description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<QuestionBluePrintDetail> getQuestionBluePrintDetails() {
        return questionBluePrintDetails;
    }

    public void setQuestionBluePrintDetails(Set<QuestionBluePrintDetail> questionBluePrintDetails) {
        this.questionBluePrintDetails = questionBluePrintDetails;
    }

    public Long getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Long totalMarks) {
        this.totalMarks = totalMarks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionBluePrintMaster)) {
            return false;
        }
        return id != null && id.equals(((QuestionBluePrintMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionBluePrintMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
