package com.qpg.service.impl;

import com.qpg.service.DifficultyTypeMasterService;
import com.qpg.domain.DifficultyTypeMaster;
import com.qpg.repository.DifficultyTypeMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DifficultyTypeMaster}.
 */
@Service
@Transactional
public class DifficultyTypeMasterServiceImpl implements DifficultyTypeMasterService {

    private final Logger log = LoggerFactory.getLogger(DifficultyTypeMasterServiceImpl.class);

    private final DifficultyTypeMasterRepository difficultyTypeMasterRepository;

    public DifficultyTypeMasterServiceImpl(DifficultyTypeMasterRepository difficultyTypeMasterRepository) {
        this.difficultyTypeMasterRepository = difficultyTypeMasterRepository;
    }

    @Override
    public DifficultyTypeMaster save(DifficultyTypeMaster difficultyTypeMaster) {
        log.debug("Request to save DifficultyTypeMaster : {}", difficultyTypeMaster);
        return difficultyTypeMasterRepository.save(difficultyTypeMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DifficultyTypeMaster> findAll(Pageable pageable) {
        log.debug("Request to get all DifficultyTypeMasters");
        return difficultyTypeMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DifficultyTypeMaster> findOne(Long id) {
        log.debug("Request to get DifficultyTypeMaster : {}", id);
        return difficultyTypeMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DifficultyTypeMaster : {}", id);
        difficultyTypeMasterRepository.deleteById(id);
    }

    @Override
    public Optional<DifficultyTypeMaster> findDifficultyTypeMasterByName(String difficultyTypeName) {
        return difficultyTypeMasterRepository.findDifficultyTypeMasterByName(difficultyTypeName);
    }
}
