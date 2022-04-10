package com.qpg.service;

import com.qpg.domain.SubCategoryMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SubCategoryMaster}.
 */
public interface SubCategoryMasterService {

    /**
     * Save a subCategoryMaster.
     *
     * @param subCategoryMaster the entity to save.
     * @return the persisted entity.
     */
    SubCategoryMaster save(SubCategoryMaster subCategoryMaster);

    /**
     * Get all the subCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubCategoryMaster> findAll(Pageable pageable);


    /**
     * Get the "id" subCategoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubCategoryMaster> findOne(Long id);

    /**
     * Delete the "id" subCategoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
