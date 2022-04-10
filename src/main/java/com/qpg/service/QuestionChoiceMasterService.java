package com.qpg.service;

import com.qpg.domain.QuestionChoiceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionChoiceMaster}.
 */
public interface QuestionChoiceMasterService {

    /**
     * Save a questionChoiceMaster.
     *
     * @param questionChoiceMaster the entity to save.
     * @return the persisted entity.
     */
    QuestionChoiceMaster save(QuestionChoiceMaster questionChoiceMaster);

    /**
     * Get all the questionChoiceMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionChoiceMaster> findAll(Pageable pageable);


    /**
     * Get the "id" questionChoiceMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionChoiceMaster> findOne(Long id);

    /**
     * Delete the "id" questionChoiceMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


}
