package com.qpg.service.impl;

import com.qpg.service.QuestionBluePrintMasterService;
import com.qpg.domain.QuestionBluePrintMaster;
import com.qpg.repository.QuestionBluePrintMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionBluePrintMaster}.
 */
@Service
@Transactional
public class QuestionBluePrintMasterServiceImpl implements QuestionBluePrintMasterService {

    private final Logger log = LoggerFactory.getLogger(QuestionBluePrintMasterServiceImpl.class);

    private final QuestionBluePrintMasterRepository questionBluePrintMasterRepository;

    public QuestionBluePrintMasterServiceImpl(QuestionBluePrintMasterRepository questionBluePrintMasterRepository) {
        this.questionBluePrintMasterRepository = questionBluePrintMasterRepository;
    }

    @Override
    public QuestionBluePrintMaster save(QuestionBluePrintMaster questionBluePrintMaster) {
        log.debug("Request to save QuestionBluePrintMaster : {}", questionBluePrintMaster);
        return questionBluePrintMasterRepository.save(questionBluePrintMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionBluePrintMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionBluePrintMasters");
        return questionBluePrintMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionBluePrintMaster> findOne(Long id) {
        log.debug("Request to get QuestionBluePrintMaster : {}", id);
        return questionBluePrintMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionBluePrintMaster : {}", id);
        questionBluePrintMasterRepository.deleteById(id);
    }
}
