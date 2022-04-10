package com.qpg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QpBankUploadMaster.
 */
@Entity
@Table(name = "qp_bank_upload_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QpBankUploadMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "qp_bank_file", nullable = false)
    private byte[] qpBankFile;

    @Column(name = "qp_bank_file_content_type", nullable = false)
    private String qpBankFileContentType;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "name", length = 500, nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "qpBankUploadMasters", allowSetters = true)
    private SubTopicMaster subTopicMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getQpBankFile() {
        return qpBankFile;
    }

    public QpBankUploadMaster qpBankFile(byte[] qpBankFile) {
        this.qpBankFile = qpBankFile;
        return this;
    }

    public void setQpBankFile(byte[] qpBankFile) {
        this.qpBankFile = qpBankFile;
    }

    public String getQpBankFileContentType() {
        return qpBankFileContentType;
    }

    public QpBankUploadMaster qpBankFileContentType(String qpBankFileContentType) {
        this.qpBankFileContentType = qpBankFileContentType;
        return this;
    }

    public void setQpBankFileContentType(String qpBankFileContentType) {
        this.qpBankFileContentType = qpBankFileContentType;
    }

    public String getName() {
        return name;
    }

    public QpBankUploadMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubTopicMaster getSubTopicMaster() {
        return subTopicMaster;
    }

    public QpBankUploadMaster subTopicMaster(SubTopicMaster subTopicMaster) {
        this.subTopicMaster = subTopicMaster;
        return this;
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
        if (!(o instanceof QpBankUploadMaster)) {
            return false;
        }
        return id != null && id.equals(((QpBankUploadMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QpBankUploadMaster{" +
            "id=" + getId() +
            ", qpBankFile='" + getQpBankFile() + "'" +
            ", qpBankFileContentType='" + getQpBankFileContentType() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
