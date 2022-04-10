package com.qpg.service;

import com.qpg.domain.CourseMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CourseMaster}.
 */
public interface CourseMasterService {

    /**
     * Save a courseMaster.
     *
     * @param courseMaster the entity to save.
     * @return the persisted entity.
     */
    CourseMaster save(CourseMaster courseMaster);

    /**
     * Get all the courseMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseMaster> findAll(Pageable pageable);


    /**
     * Get the "id" courseMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseMaster> findOne(Long id);

    /**
     * Delete the "id" courseMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
