package com.qpg.repository;

import com.qpg.domain.QuestionTypeMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Spring Data  repository for the QuestionTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionTypeMasterRepository extends JpaRepository<QuestionTypeMaster, Long>, JpaSpecificationExecutor<QuestionTypeMaster> {

    Optional<QuestionTypeMaster> findQuestionTypeMasterByName(String questionTypeName);

    Optional<QuestionTypeMaster> findQuestionTypeMasterByShortName(String shortName);

}
