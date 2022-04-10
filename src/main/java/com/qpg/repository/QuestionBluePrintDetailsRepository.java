package com.qpg.repository;

import com.qpg.domain.QuestionBluePrintDetail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionBluePrintDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionBluePrintDetailsRepository extends JpaRepository<QuestionBluePrintDetail, Long>, JpaSpecificationExecutor<QuestionBluePrintDetail> {
}
