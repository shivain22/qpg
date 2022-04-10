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

import com.qpg.domain.QuestionMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.QuestionMasterRepository;
import com.qpg.service.dto.QuestionMasterCriteria;

/**
 * Service for executing complex queries for {@link QuestionMaster} entities in the database.
 * The main input is a {@link QuestionMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionMaster} or a {@link Page} of {@link QuestionMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionMasterQueryService extends QueryService<QuestionMaster> {

    private final Logger log = LoggerFactory.getLogger(QuestionMasterQueryService.class);

    private final QuestionMasterRepository questionMasterRepository;

    public QuestionMasterQueryService(QuestionMasterRepository questionMasterRepository) {
        this.questionMasterRepository = questionMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionMaster> findByCriteria(QuestionMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionMaster> specification = createSpecification(criteria);
        return questionMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionMaster> findByCriteria(QuestionMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionMaster> specification = createSpecification(criteria);
        return questionMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionMaster> specification = createSpecification(criteria);
        return questionMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionMaster> createSpecification(QuestionMasterCriteria criteria) {
        Specification<QuestionMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionMaster_.id));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), QuestionMaster_.text));
            }
            if (criteria.getWeightage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightage(), QuestionMaster_.weightage));
            }
            if (criteria.getQuestionTypeMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionTypeMasterId(),
                    root -> root.join(QuestionMaster_.questionTypeMaster, JoinType.LEFT).get(QuestionTypeMaster_.id)));
            }
            if (criteria.getDifficultyTypeMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getDifficultyTypeMasterId(),
                    root -> root.join(QuestionMaster_.difficultyTypeMaster, JoinType.LEFT).get(DifficultyTypeMaster_.id)));
            }
            if (criteria.getSubTopicMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubTopicMasterId(),
                    root -> root.join(QuestionMaster_.subTopicMaster, JoinType.LEFT).get(SubTopicMaster_.id)));
            }
            if (criteria.getParentQuestionMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentQuestionMasterId(),
                    root -> root.join(QuestionMaster_.parentQuestionMaster, JoinType.LEFT).get(QuestionMaster_.id)));
            }
            if (criteria.getQuestionMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionMasterId(),
                    root -> root.join(QuestionMaster_.questionMasters, JoinType.LEFT).get(QuestionMaster_.id)));
            }
            if (criteria.getAnswerMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerMasterId(),
                    root -> root.join(QuestionMaster_.answerMasters, JoinType.LEFT).get(AnswerMaster_.id)));
            }
        }
        return specification;
    }
}
