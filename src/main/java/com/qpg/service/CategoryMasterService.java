package com.qpg.service;

import com.qpg.domain.CategoryMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CategoryMaster}.
 */
public interface CategoryMasterService {

    /**
     * Save a categoryMaster.
     *
     * @param categoryMaster the entity to save.
     * @return the persisted entity.
     */
    CategoryMaster save(CategoryMaster categoryMaster);

    /**
     * Get all the categoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryMaster> findAll(Pageable pageable);


    /**
     * Get the "id" categoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryMaster> findOne(Long id);

    /**
     * Delete the "id" categoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
