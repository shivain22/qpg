package com.qpg.service;

import com.qpg.domain.CollegeMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CollegeMaster}.
 */
public interface CollegeMasterService {

    /**
     * Save a collegeMaster.
     *
     * @param collegeMaster the entity to save.
     * @return the persisted entity.
     */
    CollegeMaster save(CollegeMaster collegeMaster);

    /**
     * Get all the collegeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollegeMaster> findAll(Pageable pageable);


    /**
     * Get the "id" collegeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollegeMaster> findOne(Long id);

    /**
     * Delete the "id" collegeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
