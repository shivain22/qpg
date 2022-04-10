package com.qpg.repository;

import com.qpg.domain.CategoryMaster;
import com.qpg.domain.ExamMaster;
import com.qpg.domain.ExamQuestionPaperMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamQuestionPaperMasterRepository extends JpaRepository<ExamQuestionPaperMaster, Long>, JpaSpecificationExecutor<ExamQuestionPaperMaster> {

    @Query
    Long countByExamMaster(ExamMaster examMaster);
}
