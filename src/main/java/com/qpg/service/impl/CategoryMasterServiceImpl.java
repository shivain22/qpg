package com.qpg.service.impl;

import com.qpg.service.CategoryMasterService;
import com.qpg.domain.CategoryMaster;
import com.qpg.repository.CategoryMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryMaster}.
 */
@Service
@Transactional
public class CategoryMasterServiceImpl implements CategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(CategoryMasterServiceImpl.class);

    private final CategoryMasterRepository categoryMasterRepository;

    public CategoryMasterServiceImpl(CategoryMasterRepository categoryMasterRepository) {
        this.categoryMasterRepository = categoryMasterRepository;
    }

    @Override
    public CategoryMaster save(CategoryMaster categoryMaster) {
        log.debug("Request to save CategoryMaster : {}", categoryMaster);
        return categoryMasterRepository.save(categoryMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryMasters");
        return categoryMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryMaster> findOne(Long id) {
        log.debug("Request to get CategoryMaster : {}", id);
        return categoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryMaster : {}", id);
        categoryMasterRepository.deleteById(id);
    }
}
