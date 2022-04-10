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

import com.qpg.domain.CourseMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.CourseMasterRepository;
import com.qpg.service.dto.CourseMasterCriteria;

/**
 * Service for executing complex queries for {@link CourseMaster} entities in the database.
 * The main input is a {@link CourseMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseMaster} or a {@link Page} of {@link CourseMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseMasterQueryService extends QueryService<CourseMaster> {

    private final Logger log = LoggerFactory.getLogger(CourseMasterQueryService.class);

    private final CourseMasterRepository courseMasterRepository;

    public CourseMasterQueryService(CourseMasterRepository courseMasterRepository) {
        this.courseMasterRepository = courseMasterRepository;
    }

    /**
     * Return a {@link List} of {@link CourseMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseMaster> findByCriteria(CourseMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseMaster> specification = createSpecification(criteria);
        return courseMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CourseMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseMaster> findByCriteria(CourseMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseMaster> specification = createSpecification(criteria);
        return courseMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseMaster> specification = createSpecification(criteria);
        return courseMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseMaster> createSpecification(CourseMasterCriteria criteria) {
        Specification<CourseMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CourseMaster_.name));
            }
            if (criteria.getDepartmentMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentMasterId(),
                    root -> root.join(CourseMaster_.departmentMaster, JoinType.LEFT).get(DepartmentMaster_.id)));
            }
            if (criteria.getCategoryMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryMasterId(),
                    root -> root.join(CourseMaster_.categoryMasters, JoinType.LEFT).get(CategoryMaster_.id)));
            }
        }
        return specification;
    }
}
