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

import com.qpg.domain.SubTopicMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.SubTopicMasterRepository;
import com.qpg.service.dto.SubTopicMasterCriteria;

/**
 * Service for executing complex queries for {@link SubTopicMaster} entities in the database.
 * The main input is a {@link SubTopicMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubTopicMaster} or a {@link Page} of {@link SubTopicMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubTopicMasterQueryService extends QueryService<SubTopicMaster> {

    private final Logger log = LoggerFactory.getLogger(SubTopicMasterQueryService.class);

    private final SubTopicMasterRepository subTopicMasterRepository;

    public SubTopicMasterQueryService(SubTopicMasterRepository subTopicMasterRepository) {
        this.subTopicMasterRepository = subTopicMasterRepository;
    }

    /**
     * Return a {@link List} of {@link SubTopicMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubTopicMaster> findByCriteria(SubTopicMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubTopicMaster> specification = createSpecification(criteria);
        return subTopicMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SubTopicMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubTopicMaster> findByCriteria(SubTopicMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubTopicMaster> specification = createSpecification(criteria);
        return subTopicMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubTopicMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubTopicMaster> specification = createSpecification(criteria);
        return subTopicMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link SubTopicMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubTopicMaster> createSpecification(SubTopicMasterCriteria criteria) {
        Specification<SubTopicMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubTopicMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SubTopicMaster_.name));
            }
            if (criteria.getTopicMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getTopicMasterId(),
                    root -> root.join(SubTopicMaster_.topicMaster, JoinType.LEFT).get(TopicMaster_.id)));
            }
            if (criteria.getQuestionMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionMasterId(),
                    root -> root.join(SubTopicMaster_.questionMasters, JoinType.LEFT).get(QuestionMaster_.id)));
            }
        }
        return specification;
    }
}
