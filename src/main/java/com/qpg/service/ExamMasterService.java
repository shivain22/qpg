package com.qpg.service;

import com.qpg.domain.ExamMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ExamMaster}.
 */
public interface ExamMasterService {

    /**
     * Save a examMaster.
     *
     * @param examMaster the entity to save.
     * @return the persisted entity.
     */
    ExamMaster save(ExamMaster examMaster);

    /**
     * Get all the examMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamMaster> findAll(Pageable pageable);


    /**
     * Get the "id" examMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamMaster> findOne(Long id);

    /**
     * Delete the "id" examMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void createQuestionPaper(ExamMaster examMaster);
}
