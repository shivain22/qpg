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

import com.qpg.domain.CategoryMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.CategoryMasterRepository;
import com.qpg.service.dto.CategoryMasterCriteria;

/**
 * Service for executing complex queries for {@link CategoryMaster} entities in the database.
 * The main input is a {@link CategoryMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoryMaster} or a {@link Page} of {@link CategoryMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryMasterQueryService extends QueryService<CategoryMaster> {

    private final Logger log = LoggerFactory.getLogger(CategoryMasterQueryService.class);

    private final CategoryMasterRepository categoryMasterRepository;

    public CategoryMasterQueryService(CategoryMasterRepository categoryMasterRepository) {
        this.categoryMasterRepository = categoryMasterRepository;
    }

    /**
     * Return a {@link List} of {@link CategoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryMaster> findByCriteria(CategoryMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoryMaster> specification = createSpecification(criteria);
        return categoryMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryMaster> findByCriteria(CategoryMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoryMaster> specification = createSpecification(criteria);
        return categoryMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoryMaster> specification = createSpecification(criteria);
        return categoryMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoryMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoryMaster> createSpecification(CategoryMasterCriteria criteria) {
        Specification<CategoryMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoryMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CategoryMaster_.name));
            }
            if (criteria.getSubCategoryMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubCategoryMasterId(),
                    root -> root.join(CategoryMaster_.subCategoryMasters, JoinType.LEFT).get(SubCategoryMaster_.id)));
            }
        }
        return specification;
    }
}
