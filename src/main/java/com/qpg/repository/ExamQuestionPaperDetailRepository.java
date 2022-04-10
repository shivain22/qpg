package com.qpg.repository;

import com.qpg.domain.ExamQuestionPaperDetail;
import com.qpg.domain.ExamQuestionPaperMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamQuestionPaperDetailRepository extends JpaRepository<ExamQuestionPaperDetail, Long>, JpaSpecificationExecutor<ExamQuestionPaperDetail> {
}
