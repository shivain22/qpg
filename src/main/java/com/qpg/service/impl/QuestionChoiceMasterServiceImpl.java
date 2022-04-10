package com.qpg.service.impl;

import com.qpg.domain.QuestionChoiceMaster;
import com.qpg.repository.QuestionChoiceMasterRepository;
import com.qpg.service.QuestionChoiceMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionChoiceMaster}.
 */
@Service
@Transactional
public class QuestionChoiceMasterServiceImpl implements QuestionChoiceMasterService {

    private final Logger log = LoggerFactory.getLogger(QuestionChoiceMasterServiceImpl.class);

    private final QuestionChoiceMasterRepository questionChoiceMasterRepository;

    public QuestionChoiceMasterServiceImpl(QuestionChoiceMasterRepository questionChoiceMasterRepository) {
        this.questionChoiceMasterRepository = questionChoiceMasterRepository;
    }

    @Override
    public QuestionChoiceMaster save(QuestionChoiceMaster questionChoiceMaster) {
        log.debug("Request to save QuestionChoiceMaster : {}", questionChoiceMaster);
        return questionChoiceMasterRepository.save(questionChoiceMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionChoiceMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionChoiceMasters");
        return questionChoiceMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionChoiceMaster> findOne(Long id) {
        log.debug("Request to get QuestionChoiceMaster : {}", id);
        return questionChoiceMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionChoiceMaster : {}", id);
        questionChoiceMasterRepository.deleteById(id);
    }

}
