package com.qpg.service.impl;

import com.qpg.service.DepartmentMasterService;
import com.qpg.domain.DepartmentMaster;
import com.qpg.repository.DepartmentMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DepartmentMaster}.
 */
@Service
@Transactional
public class DepartmentMasterServiceImpl implements DepartmentMasterService {

    private final Logger log = LoggerFactory.getLogger(DepartmentMasterServiceImpl.class);

    private final DepartmentMasterRepository departmentMasterRepository;

    public DepartmentMasterServiceImpl(DepartmentMasterRepository departmentMasterRepository) {
        this.departmentMasterRepository = departmentMasterRepository;
    }

    @Override
    public DepartmentMaster save(DepartmentMaster departmentMaster) {
        log.debug("Request to save DepartmentMaster : {}", departmentMaster);
        return departmentMasterRepository.save(departmentMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentMaster> findAll(Pageable pageable) {
        log.debug("Request to get all DepartmentMasters");
        return departmentMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentMaster> findOne(Long id) {
        log.debug("Request to get DepartmentMaster : {}", id);
        return departmentMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DepartmentMaster : {}", id);
        departmentMasterRepository.deleteById(id);
    }
}
