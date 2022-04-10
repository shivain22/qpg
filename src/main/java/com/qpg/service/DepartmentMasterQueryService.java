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

import com.qpg.domain.DepartmentMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.DepartmentMasterRepository;
import com.qpg.service.dto.DepartmentMasterCriteria;

/**
 * Service for executing complex queries for {@link DepartmentMaster} entities in the database.
 * The main input is a {@link DepartmentMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepartmentMaster} or a {@link Page} of {@link DepartmentMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentMasterQueryService extends QueryService<DepartmentMaster> {

    private final Logger log = LoggerFactory.getLogger(DepartmentMasterQueryService.class);

    private final DepartmentMasterRepository departmentMasterRepository;

    public DepartmentMasterQueryService(DepartmentMasterRepository departmentMasterRepository) {
        this.departmentMasterRepository = departmentMasterRepository;
    }

    /**
     * Return a {@link List} of {@link DepartmentMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentMaster> findByCriteria(DepartmentMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DepartmentMaster> specification = createSpecification(criteria);
        return departmentMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DepartmentMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentMaster> findByCriteria(DepartmentMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepartmentMaster> specification = createSpecification(criteria);
        return departmentMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartmentMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepartmentMaster> specification = createSpecification(criteria);
        return departmentMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link DepartmentMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DepartmentMaster> createSpecification(DepartmentMasterCriteria criteria) {
        Specification<DepartmentMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DepartmentMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DepartmentMaster_.name));
            }
            if (criteria.getCollegeMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCollegeMasterId(),
                    root -> root.join(DepartmentMaster_.collegeMaster, JoinType.LEFT).get(CollegeMaster_.id)));
            }
            if (criteria.getCourseMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourseMasterId(),
                    root -> root.join(DepartmentMaster_.courseMasters, JoinType.LEFT).get(CourseMaster_.id)));
            }
        }
        return specification;
    }
}
