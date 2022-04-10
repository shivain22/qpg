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

import com.qpg.domain.SubjectMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.SubjectMasterRepository;
import com.qpg.service.dto.SubjectMasterCriteria;

/**
 * Service for executing complex queries for {@link SubjectMaster} entities in the database.
 * The main input is a {@link SubjectMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubjectMaster} or a {@link Page} of {@link SubjectMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubjectMasterQueryService extends QueryService<SubjectMaster> {

    private final Logger log = LoggerFactory.getLogger(SubjectMasterQueryService.class);

    private final SubjectMasterRepository subjectMasterRepository;

    public SubjectMasterQueryService(SubjectMasterRepository subjectMasterRepository) {
        this.subjectMasterRepository = subjectMasterRepository;
    }

    /**
     * Return a {@link List} of {@link SubjectMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubjectMaster> findByCriteria(SubjectMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubjectMaster> specification = createSpecification(criteria);
        return subjectMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SubjectMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubjectMaster> findByCriteria(SubjectMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubjectMaster> specification = createSpecification(criteria);
        return subjectMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubjectMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubjectMaster> specification = createSpecification(criteria);
        return subjectMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link SubjectMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubjectMaster> createSpecification(SubjectMasterCriteria criteria) {
        Specification<SubjectMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubjectMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SubjectMaster_.name));
            }
            if (criteria.getSubCategoryMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubCategoryMasterId(),
                    root -> root.join(SubjectMaster_.subCategoryMaster, JoinType.LEFT).get(SubCategoryMaster_.id)));
            }
        }
        return specification;
    }
}
