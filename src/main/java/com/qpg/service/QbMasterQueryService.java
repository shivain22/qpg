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

import com.qpg.domain.QbMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.QbMasterRepository;
import com.qpg.service.dto.QbMasterCriteria;

/**
 * Service for executing complex queries for {@link QbMaster} entities in the database.
 * The main input is a {@link QbMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QbMaster} or a {@link Page} of {@link QbMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QbMasterQueryService extends QueryService<QbMaster> {

    private final Logger log = LoggerFactory.getLogger(QbMasterQueryService.class);

    private final QbMasterRepository qbMasterRepository;

    public QbMasterQueryService(QbMasterRepository qbMasterRepository) {
        this.qbMasterRepository = qbMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QbMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QbMaster> findByCriteria(QbMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QbMaster> specification = createSpecification(criteria);
        return qbMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QbMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QbMaster> findByCriteria(QbMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QbMaster> specification = createSpecification(criteria);
        return qbMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QbMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QbMaster> specification = createSpecification(criteria);
        return qbMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QbMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QbMaster> createSpecification(QbMasterCriteria criteria) {
        Specification<QbMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QbMaster_.id));
            }
            /*if (criteria.getCollegeMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCollegeMaster(), QbMaster_.collegeMaster));
            }
            if (criteria.getDepartmentMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartmentMaster(), QbMaster_.departmentMaster));
            }
            if (criteria.getCourseMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseMaster(), QbMaster_.courseMaster));
            }
            if (criteria.getCategoryMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryMaster(), QbMaster_.categoryMaster));
            }
            if (criteria.getSubCategoryMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubCategoryMaster(), QbMaster_.subCategoryMaster));
            }
            if (criteria.getSubjectMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectMaster(), QbMaster_.subjectMaster));
            }
            if (criteria.getSubSubjectMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubSubjectMaster(), QbMaster_.subSubjectMaster));
            }
            if (criteria.getTopicMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTopicMaster(), QbMaster_.topicMaster));
            }
            if (criteria.getSubTopicMaster() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubTopicMaster(), QbMaster_.subTopicMaster));
            }*/
        }
        return specification;
    }
}
