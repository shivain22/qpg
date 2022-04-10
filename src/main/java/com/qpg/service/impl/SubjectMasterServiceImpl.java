package com.qpg.service.impl;

import com.qpg.service.SubjectMasterService;
import com.qpg.domain.SubjectMaster;
import com.qpg.repository.SubjectMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SubjectMaster}.
 */
@Service
@Transactional
public class SubjectMasterServiceImpl implements SubjectMasterService {

    private final Logger log = LoggerFactory.getLogger(SubjectMasterServiceImpl.class);

    private final SubjectMasterRepository subjectMasterRepository;

    public SubjectMasterServiceImpl(SubjectMasterRepository subjectMasterRepository) {
        this.subjectMasterRepository = subjectMasterRepository;
    }

    @Override
    public SubjectMaster save(SubjectMaster subjectMaster) {
        log.debug("Request to save SubjectMaster : {}", subjectMaster);
        return subjectMasterRepository.save(subjectMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubjectMaster> findAll(Pageable pageable) {
        log.debug("Request to get all SubjectMasters");
        return subjectMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectMaster> findOne(Long id) {
        log.debug("Request to get SubjectMaster : {}", id);
        return subjectMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectMaster : {}", id);
        subjectMasterRepository.deleteById(id);
    }
}
