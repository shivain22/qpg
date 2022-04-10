package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QuestionBankMaster;
import com.qpg.repository.QuestionBankMasterRepository;
import com.qpg.service.QuestionBankMasterService;
import com.qpg.service.dto.QuestionBankMasterCriteria;
import com.qpg.service.QuestionBankMasterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuestionBankMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionBankMasterResourceIT {

    private static final byte[] DEFAULT_QUESTION_BANK_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_QUESTION_BANK_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_QUESTION_BANK_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_QUESTION_BANK_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private QuestionBankMasterRepository questionBankMasterRepository;

    @Autowired
    private QuestionBankMasterService questionBankMasterService;

    @Autowired
    private QuestionBankMasterQueryService questionBankMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionBankMasterMockMvc;

    private QuestionBankMaster questionBankMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionBankMaster createEntity(EntityManager em) {
        QuestionBankMaster questionBankMaster = new QuestionBankMaster()
            .questionBankFile(DEFAULT_QUESTION_BANK_FILE)
            .questionBankFileContentType(DEFAULT_QUESTION_BANK_FILE_CONTENT_TYPE);
        return questionBankMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionBankMaster createUpdatedEntity(EntityManager em) {
        QuestionBankMaster questionBankMaster = new QuestionBankMaster()
            .questionBankFile(UPDATED_QUESTION_BANK_FILE)
            .questionBankFileContentType(UPDATED_QUESTION_BANK_FILE_CONTENT_TYPE);
        return questionBankMaster;
    }

    @BeforeEach
    public void initTest() {
        questionBankMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionBankMaster() throws Exception {
        int databaseSizeBeforeCreate = questionBankMasterRepository.findAll().size();
        // Create the QuestionBankMaster
        restQuestionBankMasterMockMvc.perform(post("/api/question-bank-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBankMaster)))
            .andExpect(status().isCreated());

        // Validate the QuestionBankMaster in the database
        List<QuestionBankMaster> questionBankMasterList = questionBankMasterRepository.findAll();
        assertThat(questionBankMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionBankMaster testQuestionBankMaster = questionBankMasterList.get(questionBankMasterList.size() - 1);
        assertThat(testQuestionBankMaster.getQuestionBankFile()).isEqualTo(DEFAULT_QUESTION_BANK_FILE);
        assertThat(testQuestionBankMaster.getQuestionBankFileContentType()).isEqualTo(DEFAULT_QUESTION_BANK_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createQuestionBankMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionBankMasterRepository.findAll().size();

        // Create the QuestionBankMaster with an existing ID
        questionBankMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionBankMasterMockMvc.perform(post("/api/question-bank-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBankMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBankMaster in the database
        List<QuestionBankMaster> questionBankMasterList = questionBankMasterRepository.findAll();
        assertThat(questionBankMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionBankMasters() throws Exception {
        // Initialize the database
        questionBankMasterRepository.saveAndFlush(questionBankMaster);

        // Get all the questionBankMasterList
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBankMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionBankFileContentType").value(hasItem(DEFAULT_QUESTION_BANK_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].questionBankFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_QUESTION_BANK_FILE))));
    }
    
    @Test
    @Transactional
    public void getQuestionBankMaster() throws Exception {
        // Initialize the database
        questionBankMasterRepository.saveAndFlush(questionBankMaster);

        // Get the questionBankMaster
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters/{id}", questionBankMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionBankMaster.getId().intValue()))
            .andExpect(jsonPath("$.questionBankFileContentType").value(DEFAULT_QUESTION_BANK_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.questionBankFile").value(Base64Utils.encodeToString(DEFAULT_QUESTION_BANK_FILE)));
    }


    @Test
    @Transactional
    public void getQuestionBankMastersByIdFiltering() throws Exception {
        // Initialize the database
        questionBankMasterRepository.saveAndFlush(questionBankMaster);

        Long id = questionBankMaster.getId();

        defaultQuestionBankMasterShouldBeFound("id.equals=" + id);
        defaultQuestionBankMasterShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionBankMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionBankMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionBankMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionBankMasterShouldNotBeFound("id.lessThan=" + id);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionBankMasterShouldBeFound(String filter) throws Exception {
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBankMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionBankFileContentType").value(hasItem(DEFAULT_QUESTION_BANK_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].questionBankFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_QUESTION_BANK_FILE))));

        // Check, that the count call also returns 1
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionBankMasterShouldNotBeFound(String filter) throws Exception {
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionBankMaster() throws Exception {
        // Get the questionBankMaster
        restQuestionBankMasterMockMvc.perform(get("/api/question-bank-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionBankMaster() throws Exception {
        // Initialize the database
        questionBankMasterService.save(questionBankMaster);

        int databaseSizeBeforeUpdate = questionBankMasterRepository.findAll().size();

        // Update the questionBankMaster
        QuestionBankMaster updatedQuestionBankMaster = questionBankMasterRepository.findById(questionBankMaster.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionBankMaster are not directly saved in db
        em.detach(updatedQuestionBankMaster);
        updatedQuestionBankMaster
            .questionBankFile(UPDATED_QUESTION_BANK_FILE)
            .questionBankFileContentType(UPDATED_QUESTION_BANK_FILE_CONTENT_TYPE);

        restQuestionBankMasterMockMvc.perform(put("/api/question-bank-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionBankMaster)))
            .andExpect(status().isOk());

        // Validate the QuestionBankMaster in the database
        List<QuestionBankMaster> questionBankMasterList = questionBankMasterRepository.findAll();
        assertThat(questionBankMasterList).hasSize(databaseSizeBeforeUpdate);
        QuestionBankMaster testQuestionBankMaster = questionBankMasterList.get(questionBankMasterList.size() - 1);
        assertThat(testQuestionBankMaster.getQuestionBankFile()).isEqualTo(UPDATED_QUESTION_BANK_FILE);
        assertThat(testQuestionBankMaster.getQuestionBankFileContentType()).isEqualTo(UPDATED_QUESTION_BANK_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionBankMaster() throws Exception {
        int databaseSizeBeforeUpdate = questionBankMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionBankMasterMockMvc.perform(put("/api/question-bank-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBankMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBankMaster in the database
        List<QuestionBankMaster> questionBankMasterList = questionBankMasterRepository.findAll();
        assertThat(questionBankMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionBankMaster() throws Exception {
        // Initialize the database
        questionBankMasterService.save(questionBankMaster);

        int databaseSizeBeforeDelete = questionBankMasterRepository.findAll().size();

        // Delete the questionBankMaster
        restQuestionBankMasterMockMvc.perform(delete("/api/question-bank-masters/{id}", questionBankMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionBankMaster> questionBankMasterList = questionBankMasterRepository.findAll();
        assertThat(questionBankMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
