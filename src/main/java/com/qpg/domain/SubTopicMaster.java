package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubTopicMaster.
 */
@Entity
@Table(name = "sub_topic_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubTopicMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "subTopicMasters", allowSetters = true)
    private TopicMaster topicMaster;

    @OneToMany(mappedBy = "subTopicMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<QuestionMaster> questionMasters = new HashSet<>();

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

    public SubTopicMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TopicMaster getTopicMaster() {
        return topicMaster;
    }

    public SubTopicMaster topicMaster(TopicMaster topicMaster) {
        this.topicMaster = topicMaster;
        return this;
    }

    public void setTopicMaster(TopicMaster topicMaster) {
        this.topicMaster = topicMaster;
    }

    public Set<QuestionMaster> getQuestionMasters() {
        return questionMasters;
    }

    public SubTopicMaster questionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
        return this;
    }



    public void setQuestionMasters(Set<QuestionMaster> questionMasters) {
        this.questionMasters = questionMasters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubTopicMaster)) {
            return false;
        }
        return id != null && id.equals(((SubTopicMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubTopicMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
