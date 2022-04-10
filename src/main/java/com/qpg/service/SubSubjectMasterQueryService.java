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

import com.qpg.domain.SubSubjectMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.SubSubjectMasterRepository;
import com.qpg.service.dto.SubSubjectMasterCriteria;

/**
 * Service for executing complex queries for {@link SubSubjectMaster} entities in the database.
 * The main input is a {@link SubSubjectMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubSubjectMaster} or a {@link Page} of {@link SubSubjectMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubSubjectMasterQueryService extends QueryService<SubSubjectMaster> {

    private final Logger log = LoggerFactory.getLogger(SubSubjectMasterQueryService.class);

    private final SubSubjectMasterRepository subSubjectMasterRepository;

    public SubSubjectMasterQueryService(SubSubjectMasterRepository subSubjectMasterRepository) {
        this.subSubjectMasterRepository = subSubjectMasterRepository;
    }

    /**
     * Return a {@link List} of {@link SubSubjectMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubSubjectMaster> findByCriteria(SubSubjectMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubSubjectMaster> specification = createSpecification(criteria);
        return subSubjectMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SubSubjectMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubSubjectMaster> findByCriteria(SubSubjectMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubSubjectMaster> specification = createSpecification(criteria);
        return subSubjectMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubSubjectMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubSubjectMaster> specification = createSpecification(criteria);
        return subSubjectMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link SubSubjectMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubSubjectMaster> createSpecification(SubSubjectMasterCriteria criteria) {
        Specification<SubSubjectMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubSubjectMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SubSubjectMaster_.name));
            }
            if (criteria.getSubjectMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubjectMasterId(),
                    root -> root.join(SubSubjectMaster_.subjectMaster, JoinType.LEFT).get(SubjectMaster_.id)));
            }
            if (criteria.getTopicMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getTopicMasterId(),
                    root -> root.join(SubSubjectMaster_.topicMasters, JoinType.LEFT).get(TopicMaster_.id)));
            }
        }
        return specification;
    }
}
