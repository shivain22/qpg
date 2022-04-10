package com.qpg.service;

import com.qpg.domain.QuestionTypeMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionTypeMaster}.
 */
public interface QuestionTypeMasterService {

    /**
     * Save a questionTypeMaster.
     *
     * @param questionTypeMaster the entity to save.
     * @return the persisted entity.
     */
    QuestionTypeMaster save(QuestionTypeMaster questionTypeMaster);

    /**
     * Get all the questionTypeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionTypeMaster> findAll(Pageable pageable);


    /**
     * Get the "id" questionTypeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionTypeMaster> findOne(Long id);

    /**
     * Delete the "id" questionTypeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<QuestionTypeMaster> findQuestionTypeMasterByName(String questionTypeName);

    Optional<QuestionTypeMaster> findQuestionTypeMasterByShortName(String shortNaqme);

}
