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

import com.qpg.domain.TopicMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.TopicMasterRepository;
import com.qpg.service.dto.TopicMasterCriteria;

/**
 * Service for executing complex queries for {@link TopicMaster} entities in the database.
 * The main input is a {@link TopicMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TopicMaster} or a {@link Page} of {@link TopicMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TopicMasterQueryService extends QueryService<TopicMaster> {

    private final Logger log = LoggerFactory.getLogger(TopicMasterQueryService.class);

    private final TopicMasterRepository topicMasterRepository;

    public TopicMasterQueryService(TopicMasterRepository topicMasterRepository) {
        this.topicMasterRepository = topicMasterRepository;
    }

    /**
     * Return a {@link List} of {@link TopicMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TopicMaster> findByCriteria(TopicMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TopicMaster> specification = createSpecification(criteria);
        return topicMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TopicMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TopicMaster> findByCriteria(TopicMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TopicMaster> specification = createSpecification(criteria);
        return topicMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TopicMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TopicMaster> specification = createSpecification(criteria);
        return topicMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link TopicMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TopicMaster> createSpecification(TopicMasterCriteria criteria) {
        Specification<TopicMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TopicMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TopicMaster_.name));
            }
            if (criteria.getSubSubjectMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubSubjectMasterId(),
                    root -> root.join(TopicMaster_.subSubjectMaster, JoinType.LEFT).get(SubSubjectMaster_.id)));
            }
        }
        return specification;
    }
}
