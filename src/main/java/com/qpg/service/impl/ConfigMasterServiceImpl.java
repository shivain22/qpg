package com.qpg.service.impl;

import com.qpg.domain.CollegeMaster;
import com.qpg.domain.ConfigMaster;
import com.qpg.repository.CollegeMasterRepository;
import com.qpg.repository.ConfigMasterRepository;
import com.qpg.service.CollegeMasterService;
import com.qpg.service.ConfigMasterService;
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
public class ConfigMasterServiceImpl implements ConfigMasterService {

    private final Logger log = LoggerFactory.getLogger(ConfigMasterServiceImpl.class);

    private final ConfigMasterRepository configMasterRepository;

    public ConfigMasterServiceImpl(ConfigMasterRepository configeMasterRepository) {
        this.configMasterRepository = configeMasterRepository;
    }

    @Override
    public ConfigMaster save(ConfigMaster configMaster) {
        log.debug("Request to save ConfigMaster : {}", configMaster);
        return configMasterRepository.save(configMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfigMaster> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigMasters");
        return configMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigMaster> findOne(Long id) {
        log.debug("Request to get ConfigMaster : {}", id);
        return configMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigMaster : {}", id);
        configMasterRepository.deleteById(id);
    }
}
