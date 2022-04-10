package com.qpg.service.impl;

import com.qpg.domain.ExamQuestionPaperDetail;
import com.qpg.repository.ExamQuestionPaperDetailRepository;
import com.qpg.service.ExamQuestionPaperDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExamQuestionPaperDetail}.
 */
@Service
@Transactional
public class ExamQuestionPaperDetailServiceImpl implements ExamQuestionPaperDetailService {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionPaperDetailServiceImpl.class);

    private final ExamQuestionPaperDetailRepository examQuestionPaperDetailRepository;

    public ExamQuestionPaperDetailServiceImpl(ExamQuestionPaperDetailRepository examQuestionPaperDetailRepository) {
        this.examQuestionPaperDetailRepository = examQuestionPaperDetailRepository;
    }

    @Override
    public ExamQuestionPaperDetail save(ExamQuestionPaperDetail examQuestionPaperDetail) {
        log.debug("Request to save ExamQuestionPaperDetail : {}", examQuestionPaperDetail);
        return examQuestionPaperDetailRepository.save(examQuestionPaperDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamQuestionPaperDetail> findAll(Pageable pageable) {
        log.debug("Request to get all ExamQuestionPaperDetails");
        return examQuestionPaperDetailRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExamQuestionPaperDetail> findOne(Long id) {
        log.debug("Request to get ExamQuestionPaperDetail : {}", id);
        return examQuestionPaperDetailRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamQuestionPaperDetail : {}", id);
        examQuestionPaperDetailRepository.deleteById(id);
    }
}
