package com.qpg.service;

import com.qpg.domain.DifficultyTypeMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DifficultyTypeMaster}.
 */
public interface DifficultyTypeMasterService {

    /**
     * Save a difficultyTypeMaster.
     *
     * @param difficultyTypeMaster the entity to save.
     * @return the persisted entity.
     */
    DifficultyTypeMaster save(DifficultyTypeMaster difficultyTypeMaster);

    /**
     * Get all the difficultyTypeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DifficultyTypeMaster> findAll(Pageable pageable);


    /**
     * Get the "id" difficultyTypeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DifficultyTypeMaster> findOne(Long id);

    /**
     * Delete the "id" difficultyTypeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "id" difficultyTypeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DifficultyTypeMaster> findDifficultyTypeMasterByName(String difficultyTypeName);
}
