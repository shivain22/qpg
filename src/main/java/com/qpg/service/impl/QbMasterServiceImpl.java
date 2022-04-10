package com.qpg.service.impl;

import com.qpg.service.QbMasterService;
import com.qpg.domain.QbMaster;
import com.qpg.repository.QbMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QbMaster}.
 */
@Service
@Transactional
public class QbMasterServiceImpl implements QbMasterService {

    private final Logger log = LoggerFactory.getLogger(QbMasterServiceImpl.class);

    private final QbMasterRepository qbMasterRepository;

    public QbMasterServiceImpl(QbMasterRepository qbMasterRepository) {
        this.qbMasterRepository = qbMasterRepository;
    }

    @Override
    public QbMaster save(QbMaster qbMaster) {
        log.debug("Request to save QbMaster : {}", qbMaster);
        return qbMasterRepository.save(qbMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QbMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QbMasters");
        return qbMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QbMaster> findOne(Long id) {
        log.debug("Request to get QbMaster : {}", id);
        return qbMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QbMaster : {}", id);
        qbMasterRepository.deleteById(id);
    }
}
