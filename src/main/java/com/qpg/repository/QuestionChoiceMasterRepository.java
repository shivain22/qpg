package com.qpg.repository;

import com.qpg.domain.QuestionChoiceMaster;
import com.qpg.domain.QuestionTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the QuestionTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionChoiceMasterRepository extends JpaRepository<QuestionChoiceMaster, Long>, JpaSpecificationExecutor<QuestionChoiceMaster> {
}
