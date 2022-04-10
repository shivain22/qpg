package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QuestionBluePrintMaster;
import com.qpg.domain.ExamMaster;
import com.qpg.repository.QuestionBluePrintMasterRepository;
import com.qpg.service.QuestionBluePrintMasterService;
import com.qpg.service.dto.QuestionBluePrintMasterCriteria;
import com.qpg.service.QuestionBluePrintMasterQueryService;

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
 * Integration tests for the {@link QuestionBluePrintMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionBluePrintMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private QuestionBluePrintMasterRepository questionBluePrintMasterRepository;

    @Autowired
    private QuestionBluePrintMasterService questionBluePrintMasterService;

    @Autowired
    private QuestionBluePrintMasterQueryService questionBluePrintMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionBluePrintMasterMockMvc;

    private QuestionBluePrintMaster questionBluePrintMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionBluePrintMaster createEntity(EntityManager em) {
        QuestionBluePrintMaster questionBluePrintMaster = new QuestionBluePrintMaster()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        ExamMaster examMaster;
        if (TestUtil.findAll(em, ExamMaster.class).isEmpty()) {
            examMaster = ExamMasterResourceIT.createEntity(em);
            em.persist(examMaster);
            em.flush();
        } else {
            examMaster = TestUtil.findAll(em, ExamMaster.class).get(0);
        }
        //questionBluePrintMaster.setExamMaster(examMaster);
        return questionBluePrintMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionBluePrintMaster createUpdatedEntity(EntityManager em) {
        QuestionBluePrintMaster questionBluePrintMaster = new QuestionBluePrintMaster()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        ExamMaster examMaster;
        if (TestUtil.findAll(em, ExamMaster.class).isEmpty()) {
            examMaster = ExamMasterResourceIT.createUpdatedEntity(em);
            em.persist(examMaster);
            em.flush();
        } else {
            examMaster = TestUtil.findAll(em, ExamMaster.class).get(0);
        }
        //questionBluePrintMaster.setExamMaster(examMaster);
        return questionBluePrintMaster;
    }

    @BeforeEach
    public void initTest() {
        questionBluePrintMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionBluePrintMaster() throws Exception {
        int databaseSizeBeforeCreate = questionBluePrintMasterRepository.findAll().size();
        // Create the QuestionBluePrintMaster
        restQuestionBluePrintMasterMockMvc.perform(post("/api/question-blue-print-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintMaster)))
            .andExpect(status().isCreated());

        // Validate the QuestionBluePrintMaster in the database
        List<QuestionBluePrintMaster> questionBluePrintMasterList = questionBluePrintMasterRepository.findAll();
        assertThat(questionBluePrintMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionBluePrintMaster testQuestionBluePrintMaster = questionBluePrintMasterList.get(questionBluePrintMasterList.size() - 1);
        assertThat(testQuestionBluePrintMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuestionBluePrintMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createQuestionBluePrintMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionBluePrintMasterRepository.findAll().size();

        // Create the QuestionBluePrintMaster with an existing ID
        questionBluePrintMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionBluePrintMasterMockMvc.perform(post("/api/question-blue-print-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBluePrintMaster in the database
        List<QuestionBluePrintMaster> questionBluePrintMasterList = questionBluePrintMasterRepository.findAll();
        assertThat(questionBluePrintMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionBluePrintMasterRepository.findAll().size();
        // set the field null
        questionBluePrintMaster.setName(null);

        // Create the QuestionBluePrintMaster, which fails.


        restQuestionBluePrintMasterMockMvc.perform(post("/api/question-blue-print-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintMaster)))
            .andExpect(status().isBadRequest());

        List<QuestionBluePrintMaster> questionBluePrintMasterList = questionBluePrintMasterRepository.findAll();
        assertThat(questionBluePrintMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMasters() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBluePrintMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getQuestionBluePrintMaster() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get the questionBluePrintMaster
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters/{id}", questionBluePrintMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionBluePrintMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getQuestionBluePrintMastersByIdFiltering() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        Long id = questionBluePrintMaster.getId();

        defaultQuestionBluePrintMasterShouldBeFound("id.equals=" + id);
        defaultQuestionBluePrintMasterShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionBluePrintMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionBluePrintMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionBluePrintMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionBluePrintMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where name equals to DEFAULT_NAME
        defaultQuestionBluePrintMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the questionBluePrintMasterList where name equals to UPDATED_NAME
        defaultQuestionBluePrintMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where name not equals to DEFAULT_NAME
        defaultQuestionBluePrintMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the questionBluePrintMasterList where name not equals to UPDATED_NAME
        defaultQuestionBluePrintMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultQuestionBluePrintMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the questionBluePrintMasterList where name equals to UPDATED_NAME
        defaultQuestionBluePrintMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where name is not null
        defaultQuestionBluePrintMasterShouldBeFound("name.specified=true");

        // Get all the questionBluePrintMasterList where name is null
        defaultQuestionBluePrintMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where name contains DEFAULT_NAME
        defaultQuestionBluePrintMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the questionBluePrintMasterList where name contains UPDATED_NAME
        defaultQuestionBluePrintMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where name does not contain DEFAULT_NAME
        defaultQuestionBluePrintMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the questionBluePrintMasterList where name does not contain UPDATED_NAME
        defaultQuestionBluePrintMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where description equals to DEFAULT_DESCRIPTION
        defaultQuestionBluePrintMasterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the questionBluePrintMasterList where description equals to UPDATED_DESCRIPTION
        defaultQuestionBluePrintMasterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where description not equals to DEFAULT_DESCRIPTION
        defaultQuestionBluePrintMasterShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the questionBluePrintMasterList where description not equals to UPDATED_DESCRIPTION
        defaultQuestionBluePrintMasterShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultQuestionBluePrintMasterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the questionBluePrintMasterList where description equals to UPDATED_DESCRIPTION
        defaultQuestionBluePrintMasterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where description is not null
        defaultQuestionBluePrintMasterShouldBeFound("description.specified=true");

        // Get all the questionBluePrintMasterList where description is null
        defaultQuestionBluePrintMasterShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where description contains DEFAULT_DESCRIPTION
        defaultQuestionBluePrintMasterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the questionBluePrintMasterList where description contains UPDATED_DESCRIPTION
        defaultQuestionBluePrintMasterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);

        // Get all the questionBluePrintMasterList where description does not contain DEFAULT_DESCRIPTION
        defaultQuestionBluePrintMasterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the questionBluePrintMasterList where description does not contain UPDATED_DESCRIPTION
        defaultQuestionBluePrintMasterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


   /* @Test
    @Transactional
    public void getAllQuestionBluePrintMastersByExamMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExamMaster examMaster = questionBluePrintMaster.getExamMaster();
        questionBluePrintMasterRepository.saveAndFlush(questionBluePrintMaster);
        Long examMasterId = examMaster.getId();

        // Get all the questionBluePrintMasterList where examMaster equals to examMasterId
        defaultQuestionBluePrintMasterShouldBeFound("examMasterId.equals=" + examMasterId);

        // Get all the questionBluePrintMasterList where examMaster equals to examMasterId + 1
        defaultQuestionBluePrintMasterShouldNotBeFound("examMasterId.equals=" + (examMasterId + 1));
    }*/

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionBluePrintMasterShouldBeFound(String filter) throws Exception {
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionBluePrintMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionBluePrintMasterShouldNotBeFound(String filter) throws Exception {
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionBluePrintMaster() throws Exception {
        // Get the questionBluePrintMaster
        restQuestionBluePrintMasterMockMvc.perform(get("/api/question-blue-print-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionBluePrintMaster() throws Exception {
        // Initialize the database
        questionBluePrintMasterService.save(questionBluePrintMaster);

        int databaseSizeBeforeUpdate = questionBluePrintMasterRepository.findAll().size();

        // Update the questionBluePrintMaster
        QuestionBluePrintMaster updatedQuestionBluePrintMaster = questionBluePrintMasterRepository.findById(questionBluePrintMaster.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionBluePrintMaster are not directly saved in db
        em.detach(updatedQuestionBluePrintMaster);
        updatedQuestionBluePrintMaster
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restQuestionBluePrintMasterMockMvc.perform(put("/api/question-blue-print-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionBluePrintMaster)))
            .andExpect(status().isOk());

        // Validate the QuestionBluePrintMaster in the database
        List<QuestionBluePrintMaster> questionBluePrintMasterList = questionBluePrintMasterRepository.findAll();
        assertThat(questionBluePrintMasterList).hasSize(databaseSizeBeforeUpdate);
        QuestionBluePrintMaster testQuestionBluePrintMaster = questionBluePrintMasterList.get(questionBluePrintMasterList.size() - 1);
        assertThat(testQuestionBluePrintMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuestionBluePrintMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionBluePrintMaster() throws Exception {
        int databaseSizeBeforeUpdate = questionBluePrintMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionBluePrintMasterMockMvc.perform(put("/api/question-blue-print-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionBluePrintMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionBluePrintMaster in the database
        List<QuestionBluePrintMaster> questionBluePrintMasterList = questionBluePrintMasterRepository.findAll();
        assertThat(questionBluePrintMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionBluePrintMaster() throws Exception {
        // Initialize the database
        questionBluePrintMasterService.save(questionBluePrintMaster);

        int databaseSizeBeforeDelete = questionBluePrintMasterRepository.findAll().size();

        // Delete the questionBluePrintMaster
        restQuestionBluePrintMasterMockMvc.perform(delete("/api/question-blue-print-masters/{id}", questionBluePrintMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionBluePrintMaster> questionBluePrintMasterList = questionBluePrintMasterRepository.findAll();
        assertThat(questionBluePrintMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
