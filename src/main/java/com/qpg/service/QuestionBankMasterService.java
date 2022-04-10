package com.qpg.service;

import com.qpg.domain.QuestionBankMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionBankMaster}.
 */
public interface QuestionBankMasterService {

    /**
     * Save a questionBankMaster.
     *
     * @param questionBankMaster the entity to save.
     * @return the persisted entity.
     */
    QuestionBankMaster save(QuestionBankMaster questionBankMaster);

    /**
     * Get all the questionBankMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionBankMaster> findAll(Pageable pageable);


    /**
     * Get the "id" questionBankMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionBankMaster> findOne(Long id);

    /**
     * Delete the "id" questionBankMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
