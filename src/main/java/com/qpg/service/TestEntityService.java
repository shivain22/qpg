package com.qpg.service;

import com.qpg.domain.TestEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TestEntity}.
 */
public interface TestEntityService {

    /**
     * Save a testEntity.
     *
     * @param testEntity the entity to save.
     * @return the persisted entity.
     */
    TestEntity save(TestEntity testEntity);

    /**
     * Get all the testEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestEntity> findAll(Pageable pageable);


    /**
     * Get the "id" testEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestEntity> findOne(Long id);

    /**
     * Delete the "id" testEntity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
