package com.qpg.service;

import com.qpg.domain.ExamMaster;
import com.qpg.domain.ExamQuestionPaperMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ExamQuestionPaperMaster}.
 */
public interface ExamQuestionPaperMasterService {

    /**
     * Save a examQuestionPaperMaster.
     *
     * @param examQuestionPaperMaster the entity to save.
     * @return the persisted entity.
     */
    ExamQuestionPaperMaster save(ExamQuestionPaperMaster examQuestionPaperMaster);

    /**
     * Get all the examQuestionPaperMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamQuestionPaperMaster> findAll(Pageable pageable);


    /**
     * Get the "id" examQuestionPaperMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamQuestionPaperMaster> findOne(Long id);

    /**
     * Delete the "id" examQuestionPaperMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Long countByExamMaster(ExamMaster examMaster);
}
