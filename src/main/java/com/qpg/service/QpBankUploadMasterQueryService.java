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

import com.qpg.domain.QpBankUploadMaster;
import com.qpg.domain.*; // for static metamodels
import com.qpg.repository.QpBankUploadMasterRepository;
import com.qpg.service.dto.QpBankUploadMasterCriteria;

/**
 * Service for executing complex queries for {@link QpBankUploadMaster} entities in the database.
 * The main input is a {@link QpBankUploadMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QpBankUploadMaster} or a {@link Page} of {@link QpBankUploadMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QpBankUploadMasterQueryService extends QueryService<QpBankUploadMaster> {

    private final Logger log = LoggerFactory.getLogger(QpBankUploadMasterQueryService.class);

    private final QpBankUploadMasterRepository qpBankUploadMasterRepository;

    public QpBankUploadMasterQueryService(QpBankUploadMasterRepository qpBankUploadMasterRepository) {
        this.qpBankUploadMasterRepository = qpBankUploadMasterRepository;
    }

    /**
     * Return a {@link List} of {@link QpBankUploadMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QpBankUploadMaster> findByCriteria(QpBankUploadMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QpBankUploadMaster> specification = createSpecification(criteria);
        return qpBankUploadMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QpBankUploadMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QpBankUploadMaster> findByCriteria(QpBankUploadMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QpBankUploadMaster> specification = createSpecification(criteria);
        return qpBankUploadMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QpBankUploadMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QpBankUploadMaster> specification = createSpecification(criteria);
        return qpBankUploadMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link QpBankUploadMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QpBankUploadMaster> createSpecification(QpBankUploadMasterCriteria criteria) {
        Specification<QpBankUploadMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QpBankUploadMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), QpBankUploadMaster_.name));
            }
            if (criteria.getSubTopicMasterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubTopicMasterId(),
                    root -> root.join(QpBankUploadMaster_.subTopicMaster, JoinType.LEFT).get(SubTopicMaster_.id)));
            }
        }
        return specification;
    }
}
