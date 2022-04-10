package com.qpg.repository;

import com.qpg.domain.QpBankUploadMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QpBankUploadMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QpBankUploadMasterRepository extends JpaRepository<QpBankUploadMaster, Long>, JpaSpecificationExecutor<QpBankUploadMaster> {
}
