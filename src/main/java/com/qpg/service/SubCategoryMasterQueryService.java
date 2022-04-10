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

import com.qpg.domain.SubCategoryMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.SubCategoryMasterRepository;
import com.qpg.service.dto.SubCategoryMasterCriteria;

/**
 * Service for executing complex queries for {@link SubCategoryMaster} entities in the database.
 * The main input is a {@link SubCategoryMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubCategoryMaster} or a {@link Page} of {@link SubCategoryMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubCategoryMasterQueryService extends QueryService<SubCategoryMaster> {

    private final Logger log = LoggerFactory.getLogger(SubCategoryMasterQueryService.class);

    private final SubCategoryMasterRepository subCategoryMasterRepository;

    public SubCategoryMasterQueryService(SubCategoryMasterRepository subCategoryMasterRepository) {
        this.subCategoryMasterRepository = subCategoryMasterRepository;
    }

    /**
     * Return a {@link List} of {@link SubCategoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubCategoryMaster> findByCriteria(SubCategoryMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubCategoryMaster> specification = createSpecification(criteria);
        return subCategoryMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SubCategoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubCategoryMaster> findByCriteria(SubCategoryMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubCategoryMaster> specification = createSpecification(criteria);
        return subCategoryMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubCategoryMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubCategoryMaster> specification = createSpecification(criteria);
        return subCategoryMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link SubCategoryMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubCategoryMaster> createSpecification(SubCategoryMasterCriteria criteria) {
        Specification<SubCategoryMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubCategoryMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SubCategoryMaster_.name));
            }
            if (criteria.getSubjectMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubjectMasterId(),
                    root -> root.join(SubCategoryMaster_.subjectMasters, JoinType.LEFT).get(SubjectMaster_.id)));
            }
            if (criteria.getCategoryMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryMasterId(),
                    root -> root.join(SubCategoryMaster_.categoryMaster, JoinType.LEFT).get(CategoryMaster_.id)));
            }
        }
        return specification;
    }
}
