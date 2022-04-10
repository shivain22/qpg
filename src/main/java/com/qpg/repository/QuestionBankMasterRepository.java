package com.qpg.repository;

import com.qpg.domain.QuestionBankMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionBankMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionBankMasterRepository extends JpaRepository<QuestionBankMaster, Long>, JpaSpecificationExecutor<QuestionBankMaster> {
}
