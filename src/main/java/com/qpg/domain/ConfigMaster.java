package com.qpg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CollegeMaster.
 */
@Entity
@Table(name = "config_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "name", length = 500, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "value", length = 500, nullable = false, unique = true)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ConfigMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigMaster)) {
            return false;
        }
        return id != null && id.equals(((ConfigMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
