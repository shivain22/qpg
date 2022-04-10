package com.qpg.repository;

import com.qpg.domain.CategoryMaster;
import com.qpg.domain.QuestionTypeCategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionTypeCategoryMasterRepository extends JpaRepository<QuestionTypeCategoryMaster, Long>, JpaSpecificationExecutor<QuestionTypeCategoryMaster> {
}
