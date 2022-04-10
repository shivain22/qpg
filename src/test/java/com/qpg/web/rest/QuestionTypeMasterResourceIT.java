package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QuestionTypeMaster;
import com.qpg.domain.QuestionMaster;
import com.qpg.repository.QuestionTypeMasterRepository;
import com.qpg.service.QuestionTypeMasterService;
import com.qpg.service.dto.QuestionTypeMasterCriteria;
import com.qpg.service.QuestionTypeMasterQueryService;

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
 * Integration tests for the {@link QuestionTypeMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionTypeMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private QuestionTypeMasterRepository questionTypeMasterRepository;

    @Autowired
    private QuestionTypeMasterService questionTypeMasterService;

    @Autowired
    private QuestionTypeMasterQueryService questionTypeMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionTypeMasterMockMvc;

    private QuestionTypeMaster questionTypeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionTypeMaster createEntity(EntityManager em) {
        QuestionTypeMaster questionTypeMaster = new QuestionTypeMaster()
            .name(DEFAULT_NAME);
        return questionTypeMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionTypeMaster createUpdatedEntity(EntityManager em) {
        QuestionTypeMaster questionTypeMaster = new QuestionTypeMaster()
            .name(UPDATED_NAME);
        return questionTypeMaster;
    }

    @BeforeEach
    public void initTest() {
        questionTypeMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionTypeMaster() throws Exception {
        int databaseSizeBeforeCreate = questionTypeMasterRepository.findAll().size();
        // Create the QuestionTypeMaster
        restQuestionTypeMasterMockMvc.perform(post("/api/question-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeMaster)))
            .andExpect(status().isCreated());

        // Validate the QuestionTypeMaster in the database
        List<QuestionTypeMaster> questionTypeMasterList = questionTypeMasterRepository.findAll();
        assertThat(questionTypeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionTypeMaster testQuestionTypeMaster = questionTypeMasterList.get(questionTypeMasterList.size() - 1);
        assertThat(testQuestionTypeMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createQuestionTypeMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionTypeMasterRepository.findAll().size();

        // Create the QuestionTypeMaster with an existing ID
        questionTypeMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionTypeMasterMockMvc.perform(post("/api/question-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionTypeMaster in the database
        List<QuestionTypeMaster> questionTypeMasterList = questionTypeMasterRepository.findAll();
        assertThat(questionTypeMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionTypeMasterRepository.findAll().size();
        // set the field null
        questionTypeMaster.setName(null);

        // Create the QuestionTypeMaster, which fails.


        restQuestionTypeMasterMockMvc.perform(post("/api/question-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeMaster)))
            .andExpect(status().isBadRequest());

        List<QuestionTypeMaster> questionTypeMasterList = questionTypeMasterRepository.findAll();
        assertThat(questionTypeMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionTypeMasters() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionTypeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getQuestionTypeMaster() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get the questionTypeMaster
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters/{id}", questionTypeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionTypeMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getQuestionTypeMastersByIdFiltering() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        Long id = questionTypeMaster.getId();

        defaultQuestionTypeMasterShouldBeFound("id.equals=" + id);
        defaultQuestionTypeMasterShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionTypeMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionTypeMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionTypeMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionTypeMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionTypeMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList where name equals to DEFAULT_NAME
        defaultQuestionTypeMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the questionTypeMasterList where name equals to UPDATED_NAME
        defaultQuestionTypeMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionTypeMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList where name not equals to DEFAULT_NAME
        defaultQuestionTypeMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the questionTypeMasterList where name not equals to UPDATED_NAME
        defaultQuestionTypeMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionTypeMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultQuestionTypeMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the questionTypeMasterList where name equals to UPDATED_NAME
        defaultQuestionTypeMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionTypeMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList where name is not null
        defaultQuestionTypeMasterShouldBeFound("name.specified=true");

        // Get all the questionTypeMasterList where name is null
        defaultQuestionTypeMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionTypeMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList where name contains DEFAULT_NAME
        defaultQuestionTypeMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the questionTypeMasterList where name contains UPDATED_NAME
        defaultQuestionTypeMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQuestionTypeMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);

        // Get all the questionTypeMasterList where name does not contain DEFAULT_NAME
        defaultQuestionTypeMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the questionTypeMasterList where name does not contain UPDATED_NAME
        defaultQuestionTypeMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllQuestionTypeMastersByQuestionMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);
        QuestionMaster questionMaster = QuestionMasterResourceIT.createEntity(em);
        em.persist(questionMaster);
        em.flush();
        questionTypeMaster.addQuestionMaster(questionMaster);
        questionTypeMasterRepository.saveAndFlush(questionTypeMaster);
        Long questionMasterId = questionMaster.getId();

        // Get all the questionTypeMasterList where questionMaster equals to questionMasterId
        defaultQuestionTypeMasterShouldBeFound("questionMasterId.equals=" + questionMasterId);

        // Get all the questionTypeMasterList where questionMaster equals to questionMasterId + 1
        defaultQuestionTypeMasterShouldNotBeFound("questionMasterId.equals=" + (questionMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionTypeMasterShouldBeFound(String filter) throws Exception {
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionTypeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionTypeMasterShouldNotBeFound(String filter) throws Exception {
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionTypeMaster() throws Exception {
        // Get the questionTypeMaster
        restQuestionTypeMasterMockMvc.perform(get("/api/question-type-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionTypeMaster() throws Exception {
        // Initialize the database
        questionTypeMasterService.save(questionTypeMaster);

        int databaseSizeBeforeUpdate = questionTypeMasterRepository.findAll().size();

        // Update the questionTypeMaster
        QuestionTypeMaster updatedQuestionTypeMaster = questionTypeMasterRepository.findById(questionTypeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionTypeMaster are not directly saved in db
        em.detach(updatedQuestionTypeMaster);
        updatedQuestionTypeMaster
            .name(UPDATED_NAME);

        restQuestionTypeMasterMockMvc.perform(put("/api/question-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionTypeMaster)))
            .andExpect(status().isOk());

        // Validate the QuestionTypeMaster in the database
        List<QuestionTypeMaster> questionTypeMasterList = questionTypeMasterRepository.findAll();
        assertThat(questionTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        QuestionTypeMaster testQuestionTypeMaster = questionTypeMasterList.get(questionTypeMasterList.size() - 1);
        assertThat(testQuestionTypeMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = questionTypeMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionTypeMasterMockMvc.perform(put("/api/question-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionTypeMaster in the database
        List<QuestionTypeMaster> questionTypeMasterList = questionTypeMasterRepository.findAll();
        assertThat(questionTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionTypeMaster() throws Exception {
        // Initialize the database
        questionTypeMasterService.save(questionTypeMaster);

        int databaseSizeBeforeDelete = questionTypeMasterRepository.findAll().size();

        // Delete the questionTypeMaster
        restQuestionTypeMasterMockMvc.perform(delete("/api/question-type-masters/{id}", questionTypeMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionTypeMaster> questionTypeMasterList = questionTypeMasterRepository.findAll();
        assertThat(questionTypeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
