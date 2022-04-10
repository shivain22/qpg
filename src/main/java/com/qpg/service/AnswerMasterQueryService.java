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

import com.qpg.domain.AnswerMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.AnswerMasterRepository;
import com.qpg.service.dto.AnswerMasterCriteria;

/**
 * Service for executing complex queries for {@link AnswerMaster} entities in the database.
 * The main input is a {@link AnswerMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnswerMaster} or a {@link Page} of {@link AnswerMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnswerMasterQueryService extends QueryService<AnswerMaster> {

    private final Logger log = LoggerFactory.getLogger(AnswerMasterQueryService.class);

    private final AnswerMasterRepository answerMasterRepository;

    public AnswerMasterQueryService(AnswerMasterRepository answerMasterRepository) {
        this.answerMasterRepository = answerMasterRepository;
    }

    /**
     * Return a {@link List} of {@link AnswerMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnswerMaster> findByCriteria(AnswerMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnswerMaster> specification = createSpecification(criteria);
        return answerMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AnswerMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnswerMaster> findByCriteria(AnswerMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnswerMaster> specification = createSpecification(criteria);
        return answerMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnswerMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnswerMaster> specification = createSpecification(criteria);
        return answerMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link AnswerMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnswerMaster> createSpecification(AnswerMasterCriteria criteria) {
        Specification<AnswerMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnswerMaster_.id));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), AnswerMaster_.text));
            }
            if (criteria.getCorrect() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrect(), AnswerMaster_.correct));
            }
            if (criteria.getQuestionMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionMasterId(),
                    root -> root.join(AnswerMaster_.questionMaster, JoinType.LEFT).get(QuestionMaster_.id)));
            }
        }
        return specification;
    }
}
