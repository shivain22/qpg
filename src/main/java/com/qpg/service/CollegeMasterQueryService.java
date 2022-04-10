package com.qpg.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.qpg.domain.CollegeMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.CollegeMasterRepository;
import com.qpg.service.dto.CollegeMasterCriteria;

/**
 * Service for executing complex queries for {@link CollegeMaster} entities in the database.
 * The main input is a {@link CollegeMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CollegeMaster} or a {@link Page} of {@link CollegeMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CollegeMasterQueryService extends QueryService<CollegeMaster> {

    private final Logger log = LoggerFactory.getLogger(CollegeMasterQueryService.class);

    private final CollegeMasterRepository collegeMasterRepository;

    public CollegeMasterQueryService(CollegeMasterRepository collegeMasterRepository) {
        this.collegeMasterRepository = collegeMasterRepository;
    }

    /**
     * Return a {@link List} of {@link CollegeMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CollegeMaster> findByCriteria(CollegeMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CollegeMaster> specification = createSpecification(criteria);
        return collegeMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CollegeMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CollegeMaster> findByCriteria(CollegeMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CollegeMaster> specification = createSpecification(criteria);
        return collegeMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CollegeMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CollegeMaster> specification = createSpecification(criteria);
        return collegeMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link CollegeMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CollegeMaster> createSpecification(CollegeMasterCriteria criteria) {
        Specification<CollegeMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CollegeMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CollegeMaster_.name));
            }
            if (criteria.getDepartmentMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentMasterId(),
                    root -> root.join(CollegeMaster_.departmentMasters, JoinType.LEFT).get(DepartmentMaster_.id)));
            }
        }
        return specification;
    }
}
