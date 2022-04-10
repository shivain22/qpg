package com.qpg.repository;

import com.qpg.domain.DepartmentMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DepartmentMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentMasterRepository extends JpaRepository<DepartmentMaster, Long>, JpaSpecificationExecutor<DepartmentMaster> {
}
