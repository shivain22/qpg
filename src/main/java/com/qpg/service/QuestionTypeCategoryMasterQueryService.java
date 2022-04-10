package com.qpg.service;

import com.qpg.domain.QuestionMaster_;
import com.qpg.domain.QuestionTypeCategoryMaster;
import com.qpg.domain.QuestionTypeCategoryMaster_;
import com.qpg.repository.QuestionTypeCategoryMasterRepository;
import com.qpg.service.dto.QuestionTypeCategoryMasterCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link QuestionTypeCategoryMaster} entities in the database.
 * The main input is a {@link QuestionTypeCategoryMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionTypeCategoryMaster} or a {@link Page} of {@link QuestionTypeCategoryMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionTypeCategoryMasterQueryService extends QueryService<QuestionTypeCategoryMaster> {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeCategoryMasterQueryService.class);

    private final QuestionTypeCategoryMasterRepository questionTypeCategoryMasterRepository;

    public QuestionTypeCategoryMasterQueryService(QuestionTypeCategoryMasterRepository questionTypeCategoryMasterRepository) {
        this.questionTypeCategoryMasterRepository = questionTypeCategoryMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionTypeCategoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionTypeCategoryMaster> findByCriteria(QuestionTypeCategoryMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionTypeCategoryMaster> specification = createSpecification(criteria);
        return questionTypeCategoryMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionTypeCategoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionTypeCategoryMaster> findByCriteria(QuestionTypeCategoryMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionTypeCategoryMaster> specification = createSpecification(criteria);
        return questionTypeCategoryMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionTypeCategoryMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionTypeCategoryMaster> specification = createSpecification(criteria);
        return questionTypeCategoryMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionTypeCategoryMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionTypeCategoryMaster> createSpecification(QuestionTypeCategoryMasterCriteria criteria) {
        Specification<QuestionTypeCategoryMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionTypeCategoryMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QuestionTypeCategoryMaster_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QuestionTypeCategoryMaster_.shortName));
            }
            if (criteria.getQuestionMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionMasterId(),
                    root -> root.join(QuestionTypeCategoryMaster_.questionMasters, JoinType.LEFT).get(QuestionMaster_.id)));
            }
        }
        return specification;
    }
}
