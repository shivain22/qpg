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

import com.qpg.domain.QuestionBankMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.QuestionBankMasterRepository;
import com.qpg.service.dto.QuestionBankMasterCriteria;

/**
 * Service for executing complex queries for {@link QuestionBankMaster} entities in the database.
 * The main input is a {@link QuestionBankMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionBankMaster} or a {@link Page} of {@link QuestionBankMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionBankMasterQueryService extends QueryService<QuestionBankMaster> {

    private final Logger log = LoggerFactory.getLogger(QuestionBankMasterQueryService.class);

    private final QuestionBankMasterRepository questionBankMasterRepository;

    public QuestionBankMasterQueryService(QuestionBankMasterRepository questionBankMasterRepository) {
        this.questionBankMasterRepository = questionBankMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionBankMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionBankMaster> findByCriteria(QuestionBankMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionBankMaster> specification = createSpecification(criteria);
        return questionBankMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionBankMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionBankMaster> findByCriteria(QuestionBankMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionBankMaster> specification = createSpecification(criteria);
        return questionBankMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionBankMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionBankMaster> specification = createSpecification(criteria);
        return questionBankMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionBankMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionBankMaster> createSpecification(QuestionBankMasterCriteria criteria) {
        Specification<QuestionBankMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionBankMaster_.id));
            }
        }
        return specification;
    }
}
