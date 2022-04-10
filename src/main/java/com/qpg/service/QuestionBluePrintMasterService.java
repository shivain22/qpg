package com.qpg.service;

import com.qpg.domain.QuestionBluePrintMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionBluePrintMaster}.
 */
public interface QuestionBluePrintMasterService {

    /**
     * Save a questionBluePrintMaster.
     *
     * @param questionBluePrintMaster the entity to save.
     * @return the persisted entity.
     */
    QuestionBluePrintMaster save(QuestionBluePrintMaster questionBluePrintMaster);

    /**
     * Get all the questionBluePrintMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionBluePrintMaster> findAll(Pageable pageable);


    /**
     * Get the "id" questionBluePrintMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionBluePrintMaster> findOne(Long id);

    /**
     * Delete the "id" questionBluePrintMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
