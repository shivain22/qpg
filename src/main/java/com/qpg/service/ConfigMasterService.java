package com.qpg.service;

import com.qpg.domain.CollegeMaster;
import com.qpg.domain.ConfigMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CollegeMaster}.
 */
public interface ConfigMasterService {

    /**
     * Save a collegeMaster.
     *
     * @param configMaster the entity to save.
     * @return the persisted entity.
     */
    ConfigMaster save(ConfigMaster configMaster);

    /**
     * Get all the collegeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfigMaster> findAll(Pageable pageable);


    /**
     * Get the "id" collegeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigMaster> findOne(Long id);

    /**
     * Delete the "id" collegeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
