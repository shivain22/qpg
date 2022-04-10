package com.qpg.service;

import com.qpg.domain.*;
import com.qpg.repository.CollegeMasterRepository;
import com.qpg.repository.ConfigMasterRepository;
import com.qpg.service.dto.CollegeMasterCriteria;
import com.qpg.service.dto.ConfigMasterCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link CollegeMaster} entities in the database.
 * The main input is a {@link CollegeMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CollegeMaster} or a {@link Page} of {@link CollegeMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfigMasterQueryService extends QueryService<ConfigMaster> {

    private final Logger log = LoggerFactory.getLogger(ConfigMasterQueryService.class);

    private final ConfigMasterRepository configMasterRepository;

    public ConfigMasterQueryService(ConfigMasterRepository configMasterRepository) {
        this.configMasterRepository = configMasterRepository;
    }

    /**
     * Return a {@link List} of {@link CollegeMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConfigMaster> findByCriteria(ConfigMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConfigMaster> specification = createSpecification(criteria);
        return configMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConfigMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigMaster> findByCriteria(ConfigMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConfigMaster> specification = createSpecification(criteria);
        return configMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConfigMaster> specification = createSpecification(criteria);
        return configMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConfigMaster> createSpecification(ConfigMasterCriteria criteria) {
        Specification<ConfigMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConfigMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ConfigMaster_.name));
            }
        }
        return specification;
    }
}
