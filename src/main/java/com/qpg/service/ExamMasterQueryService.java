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

import com.qpg.domain.ExamMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.ExamMasterRepository;
import com.qpg.service.dto.ExamMasterCriteria;

/**
 * Service for executing complex queries for {@link ExamMaster} entities in the database.
 * The main input is a {@link ExamMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExamMaster} or a {@link Page} of {@link ExamMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExamMasterQueryService extends QueryService<ExamMaster> {

    private final Logger log = LoggerFactory.getLogger(ExamMasterQueryService.class);

    private final ExamMasterRepository examMasterRepository;

    public ExamMasterQueryService(ExamMasterRepository examMasterRepository) {
        this.examMasterRepository = examMasterRepository;
    }

    /**
     * Return a {@link List} of {@link ExamMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExamMaster> findByCriteria(ExamMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExamMaster> specification = createSpecification(criteria);
        return examMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExamMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamMaster> findByCriteria(ExamMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExamMaster> specification = createSpecification(criteria);
        return examMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExamMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExamMaster> specification = createSpecification(criteria);
        return examMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link ExamMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExamMaster> createSpecification(ExamMasterCriteria criteria) {
        Specification<ExamMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExamMaster_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ExamMaster_.title));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ExamMaster_.startDate));
            }
            /*if (criteria.getQuestionBluePrintMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionBluePrintMasterId(),
                    root -> root.join(ExamMaster_.questionBluePrintMaster, JoinType.LEFT).get(QuestionBluePrintMaster_.id)));
            }*/
        }
        return specification;
    }
}
