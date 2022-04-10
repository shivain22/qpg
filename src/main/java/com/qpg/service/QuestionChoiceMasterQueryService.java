package com.qpg.service;

import com.qpg.domain.QuestionMaster_;
import com.qpg.domain.QuestionChoiceMaster;
import com.qpg.domain.QuestionChoiceMaster_;
import com.qpg.repository.QuestionChoiceMasterRepository;
import com.qpg.service.dto.QuestionChoiceMasterCriteria;
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
 * Service for executing complex queries for {@link QuestionChoiceMaster} entities in the database.
 * The main input is a {@link QuestionChoiceMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionChoiceMaster} or a {@link Page} of {@link QuestionChoiceMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionChoiceMasterQueryService extends QueryService<QuestionChoiceMaster> {

    private final Logger log = LoggerFactory.getLogger(QuestionChoiceMasterQueryService.class);

    private final QuestionChoiceMasterRepository questionChoiceMasterRepository;

    public QuestionChoiceMasterQueryService(QuestionChoiceMasterRepository questionChoiceMasterRepository) {
        this.questionChoiceMasterRepository = questionChoiceMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionChoiceMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionChoiceMaster> findByCriteria(QuestionChoiceMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionChoiceMaster> specification = createSpecification(criteria);
        return questionChoiceMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionChoiceMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionChoiceMaster> findByCriteria(QuestionChoiceMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionChoiceMaster> specification = createSpecification(criteria);
        return questionChoiceMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionChoiceMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionChoiceMaster> specification = createSpecification(criteria);
        return questionChoiceMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionChoiceMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionChoiceMaster> createSpecification(QuestionChoiceMasterCriteria criteria) {
        Specification<QuestionChoiceMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionChoiceMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QuestionChoiceMaster_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QuestionChoiceMaster_.shortName));
            }
        }
        return specification;
    }
}
