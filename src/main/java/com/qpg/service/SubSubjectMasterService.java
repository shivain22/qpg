package com.qpg.service;

import com.qpg.domain.SubSubjectMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SubSubjectMaster}.
 */
public interface SubSubjectMasterService {

    /**
     * Save a subSubjectMaster.
     *
     * @param subSubjectMaster the entity to save.
     * @return the persisted entity.
     */
    SubSubjectMaster save(SubSubjectMaster subSubjectMaster);

    /**
     * Get all the subSubjectMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubSubjectMaster> findAll(Pageable pageable);


    /**
     * Get the "id" subSubjectMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubSubjectMaster> findOne(Long id);

    /**
     * Delete the "id" subSubjectMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
