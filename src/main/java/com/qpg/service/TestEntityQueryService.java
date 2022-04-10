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

import com.qpg.domain.TestEntity;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.TestEntityRepository;
import com.qpg.service.dto.TestEntityCriteria;

/**
 * Service for executing complex queries for {@link TestEntity} entities in the database.
 * The main input is a {@link TestEntityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TestEntity} or a {@link Page} of {@link TestEntity} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TestEntityQueryService extends QueryService<TestEntity> {

    private final Logger log = LoggerFactory.getLogger(TestEntityQueryService.class);

    private final TestEntityRepository testEntityRepository;

    public TestEntityQueryService(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
    }

    /**
     * Return a {@link List} of {@link TestEntity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TestEntity> findByCriteria(TestEntityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TestEntity> specification = createSpecification(criteria);
        return testEntityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TestEntity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TestEntity> findByCriteria(TestEntityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TestEntity> specification = createSpecification(criteria);
        return testEntityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TestEntityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TestEntity> specification = createSpecification(criteria);
        return testEntityRepository.count(specification);
    }

    /**
     * Function to convert {@link TestEntityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TestEntity> createSpecification(TestEntityCriteria criteria) {
        Specification<TestEntity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TestEntity_.id));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), TestEntity_.fileName));
            }
        }
        return specification;
    }
}
