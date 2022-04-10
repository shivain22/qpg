package com.qpg.service.impl;

import com.qpg.domain.ExamMaster;
import com.qpg.service.QuestionMasterService;
import com.qpg.domain.QuestionMaster;
import com.qpg.repository.QuestionMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionMaster}.
 */
@Service
@Transactional
public class QuestionMasterServiceImpl implements QuestionMasterService {

    private final Logger log = LoggerFactory.getLogger(QuestionMasterServiceImpl.class);

    private final QuestionMasterRepository questionMasterRepository;

    public QuestionMasterServiceImpl(QuestionMasterRepository questionMasterRepository) {
        this.questionMasterRepository = questionMasterRepository;
    }

    @Override
    public QuestionMaster save(QuestionMaster questionMaster) {
        log.debug("Request to save QuestionMaster : {}", questionMaster);
        return questionMasterRepository.save(questionMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionMasters");
        return questionMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionMaster> findOne(Long id) {
        log.debug("Request to get QuestionMaster : {}", id);
        return questionMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionMaster : {}", id);
        questionMasterRepository.deleteById(id);
    }

    @Override
    public List<QuestionMaster> findRandomQuestions(Long questionTypeMasterId, int totalQuestions) {
        return questionMasterRepository.findRandomQuestions(questionTypeMasterId,totalQuestions);
    }

    public List<QuestionMaster> findRandomChoiceQuestions(long questionTypeMasterId,String selectedQuestions, int totalQuestions){
        return questionMasterRepository.findRandomChoiceQuestions(questionTypeMasterId,selectedQuestions, totalQuestions);
    }

    public List<QuestionMaster> findRandomChoiceQuestionsH2(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions){
        return questionMasterRepository.findRandomChoiceQuestionsH2(questionTypeMasterId,selectedQuestions, totalQuestions);
    }

    public List<QuestionMaster> findRandomChoiceQuestionsH2(long questionTypeMasterId, Long[] selectedQuestions, int totalQuestions, ExamMaster examMaster){

        if(examMaster!=null){
            if(examMaster.getSubTopicMaster()!=null && examMaster.getSubTopicMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubTopic(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getSubTopicMaster().getId());
            }else if(examMaster.getTopicMaster()!=null && examMaster.getTopicMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2Topic(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getTopicMaster().getId());
            }else if(examMaster.getSubSubjectMaster()!=null && examMaster.getSubSubjectMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubSubject(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getSubSubjectMaster().getId());
            }else if(examMaster.getSubjectMaster()!=null && examMaster.getSubjectMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubSubject(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getSubjectMaster().getId());
            }else if(examMaster.getSubCategoryMaster()!=null && examMaster.getSubCategoryMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubSubject(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getSubCategoryMaster().getId());
            }else if(examMaster.getCategoryMaster()!=null && examMaster.getCategoryMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubSubject(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getCategoryMaster().getId());
            }else if(examMaster.getCourseMaster()!=null && examMaster.getCourseMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubSubject(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getCourseMaster().getId());
            }else if(examMaster.getDepartmentMaster()!=null && examMaster.getDepartmentMaster().getId()!=null){
                return questionMasterRepository.findRandomChoiceQuestionsH2SubSubject(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster.getDepartmentMaster().getId());
            }
        }


        return questionMasterRepository.findRandomChoiceQuestionsH2(questionTypeMasterId,selectedQuestions, totalQuestions,examMaster);
    }
}
