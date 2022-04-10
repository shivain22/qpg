package com.qpg.service;

import com.qpg.domain.QbMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QbMaster}.
 */
public interface QbMasterService {

    /**
     * Save a qbMaster.
     *
     * @param qbMaster the entity to save.
     * @return the persisted entity.
     */
    QbMaster save(QbMaster qbMaster);

    /**
     * Get all the qbMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QbMaster> findAll(Pageable pageable);


    /**
     * Get the "id" qbMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QbMaster> findOne(Long id);

    /**
     * Delete the "id" qbMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
