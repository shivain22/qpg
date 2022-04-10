package com.qpg.service.impl;

import com.qpg.domain.QuestionBluePrintDetail;
import com.qpg.service.QuestionBluePrintDetailService;
import com.qpg.repository.QuestionBluePrintDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionBluePrintDetail}.
 */
@Service
@Transactional
public class QuestionBluePrintDetailServiceImpl implements QuestionBluePrintDetailService {

    private final Logger log = LoggerFactory.getLogger(QuestionBluePrintDetailServiceImpl.class);

    private final QuestionBluePrintDetailsRepository questionBluePrintDetailsRepository;

    public QuestionBluePrintDetailServiceImpl(QuestionBluePrintDetailsRepository questionBluePrintDetailsRepository) {
        this.questionBluePrintDetailsRepository = questionBluePrintDetailsRepository;
    }

    @Override
    public QuestionBluePrintDetail save(QuestionBluePrintDetail questionBluePrintDetail) {
        log.debug("Request to save QuestionBluePrintDetail : {}", questionBluePrintDetail);
        return questionBluePrintDetailsRepository.save(questionBluePrintDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionBluePrintDetail> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionBluePrintDetail");
        return questionBluePrintDetailsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionBluePrintDetail> findOne(Long id) {
        log.debug("Request to get QuestionBluePrintDetail : {}", id);
        return questionBluePrintDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionBluePrintDetail : {}", id);
        questionBluePrintDetailsRepository.deleteById(id);
    }
}
