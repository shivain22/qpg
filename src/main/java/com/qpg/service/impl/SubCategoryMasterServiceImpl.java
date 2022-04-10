package com.qpg.service.impl;

import com.qpg.service.SubCategoryMasterService;
import com.qpg.domain.SubCategoryMaster;
import com.qpg.repository.SubCategoryMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SubCategoryMaster}.
 */
@Service
@Transactional
public class SubCategoryMasterServiceImpl implements SubCategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(SubCategoryMasterServiceImpl.class);

    private final SubCategoryMasterRepository subCategoryMasterRepository;

    public SubCategoryMasterServiceImpl(SubCategoryMasterRepository subCategoryMasterRepository) {
        this.subCategoryMasterRepository = subCategoryMasterRepository;
    }

    @Override
    public SubCategoryMaster save(SubCategoryMaster subCategoryMaster) {
        log.debug("Request to save SubCategoryMaster : {}", subCategoryMaster);
        return subCategoryMasterRepository.save(subCategoryMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubCategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all SubCategoryMasters");
        return subCategoryMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SubCategoryMaster> findOne(Long id) {
        log.debug("Request to get SubCategoryMaster : {}", id);
        return subCategoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubCategoryMaster : {}", id);
        subCategoryMasterRepository.deleteById(id);
    }
}
