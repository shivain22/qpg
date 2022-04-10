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

import com.qpg.domain.QuestionBluePrintMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.QuestionBluePrintMasterRepository;
import com.qpg.service.dto.QuestionBluePrintMasterCriteria;

/**
 * Service for executing complex queries for {@link QuestionBluePrintMaster} entities in the database.
 * The main input is a {@link QuestionBluePrintMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionBluePrintMaster} or a {@link Page} of {@link QuestionBluePrintMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionBluePrintMasterQueryService extends QueryService<QuestionBluePrintMaster> {

    private final Logger log = LoggerFactory.getLogger(QuestionBluePrintMasterQueryService.class);

    private final QuestionBluePrintMasterRepository questionBluePrintMasterRepository;

    public QuestionBluePrintMasterQueryService(QuestionBluePrintMasterRepository questionBluePrintMasterRepository) {
        this.questionBluePrintMasterRepository = questionBluePrintMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionBluePrintMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionBluePrintMaster> findByCriteria(QuestionBluePrintMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionBluePrintMaster> specification = createSpecification(criteria);
        return questionBluePrintMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionBluePrintMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionBluePrintMaster> findByCriteria(QuestionBluePrintMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionBluePrintMaster> specification = createSpecification(criteria);
        return questionBluePrintMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionBluePrintMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionBluePrintMaster> specification = createSpecification(criteria);
        return questionBluePrintMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionBluePrintMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionBluePrintMaster> createSpecification(QuestionBluePrintMasterCriteria criteria) {
        Specification<QuestionBluePrintMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionBluePrintMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QuestionBluePrintMaster_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), QuestionBluePrintMaster_.description));
            }
            /*if (criteria.getExamMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getExamMasterId(),
                    root -> root.join(QuestionBluePrintMaster_.examMaster, JoinType.LEFT).get(ExamMaster_.id)));
            }*/
        }
        return specification;
    }
}
