package com.qpg.repository;

import com.qpg.domain.CollegeMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CollegeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollegeMasterRepository extends JpaRepository<CollegeMaster, Long>, JpaSpecificationExecutor<CollegeMaster> {
}
