package com.qpg.service;

import com.qpg.domain.ExamMaster;
import com.qpg.domain.QuestionMaster;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionMaster}.
 */
public interface QuestionMasterService {

    /**
     * Save a questionMaster.
     *
     * @param questionMaster the entity to save.
     * @return the persisted entity.
     */
    QuestionMaster save(QuestionMaster questionMaster);

    /**
     * Get all the questionMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionMaster> findAll(Pageable pageable);


    /**
     * Get the "id" questionMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionMaster> findOne(Long id);

    /**
     * Delete the "id" questionMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * Delete the "id" questionMaster.
     *
     * @param id the id of the entity.
     */
    List<QuestionMaster> findRandomQuestions(Long questionTypeMasterId, int totalQuestions);


    List<QuestionMaster> findRandomChoiceQuestions(long questionTypeMasterId,String selectedQuestions, int totalQuestions);

    List<QuestionMaster> findRandomChoiceQuestionsH2(long questionTypeMasterId,Long[] selectedQuestions, int totalQuestions);

    List<QuestionMaster> findRandomChoiceQuestionsH2(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, ExamMaster examMaster);

}
