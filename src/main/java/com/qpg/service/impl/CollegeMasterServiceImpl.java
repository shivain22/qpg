package com.qpg.service.impl;

import com.qpg.service.CollegeMasterService;
import com.qpg.domain.CollegeMaster;
import com.qpg.repository.CollegeMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CollegeMaster}.
 */
@Service
@Transactional
public class CollegeMasterServiceImpl implements CollegeMasterService {

    private final Logger log = LoggerFactory.getLogger(CollegeMasterServiceImpl.class);

    private final CollegeMasterRepository collegeMasterRepository;

    public CollegeMasterServiceImpl(CollegeMasterRepository collegeMasterRepository) {
        this.collegeMasterRepository = collegeMasterRepository;
    }

    @Override
    public CollegeMaster save(CollegeMaster collegeMaster) {
        log.debug("Request to save CollegeMaster : {}", collegeMaster);
        return collegeMasterRepository.save(collegeMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollegeMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CollegeMasters");
        return collegeMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CollegeMaster> findOne(Long id) {
        log.debug("Request to get CollegeMaster : {}", id);
        return collegeMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CollegeMaster : {}", id);
        collegeMasterRepository.deleteById(id);
    }
}
