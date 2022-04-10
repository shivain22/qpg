package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QuestionBluePrintDetail;
import com.qpg.domain.QuestionTypeMaster;
import com.qpg.domain.QuestionBluePrintMaster;
import com.qpg.repository.QuestionBluePrintDetailsRepository;
import com.qpg.service.QuestionBluePrintDetailService;
import com.qpg.service.QuestionBluePrintDetailQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuestionBluePrintDetailResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionBluePrintDetailResourceIT {

    private static final Integer DEFAULT_TOTAL_QUESTIONS = 1;
    private static final Integer UPDATED_TOTAL_QUESTIONS = 2;
    private static final Integer SMALLER_TOTAL_QUESTIONS = 1 - 1;

    @Autowired
    private QuestionBluePrintDetailsRepository questionBluePrintDetailsRepository;

    @Autowired
    private QuestionBluePrintDetailService questionBluePrintDetailService;

    @Autowired
    private QuestionBluePrintDetailQueryService questionBluePrintDetailQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionBluePrintDetailsMockMvc;

    private QuestionBluePrintDetail questionBluePrintDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionBluePrintDetail createEntity(EntityManager em) {
        QuestionBluePrintDetail questionBluePrintDetail = new QuestionBluePrintDetail()
            .totalQuestions(DEFAULT_TOTAL_QUESTIONS);
        // Add required entity
        QuestionTypeMaster questionTypeMaster;
        if (TestUtil.findAll(em, QuestionTypeMaster.class).isEmpty()) {
            questionTypeMaster = QuestionTypeMasterResourceIT.createEntity(em);
            em.persist(questionTypeMaster);
            em.flush();
        } else {
            questionTypeMaster = TestUtil.findAll(em, QuestionTypeMaster.class).get(0);
        }
        questionBluePrintDetail.setQuestionTypeMaster(questionTypeMaster);
        // Add required entity
        QuestionBluePrintMaster questionBluePrintMaster;
        if (TestUtil.findAll(em, QuestionBluePrintMaster.class).isEmpty()) {
            questionBluePrintMaster = QuestionBluePrintMasterResourceIT.createEntity(em);
            em.persist(questionBluePrintMaster);
            em.flush();
        } else {
            questionBluePrintMaster = TestUtil.findAll(em, QuestionBluePrintMaster.class).get(0);
        }
        questionBluePrintDetail.setQuestionBluePrintMaster(questionBluePrintMaster);
        return questionBluePrintDetail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionBluePrintDetail createUpdatedEntity(EntityManager em) {
        QuestionBluePrintDetail questionBluePrintDetail = new QuestionBluePrintDetail()
            .totalQuestions(UPDATED_TOTAL_QUESTIONS);
        // Add required entity
        QuestionTypeMaster questionTypeMaster;
        if (TestUtil.findAll(em, QuestionTypeMaster.class).isEmpty()) {
            questionTypeMaster = QuestionTypeMasterResourceIT.createUpdatedEntity(em);
            em.persist(questionTypeMaster);
            em.flush();
        } else {
            questionTypeMaster = TestUtil.findAll(em, QuestionTypeMaster.class).get(0);
        }
        questionBluePrintDetail.setQuestionTypeMaster(questionTypeMaster);
        // Add required entity
        QuestionBluePrintMaster questionBluePrintMaster;
        if (TestUtil.findAll(em, QuestionBluePrintMaster.class).isEmpty()) {
            questionBluePrintMaster = QuestionBluePrintMasterResourceIT.createUpdatedEntity(em);
            em.persist(questionBluePrintMaster);
            em.flush();
        } else {
            questionBluePrintMaster = TestUtil.findAll(em, QuestionBluePrintMaster.class).get(0);
        }
        questionBluePrintDetail.setQuestionBluePrintMaster(questionBluePrintMaster);
        return questionBluePrintDetail;
    }

    @BeforeEach
    public void initTest() {
        questionBluePrintDetail = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionBluePrintDetails() throws Exception {
        int databaseSizeBeforeCreate = questionBluePrintDetailsRepository.findAll().size();
        // Create the QuestionBluePrintDetail
        restQuestionBluePrintDetailsMockMvc.perform(post("/api/question-blue-print-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintDetail)))
            .andExpect(status().isCreated());

        // Validate the QuestionBluePrintDetail in the database
        List<QuestionBluePrintDetail> questionBluePrintDetailList = questionBluePrintDetailsRepository.findAll();
        assertThat(questionBluePrintDetailList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionBluePrintDetail testQuestionBluePrintDetail = questionBluePrintDetailList.get(questionBluePrintDetailList.size() - 1);
        assertThat(testQuestionBluePrintDetail.getTotalQuestions()).isEqualTo(DEFAULT_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void createQuestionBluePrintDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionBluePrintDetailsRepository.findAll().size();

        // Create the QuestionBluePrintDetail with an existing ID
        questionBluePrintDetail.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionBluePrintDetailsMockMvc.perform(post("/api/question-blue-print-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintDetail)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBluePrintDetail in the database
        List<QuestionBluePrintDetail> questionBluePrintDetailList = questionBluePrintDetailsRepository.findAll();
        assertThat(questionBluePrintDetailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalQuestionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionBluePrintDetailsRepository.findAll().size();
        // set the field null
        questionBluePrintDetail.setTotalQuestions(null);

        // Create the QuestionBluePrintDetail, which fails.


        restQuestionBluePrintDetailsMockMvc.perform(post("/api/question-blue-print-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintDetail)))
            .andExpect(status().isBadRequest());

        List<QuestionBluePrintDetail> questionBluePrintDetailList = questionBluePrintDetailsRepository.findAll();
        assertThat(questionBluePrintDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetails() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBluePrintDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalQuestions").value(hasItem(DEFAULT_TOTAL_QUESTIONS)));
    }

    @Test
    @Transactional
    public void getQuestionBluePrintDetails() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get the questionBluePrintDetail
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details/{id}", questionBluePrintDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionBluePrintDetail.getId().intValue()))
            .andExpect(jsonPath("$.totalQuestions").value(DEFAULT_TOTAL_QUESTIONS));
    }


    @Test
    @Transactional
    public void getQuestionBluePrintDetailsByIdFiltering() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        Long id = questionBluePrintDetail.getId();

        defaultQuestionBluePrintDetailsShouldBeFound("id.equals=" + id);
        defaultQuestionBluePrintDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionBluePrintDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionBluePrintDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionBluePrintDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionBluePrintDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions equals to DEFAULT_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.equals=" + DEFAULT_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions equals to UPDATED_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.equals=" + UPDATED_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions not equals to DEFAULT_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.notEquals=" + DEFAULT_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions not equals to UPDATED_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.notEquals=" + UPDATED_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsInShouldWork() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions in DEFAULT_TOTAL_QUESTIONS or UPDATED_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.in=" + DEFAULT_TOTAL_QUESTIONS + "," + UPDATED_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions equals to UPDATED_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.in=" + UPDATED_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions is not null
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.specified=true");

        // Get all the questionBluePrintDetailsList where totalQuestions is null
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions is greater than or equal to DEFAULT_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.greaterThanOrEqual=" + DEFAULT_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions is greater than or equal to UPDATED_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.greaterThanOrEqual=" + UPDATED_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions is less than or equal to DEFAULT_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.lessThanOrEqual=" + DEFAULT_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions is less than or equal to SMALLER_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.lessThanOrEqual=" + SMALLER_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsLessThanSomething() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions is less than DEFAULT_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.lessThan=" + DEFAULT_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions is less than UPDATED_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.lessThan=" + UPDATED_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByTotalQuestionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);

        // Get all the questionBluePrintDetailsList where totalQuestions is greater than DEFAULT_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldNotBeFound("totalQuestions.greaterThan=" + DEFAULT_TOTAL_QUESTIONS);

        // Get all the questionBluePrintDetailsList where totalQuestions is greater than SMALLER_TOTAL_QUESTIONS
        defaultQuestionBluePrintDetailsShouldBeFound("totalQuestions.greaterThan=" + SMALLER_TOTAL_QUESTIONS);
    }


    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByQuestionTypeMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        QuestionTypeMaster questionTypeMaster = questionBluePrintDetail.getQuestionTypeMaster();
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);
        Long questionTypeMasterId = questionTypeMaster.getId();

        // Get all the questionBluePrintDetailsList where questionTypeMaster equals to questionTypeMasterId
        defaultQuestionBluePrintDetailsShouldBeFound("questionTypeMasterId.equals=" + questionTypeMasterId);

        // Get all the questionBluePrintDetailsList where questionTypeMaster equals to questionTypeMasterId + 1
        defaultQuestionBluePrintDetailsShouldNotBeFound("questionTypeMasterId.equals=" + (questionTypeMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionBluePrintDetailsByQuestionBluePrintMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        QuestionBluePrintMaster questionBluePrintMaster = questionBluePrintDetail.getQuestionBluePrintMaster();
        questionBluePrintDetailsRepository.saveAndFlush(questionBluePrintDetail);
        Long questionBluePrintMasterId = questionBluePrintMaster.getId();

        // Get all the questionBluePrintDetailsList where questionBluePrintMaster equals to questionBluePrintMasterId
        defaultQuestionBluePrintDetailsShouldBeFound("questionBluePrintMasterId.equals=" + questionBluePrintMasterId);

        // Get all the questionBluePrintDetailsList where questionBluePrintMaster equals to questionBluePrintMasterId + 1
        defaultQuestionBluePrintDetailsShouldNotBeFound("questionBluePrintMasterId.equals=" + (questionBluePrintMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionBluePrintDetailsShouldBeFound(String filter) throws Exception {
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBluePrintDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalQuestions").value(hasItem(DEFAULT_TOTAL_QUESTIONS)));

        // Check, that the count call also returns 1
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionBluePrintDetailsShouldNotBeFound(String filter) throws Exception {
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionBluePrintDetails() throws Exception {
        // Get the questionBluePrintDetail
        restQuestionBluePrintDetailsMockMvc.perform(get("/api/question-blue-print-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionBluePrintDetails() throws Exception {
        // Initialize the database
        questionBluePrintDetailService.save(questionBluePrintDetail);

        int databaseSizeBeforeUpdate = questionBluePrintDetailsRepository.findAll().size();

        // Update the questionBluePrintDetail
        QuestionBluePrintDetail updatedQuestionBluePrintDetail = questionBluePrintDetailsRepository.findById(questionBluePrintDetail.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionBluePrintDetail are not directly saved in db
        em.detach(updatedQuestionBluePrintDetail);
        updatedQuestionBluePrintDetail
            .totalQuestions(UPDATED_TOTAL_QUESTIONS);

        restQuestionBluePrintDetailsMockMvc.perform(put("/api/question-blue-print-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionBluePrintDetail)))
            .andExpect(status().isOk());

        // Validate the QuestionBluePrintDetail in the database
        List<QuestionBluePrintDetail> questionBluePrintDetailList = questionBluePrintDetailsRepository.findAll();
        assertThat(questionBluePrintDetailList).hasSize(databaseSizeBeforeUpdate);
        QuestionBluePrintDetail testQuestionBluePrintDetail = questionBluePrintDetailList.get(questionBluePrintDetailList.size() - 1);
        assertThat(testQuestionBluePrintDetail.getTotalQuestions()).isEqualTo(UPDATED_TOTAL_QUESTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionBluePrintDetails() throws Exception {
        int databaseSizeBeforeUpdate = questionBluePrintDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionBluePrintDetailsMockMvc.perform(put("/api/question-blue-print-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintDetail)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBluePrintDetail in the database
        List<QuestionBluePrintDetail> questionBluePrintDetailList = questionBluePrintDetailsRepository.findAll();
        assertThat(questionBluePrintDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionBluePrintDetails() throws Exception {
        // Initialize the database
        questionBluePrintDetailService.save(questionBluePrintDetail);

        int databaseSizeBeforeDelete = questionBluePrintDetailsRepository.findAll().size();

        // Delete the questionBluePrintDetail
        restQuestionBluePrintDetailsMockMvc.perform(delete("/api/question-blue-print-details/{id}", questionBluePrintDetail.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionBluePrintDetail> questionBluePrintDetailList = questionBluePrintDetailsRepository.findAll();
        assertThat(questionBluePrintDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
