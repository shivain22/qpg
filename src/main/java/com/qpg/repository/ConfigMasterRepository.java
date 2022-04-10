package com.qpg.repository;

import com.qpg.domain.ConfigMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CollegeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigMasterRepository extends JpaRepository<ConfigMaster, Long>, JpaSpecificationExecutor<ConfigMaster> {
}
