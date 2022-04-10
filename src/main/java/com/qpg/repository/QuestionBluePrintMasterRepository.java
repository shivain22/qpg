package com.qpg.repository;

import com.qpg.domain.QuestionBluePrintMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionBluePrintMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionBluePrintMasterRepository extends JpaRepository<QuestionBluePrintMaster, Long>, JpaSpecificationExecutor<QuestionBluePrintMaster> {
}
