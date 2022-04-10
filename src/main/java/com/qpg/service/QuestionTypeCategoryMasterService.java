package com.qpg.service;

import com.qpg.domain.QuestionTypeCategoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionTypeCategoryMaster}.
 */
public interface QuestionTypeCategoryMasterService {

    /**
     * Save a questionTypeCategoryMaster.
     *
     * @param questionTypeCategoryMaster the entity to save.
     * @return the persisted entity.
     */
    QuestionTypeCategoryMaster save(QuestionTypeCategoryMaster questionTypeCategoryMaster);

    /**
     * Get all the questionTypeCategoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionTypeCategoryMaster> findAll(Pageable pageable);


    /**
     * Get the "id" questionTypeCategoryMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionTypeCategoryMaster> findOne(Long id);

    /**
     * Delete the "id" questionTypeCategoryMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
