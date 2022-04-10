package com.qpg.service;

import com.qpg.domain.SubjectMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SubjectMaster}.
 */
public interface SubjectMasterService {

    /**
     * Save a subjectMaster.
     *
     * @param subjectMaster the entity to save.
     * @return the persisted entity.
     */
    SubjectMaster save(SubjectMaster subjectMaster);

    /**
     * Get all the subjectMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubjectMaster> findAll(Pageable pageable);


    /**
     * Get the "id" subjectMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectMaster> findOne(Long id);

    /**
     * Delete the "id" subjectMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
