package com.qpg.repository;

import com.qpg.domain.AnswerMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnswerMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerMasterRepository extends JpaRepository<AnswerMaster, Long>, JpaSpecificationExecutor<AnswerMaster> {
}
