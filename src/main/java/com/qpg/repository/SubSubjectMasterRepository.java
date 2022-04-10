package com.qpg.repository;

import com.qpg.domain.SubSubjectMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubSubjectMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubSubjectMasterRepository extends JpaRepository<SubSubjectMaster, Long>, JpaSpecificationExecutor<SubSubjectMaster> {
}
