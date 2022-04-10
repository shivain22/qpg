package com.qpg.service;

import com.qpg.domain.AnswerMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AnswerMaster}.
 */
public interface AnswerMasterService {

    /**
     * Save a answerMaster.
     *
     * @param answerMaster the entity to save.
     * @return the persisted entity.
     */
    AnswerMaster save(AnswerMaster answerMaster);

    /**
     * Get all the answerMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnswerMaster> findAll(Pageable pageable);


    /**
     * Get the "id" answerMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerMaster> findOne(Long id);

    /**
     * Delete the "id" answerMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
