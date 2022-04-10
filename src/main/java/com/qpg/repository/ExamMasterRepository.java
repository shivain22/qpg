package com.qpg.repository;

import com.qpg.domain.ExamMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExamMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamMasterRepository extends JpaRepository<ExamMaster, Long>, JpaSpecificationExecutor<ExamMaster> {


}
