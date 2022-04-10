package com.qpg.repository;

import com.qpg.domain.SubjectMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubjectMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectMasterRepository extends JpaRepository<SubjectMaster, Long>, JpaSpecificationExecutor<SubjectMaster> {
}
