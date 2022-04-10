package com.qpg.service.impl;

import com.qpg.domain.ExamMaster;
import com.qpg.domain.ExamQuestionPaperMaster;
import com.qpg.repository.ExamQuestionPaperMasterRepository;
import com.qpg.service.ExamQuestionPaperMasterService;
import com.qpg.service.ExamQuestionPaperMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExamQuestionPaperMaster}.
 */
@Service
@Transactional
public class ExamQuestionPaperMasterServiceImpl implements ExamQuestionPaperMasterService {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionPaperMasterServiceImpl.class);

    private final ExamQuestionPaperMasterRepository examQuestionPaperMasterRepository;

    public ExamQuestionPaperMasterServiceImpl(ExamQuestionPaperMasterRepository examQuestionPaperMasterRepository) {
        this.examQuestionPaperMasterRepository = examQuestionPaperMasterRepository;
    }

    @Override
    public ExamQuestionPaperMaster save(ExamQuestionPaperMaster examQuestionPaperMaster) {
        log.debug("Request to save ExamQuestionPaperMaster : {}", examQuestionPaperMaster);
        return examQuestionPaperMasterRepository.save(examQuestionPaperMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamQuestionPaperMaster> findAll(Pageable pageable) {
        log.debug("Request to get all ExamQuestionPaperMasters");
        return examQuestionPaperMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExamQuestionPaperMaster> findOne(Long id) {
        log.debug("Request to get ExamQuestionPaperMaster : {}", id);
        return examQuestionPaperMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamQuestionPaperMaster : {}", id);
        examQuestionPaperMasterRepository.deleteById(id);
    }

    @Override
    public Long countByExamMaster(ExamMaster examMaster) {
        return examQuestionPaperMasterRepository.countByExamMaster(examMaster);
    }
}
