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

import com.qpg.domain.DifficultyTypeMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.DifficultyTypeMasterRepository;
import com.qpg.service.dto.DifficultyTypeMasterCriteria;

/**
 * Service for executing complex queries for {@link DifficultyTypeMaster} entities in the database.
 * The main input is a {@link DifficultyTypeMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DifficultyTypeMaster} or a {@link Page} of {@link DifficultyTypeMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DifficultyTypeMasterQueryService extends QueryService<DifficultyTypeMaster> {

    private final Logger log = LoggerFactory.getLogger(DifficultyTypeMasterQueryService.class);

    private final DifficultyTypeMasterRepository difficultyTypeMasterRepository;

    public DifficultyTypeMasterQueryService(DifficultyTypeMasterRepository difficultyTypeMasterRepository) {
        this.difficultyTypeMasterRepository = difficultyTypeMasterRepository;
    }

    /**
     * Return a {@link List} of {@link DifficultyTypeMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DifficultyTypeMaster> findByCriteria(DifficultyTypeMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DifficultyTypeMaster> specification = createSpecification(criteria);
        return difficultyTypeMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DifficultyTypeMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DifficultyTypeMaster> findByCriteria(DifficultyTypeMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DifficultyTypeMaster> specification = createSpecification(criteria);
        return difficultyTypeMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DifficultyTypeMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DifficultyTypeMaster> specification = createSpecification(criteria);
        return difficultyTypeMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link DifficultyTypeMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DifficultyTypeMaster> createSpecification(DifficultyTypeMasterCriteria criteria) {
        Specification<DifficultyTypeMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DifficultyTypeMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DifficultyTypeMaster_.name));
            }
            if (criteria.getQuestionMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionMasterId(),
                    root -> root.join(DifficultyTypeMaster_.questionMasters, JoinType.LEFT).get(QuestionMaster_.id)));
            }
        }
        return specification;
    }
}
