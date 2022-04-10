package com.qpg.service;

import com.qpg.domain.QuestionBluePrintDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionBluePrintDetail}.
 */
public interface QuestionBluePrintDetailService {

    /**
     * Save a questionBluePrintDetail.
     *
     * @param questionBluePrintDetail the entity to save.
     * @return the persisted entity.
     */
    QuestionBluePrintDetail save(QuestionBluePrintDetail questionBluePrintDetail);

    /**
     * Get all the questionBluePrintDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionBluePrintDetail> findAll(Pageable pageable);


    /**
     * Get the "id" questionBluePrintDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionBluePrintDetail> findOne(Long id);

    /**
     * Delete the "id" questionBluePrintDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
