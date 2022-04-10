package com.qpg.service.impl;

import com.qpg.service.AnswerMasterService;
import com.qpg.domain.AnswerMaster;
import com.qpg.repository.AnswerMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnswerMaster}.
 */
@Service
@Transactional
public class AnswerMasterServiceImpl implements AnswerMasterService {

    private final Logger log = LoggerFactory.getLogger(AnswerMasterServiceImpl.class);

    private final AnswerMasterRepository answerMasterRepository;

    public AnswerMasterServiceImpl(AnswerMasterRepository answerMasterRepository) {
        this.answerMasterRepository = answerMasterRepository;
    }

    @Override
    public AnswerMaster save(AnswerMaster answerMaster) {
        log.debug("Request to save AnswerMaster : {}", answerMaster);
        return answerMasterRepository.save(answerMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnswerMaster> findAll(Pageable pageable) {
        log.debug("Request to get all AnswerMasters");
        return answerMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerMaster> findOne(Long id) {
        log.debug("Request to get AnswerMaster : {}", id);
        return answerMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerMaster : {}", id);
        answerMasterRepository.deleteById(id);
    }
}
