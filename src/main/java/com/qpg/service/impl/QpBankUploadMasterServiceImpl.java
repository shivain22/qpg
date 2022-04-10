package com.qpg.service.impl;

import com.qpg.service.QpBankUploadMasterService;
import com.qpg.domain.QpBankUploadMaster;
import com.qpg.repository.QpBankUploadMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QpBankUploadMaster}.
 */
@Service
@Transactional
public class QpBankUploadMasterServiceImpl implements QpBankUploadMasterService {

    private final Logger log = LoggerFactory.getLogger(QpBankUploadMasterServiceImpl.class);

    private final QpBankUploadMasterRepository qpBankUploadMasterRepository;

    public QpBankUploadMasterServiceImpl(QpBankUploadMasterRepository qpBankUploadMasterRepository) {
        this.qpBankUploadMasterRepository = qpBankUploadMasterRepository;
    }

    @Override
    public QpBankUploadMaster save(QpBankUploadMaster qpBankUploadMaster) {
        log.debug("Request to save QpBankUploadMaster : {}", qpBankUploadMaster);
        return qpBankUploadMasterRepository.save(qpBankUploadMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QpBankUploadMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QpBankUploadMasters");
        return qpBankUploadMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QpBankUploadMaster> findOne(Long id) {
        log.debug("Request to get QpBankUploadMaster : {}", id);
        return qpBankUploadMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QpBankUploadMaster : {}", id);
        qpBankUploadMasterRepository.deleteById(id);
    }
}
