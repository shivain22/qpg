package com.qpg.service;

import com.qpg.domain.QpBankUploadMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QpBankUploadMaster}.
 */
public interface QpBankUploadMasterService {

    /**
     * Save a qpBankUploadMaster.
     *
     * @param qpBankUploadMaster the entity to save.
     * @return the persisted entity.
     */
    QpBankUploadMaster save(QpBankUploadMaster qpBankUploadMaster);

    /**
     * Get all the qpBankUploadMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QpBankUploadMaster> findAll(Pageable pageable);


    /**
     * Get the "id" qpBankUploadMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QpBankUploadMaster> findOne(Long id);

    /**
     * Delete the "id" qpBankUploadMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
