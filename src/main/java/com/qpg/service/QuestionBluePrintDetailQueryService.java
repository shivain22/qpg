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

import com.qpg.domain.QuestionBluePrintDetail;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.QuestionBluePrintDetailsRepository;
import com.qpg.service.dto.QuestionBluePrintDetailsCriteria;

/**
 * Service for executing complex queries for {@link QuestionBluePrintDetail} entities in the database.
 * The main input is a {@link QuestionBluePrintDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionBluePrintDetail} or a {@link Page} of {@link QuestionBluePrintDetail} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionBluePrintDetailQueryService extends QueryService<QuestionBluePrintDetail> {

    private final Logger log = LoggerFactory.getLogger(QuestionBluePrintDetailQueryService.class);

    private final QuestionBluePrintDetailsRepository questionBluePrintDetailsRepository;

    public QuestionBluePrintDetailQueryService(QuestionBluePrintDetailsRepository questionBluePrintDetailsRepository) {
        this.questionBluePrintDetailsRepository = questionBluePrintDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionBluePrintDetail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionBluePrintDetail> findByCriteria(QuestionBluePrintDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionBluePrintDetail> specification = createSpecification(criteria);
        return questionBluePrintDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionBluePrintDetail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionBluePrintDetail> findByCriteria(QuestionBluePrintDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionBluePrintDetail> specification = createSpecification(criteria);
        return questionBluePrintDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionBluePrintDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionBluePrintDetail> specification = createSpecification(criteria);
        return questionBluePrintDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionBluePrintDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionBluePrintDetail> createSpecification(QuestionBluePrintDetailsCriteria criteria) {
        Specification<QuestionBluePrintDetail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionBluePrintDetail_.id));
            }
            if (criteria.getTotalQuestions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalQuestions(), QuestionBluePrintDetail_.totalQuestions));
            }
            if (criteria.getQuestionTypeMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionTypeMasterId(),
                    root -> root.join(QuestionBluePrintDetail_.questionTypeMaster, JoinType.LEFT).get(QuestionTypeMaster_.id)));
            }
            if (criteria.getQuestionBluePrintMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionBluePrintMasterId(),
                    root -> root.join(QuestionBluePrintDetail_.questionBluePrintMaster, JoinType.LEFT).get(QuestionBluePrintMaster_.id)));
            }
        }
        return specification;
    }
}
