package com.qpg.service.impl;

import com.qpg.service.CourseMasterService;
import com.qpg.domain.CourseMaster;
import com.qpg.repository.CourseMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseMaster}.
 */
@Service
@Transactional
public class CourseMasterServiceImpl implements CourseMasterService {

    private final Logger log = LoggerFactory.getLogger(CourseMasterServiceImpl.class);

    private final CourseMasterRepository courseMasterRepository;

    public CourseMasterServiceImpl(CourseMasterRepository courseMasterRepository) {
        this.courseMasterRepository = courseMasterRepository;
    }

    @Override
    public CourseMaster save(CourseMaster courseMaster) {
        log.debug("Request to save CourseMaster : {}", courseMaster);
        return courseMasterRepository.save(courseMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CourseMasters");
        return courseMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CourseMaster> findOne(Long id) {
        log.debug("Request to get CourseMaster : {}", id);
        return courseMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseMaster : {}", id);
        courseMasterRepository.deleteById(id);
    }
}
