package com.qpg.service.impl;

import com.qpg.domain.QuestionTypeCategoryMaster;
import com.qpg.repository.QuestionTypeCategoryMasterRepository;
import com.qpg.service.QuestionTypeCategoryMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionTypeCategoryMaster}.
 */
@Service
@Transactional
public class QuestionTypeCategoryMasterServiceImpl implements QuestionTypeCategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeCategoryMasterServiceImpl.class);

    private final QuestionTypeCategoryMasterRepository questionTypeCategoryMasterRepository;

    public QuestionTypeCategoryMasterServiceImpl(QuestionTypeCategoryMasterRepository questionTypeCategoryMasterRepository) {
        this.questionTypeCategoryMasterRepository = questionTypeCategoryMasterRepository;
    }

    @Override
    public QuestionTypeCategoryMaster save(QuestionTypeCategoryMaster questionTypeQuestionTypeCategoryMaster) {
        log.debug("Request to save QuestionTypeCategoryMaster : {}", questionTypeQuestionTypeCategoryMaster);
        return questionTypeCategoryMasterRepository.save(questionTypeQuestionTypeCategoryMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionTypeCategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionTypeCategoryMasters");
        return questionTypeCategoryMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionTypeCategoryMaster> findOne(Long id) {
        log.debug("Request to get QuestionTypeCategoryMaster : {}", id);
        return questionTypeCategoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionTypeCategoryMaster : {}", id);
        questionTypeCategoryMasterRepository.deleteById(id);
    }
}
