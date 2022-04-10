package com.qpg.repository;

import com.qpg.domain.ExamMaster;
import com.qpg.domain.QuestionMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the QuestionMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionMasterRepository extends JpaRepository<QuestionMaster, Long>, JpaSpecificationExecutor<QuestionMaster> {

    @Query (nativeQuery=true, value = "SELECT * FROM question_master where question_type_master_id=?1 order by rand() limit ?2 ")
    List<QuestionMaster> findRandomQuestions(long questionTypeMasterId, int totalQuestions);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master where question_type_master_id=?1 and id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestions(long questionTypeMasterId,String selectedQuestions, int totalQuestions);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master where question_type_master_id=?1  and id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2(long questionTypeMasterId,Long[] selectedQuestions, int totalQuestions);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master where question_type_master_id=?1 and sub_topic_master_id=?4 and id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, ExamMaster examMaster);


    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm where qm.question_type_master_id=?1 and qm.sub_topic_master_id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2SubTopic(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions,Long subTopicMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm where qm.sub_topic_master_id=stm.id and  stm.topic_master_id=tm.id and tm.id=?4 and qm.question_type_master_id=?1 and  qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2Topic(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions,Long topicMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm,sub_subject_master ssm where qm.sub_topic_master_id=stm.id and   stm.topic_master_id=tm.id and tm.sub_subject_master_id=ssm.id and  qm.question_type_master_id=?1 and ssm.id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2SubSubject(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions,Long subSubjectMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm,sub_subject_master ssm,subject_master sm  where qm.sub_topic_master_id=stm.id and   stm.topic_master_id=tm.id and tm.sub_subject_master_id=ssm.id and  ssm.subject_master_id=sm.id and qm.question_type_master_id=?1 and sm.id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2Subject(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, Long subjectMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm,sub_subject_master ssm,subject_master sm,sub_category_master scm where qm.sub_topic_master_id=stm.id and   stm.topic_master_id=tm.id and tm.sub_subject_master_id=ssm.id and  ssm.subject_master_id=sm.id and sm.sub_category_master_id=scm.id and qm.question_type_master_id=?1 and scm.id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2SubCategory(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, Long subCategoryMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm,sub_subject_master ssm,subject_master sm,sub_category_master scm,category_master cm where qm.sub_topic_master_id=stm.id and   stm.topic_master_id=tm.id and tm.sub_subject_master_id=ssm.id and  ssm.subject_master_id=sm.id and sm.sub_category_master_id=scm.id and scm.category_master_id=cm.id and qm.question_type_master_id=?1 and cm.id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2Category(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, Long categoryMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm,sub_subject_master ssm,subject_master sm,sub_category_master scm,category_master cm, course_master cm2  where qm.sub_topic_master_id=stm.id and   stm.topic_master_id=tm.id and tm.sub_subject_master_id=ssm.id and  ssm.subject_master_id=sm.id and sm.sub_category_master_id=scm.id and scm.category_master_id=cm.id and cm.course_master_id=cm2.id and qm.question_type_master_id=?1 and cm2.id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2Course(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, Long courseMasterId);

    @Query (nativeQuery=true, value = "SELECT * FROM question_master qm,sub_topic_master stm, topic_master tm,sub_subject_master ssm,subject_master sm,sub_category_master scm,category_master cm, course_master cm2,department_master dm where qm.sub_topic_master_id=stm.id and   stm.topic_master_id=tm.id and tm.sub_subject_master_id=ssm.id and  ssm.subject_master_id=sm.id and sm.sub_category_master_id=scm.id and scm.category_master_id=cm.id and cm.course_master_id=cm2.id and cm2.department_id=dm.id and qm.question_type_master_id=?1 and dm.id=?4 and qm.id not in (?2) order by rand() limit ?3  ")
    List<QuestionMaster> findRandomChoiceQuestionsH2Department(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, Long departmentId);
}
