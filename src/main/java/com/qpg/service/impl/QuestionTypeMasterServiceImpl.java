package com.qpg.service.impl;

import com.qpg.service.QuestionTypeMasterService;
import com.qpg.domain.QuestionTypeMaster;
import com.qpg.repository.QuestionTypeMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionTypeMaster}.
 */
@Service
@Transactional
public class QuestionTypeMasterServiceImpl implements QuestionTypeMasterService {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeMasterServiceImpl.class);

    private final QuestionTypeMasterRepository questionTypeMasterRepository;

    public QuestionTypeMasterServiceImpl(QuestionTypeMasterRepository questionTypeMasterRepository) {
        this.questionTypeMasterRepository = questionTypeMasterRepository;
    }

    @Override
    public QuestionTypeMaster save(QuestionTypeMaster questionTypeMaster) {
        log.debug("Request to save QuestionTypeMaster : {}", questionTypeMaster);
        return questionTypeMasterRepository.save(questionTypeMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionTypeMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionTypeMasters");
        return questionTypeMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionTypeMaster> findOne(Long id) {
        log.debug("Request to get QuestionTypeMaster : {}", id);
        return questionTypeMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionTypeMaster : {}", id);
        questionTypeMasterRepository.deleteById(id);
    }

    @Override
    public Optional<QuestionTypeMaster> findQuestionTypeMasterByName(String questionTypeName) {
        return questionTypeMasterRepository.findQuestionTypeMasterByName(questionTypeName);
    }

    @Override
    public Optional<QuestionTypeMaster> findQuestionTypeMasterByShortName(String shortName) {
        return questionTypeMasterRepository.findQuestionTypeMasterByShortName(shortName);
    }
}
