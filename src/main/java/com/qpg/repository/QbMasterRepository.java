package com.qpg.repository;

import com.qpg.domain.QbMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QbMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QbMasterRepository extends JpaRepository<QbMaster, Long>, JpaSpecificationExecutor<QbMaster> {
}
