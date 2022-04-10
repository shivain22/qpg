package com.qpg.service;

import com.qpg.domain.ExamQuestionPaperDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ExamQuestionPaperDetail}.
 */
public interface ExamQuestionPaperDetailService {

    /**
     * Save a examQuestionPaperDetail.
     *
     * @param examQuestionPaperDetail the entity to save.
     * @return the persisted entity.
     */
    ExamQuestionPaperDetail save(ExamQuestionPaperDetail examQuestionPaperDetail);

    /**
     * Get all the examQuestionPaperDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamQuestionPaperDetail> findAll(Pageable pageable);


    /**
     * Get the "id" examQuestionPaperDetail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamQuestionPaperDetail> findOne(Long id);

    /**
     * Delete the "id" examQuestionPaperDetail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
