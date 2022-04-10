package com.qpg.service.impl;

import com.qpg.service.SubSubjectMasterService;
import com.qpg.domain.SubSubjectMaster;
import com.qpg.repository.SubSubjectMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SubSubjectMaster}.
 */
@Service
@Transactional
public class SubSubjectMasterServiceImpl implements SubSubjectMasterService {

    private final Logger log = LoggerFactory.getLogger(SubSubjectMasterServiceImpl.class);

    private final SubSubjectMasterRepository subSubjectMasterRepository;

    public SubSubjectMasterServiceImpl(SubSubjectMasterRepository subSubjectMasterRepository) {
        this.subSubjectMasterRepository = subSubjectMasterRepository;
    }

    @Override
    public SubSubjectMaster save(SubSubjectMaster subSubjectMaster) {
        log.debug("Request to save SubSubjectMaster : {}", subSubjectMaster);
        return subSubjectMasterRepository.save(subSubjectMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubSubjectMaster> findAll(Pageable pageable) {
        log.debug("Request to get all SubSubjectMasters");
        return subSubjectMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SubSubjectMaster> findOne(Long id) {
        log.debug("Request to get SubSubjectMaster : {}", id);
        return subSubjectMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubSubjectMaster : {}", id);
        subSubjectMasterRepository.deleteById(id);
    }
}
