package com.qpg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TestEntity.
 */
@Entity
@Table(name = "test_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "test_file", nullable = false)
    private byte[] testFile;

    @Column(name = "test_file_content_type", nullable = false)
    private String testFileContentType;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getTestFile() {
        return testFile;
    }

    public TestEntity testFile(byte[] testFile) {
        this.testFile = testFile;
        return this;
    }

    public void setTestFile(byte[] testFile) {
        this.testFile = testFile;
    }

    public String getTestFileContentType() {
        return testFileContentType;
    }

    public TestEntity testFileContentType(String testFileContentType) {
        this.testFileContentType = testFileContentType;
        return this;
    }

    public void setTestFileContentType(String testFileContentType) {
        this.testFileContentType = testFileContentType;
    }

    public String getFileName() {
        return fileName;
    }

    public TestEntity fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestEntity)) {
            return false;
        }
        return id != null && id.equals(((TestEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestEntity{" +
            "id=" + getId() +
            ", testFile='" + getTestFile() + "'" +
            ", testFileContentType='" + getTestFileContentType() + "'" +
            ", fileName='" + getFileName() + "'" +
            "}";
    }
}
