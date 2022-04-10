package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.AnswerMaster;
import com.qpg.domain.QuestionMaster;
import com.qpg.repository.AnswerMasterRepository;
import com.qpg.service.AnswerMasterService;
import com.qpg.service.dto.AnswerMasterCriteria;
import com.qpg.service.AnswerMasterQueryService;

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
 * Integration tests for the {@link AnswerMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnswerMasterResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CORRECT = false;
    private static final Boolean UPDATED_CORRECT = true;

    @Autowired
    private AnswerMasterRepository answerMasterRepository;

    @Autowired
    private AnswerMasterService answerMasterService;

    @Autowired
    private AnswerMasterQueryService answerMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerMasterMockMvc;

    private AnswerMaster answerMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerMaster createEntity(EntityManager em) {
        AnswerMaster answerMaster = new AnswerMaster()
            .text(DEFAULT_TEXT)
            .correct(DEFAULT_CORRECT);
        // Add required entity
        QuestionMaster questionMaster;
        if (TestUtil.findAll(em, QuestionMaster.class).isEmpty()) {
            questionMaster = QuestionMasterResourceIT.createEntity(em);
            em.persist(questionMaster);
            em.flush();
        } else {
            questionMaster = TestUtil.findAll(em, QuestionMaster.class).get(0);
        }
        answerMaster.setQuestionMaster(questionMaster);
        return answerMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerMaster createUpdatedEntity(EntityManager em) {
        AnswerMaster answerMaster = new AnswerMaster()
            .text(UPDATED_TEXT)
            .correct(UPDATED_CORRECT);
        // Add required entity
        QuestionMaster questionMaster;
        if (TestUtil.findAll(em, QuestionMaster.class).isEmpty()) {
            questionMaster = QuestionMasterResourceIT.createUpdatedEntity(em);
            em.persist(questionMaster);
            em.flush();
        } else {
            questionMaster = TestUtil.findAll(em, QuestionMaster.class).get(0);
        }
        answerMaster.setQuestionMaster(questionMaster);
        return answerMaster;
    }

    @BeforeEach
    public void initTest() {
        answerMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswerMaster() throws Exception {
        int databaseSizeBeforeCreate = answerMasterRepository.findAll().size();
        // Create the AnswerMaster
        restAnswerMasterMockMvc.perform(post("/api/answer-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerMaster)))
            .andExpect(status().isCreated());

        // Validate the AnswerMaster in the database
        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeCreate + 1);
        AnswerMaster testAnswerMaster = answerMasterList.get(answerMasterList.size() - 1);
        assertThat(testAnswerMaster.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testAnswerMaster.isCorrect()).isEqualTo(DEFAULT_CORRECT);
    }

    @Test
    @Transactional
    public void createAnswerMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerMasterRepository.findAll().size();

        // Create the AnswerMaster with an existing ID
        answerMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerMasterMockMvc.perform(post("/api/answer-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerMaster)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerMaster in the database
        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerMasterRepository.findAll().size();
        // set the field null
        answerMaster.setText(null);

        // Create the AnswerMaster, which fails.


        restAnswerMasterMockMvc.perform(post("/api/answer-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerMaster)))
            .andExpect(status().isBadRequest());

        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorrectIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerMasterRepository.findAll().size();
        // set the field null
        answerMaster.setCorrect(null);

        // Create the AnswerMaster, which fails.


        restAnswerMasterMockMvc.perform(post("/api/answer-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerMaster)))
            .andExpect(status().isBadRequest());

        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnswerMasters() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList
        restAnswerMasterMockMvc.perform(get("/api/answer-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].correct").value(hasItem(DEFAULT_CORRECT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAnswerMaster() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get the answerMaster
        restAnswerMasterMockMvc.perform(get("/api/answer-masters/{id}", answerMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answerMaster.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.correct").value(DEFAULT_CORRECT.booleanValue()));
    }


    @Test
    @Transactional
    public void getAnswerMastersByIdFiltering() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        Long id = answerMaster.getId();

        defaultAnswerMasterShouldBeFound("id.equals=" + id);
        defaultAnswerMasterShouldNotBeFound("id.notEquals=" + id);

        defaultAnswerMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnswerMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultAnswerMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnswerMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnswerMastersByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where text equals to DEFAULT_TEXT
        defaultAnswerMasterShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the answerMasterList where text equals to UPDATED_TEXT
        defaultAnswerMasterShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where text not equals to DEFAULT_TEXT
        defaultAnswerMasterShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the answerMasterList where text not equals to UPDATED_TEXT
        defaultAnswerMasterShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByTextIsInShouldWork() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultAnswerMasterShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the answerMasterList where text equals to UPDATED_TEXT
        defaultAnswerMasterShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where text is not null
        defaultAnswerMasterShouldBeFound("text.specified=true");

        // Get all the answerMasterList where text is null
        defaultAnswerMasterShouldNotBeFound("text.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnswerMastersByTextContainsSomething() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where text contains DEFAULT_TEXT
        defaultAnswerMasterShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the answerMasterList where text contains UPDATED_TEXT
        defaultAnswerMasterShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByTextNotContainsSomething() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where text does not contain DEFAULT_TEXT
        defaultAnswerMasterShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the answerMasterList where text does not contain UPDATED_TEXT
        defaultAnswerMasterShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }


    @Test
    @Transactional
    public void getAllAnswerMastersByCorrectIsEqualToSomething() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where correct equals to DEFAULT_CORRECT
        defaultAnswerMasterShouldBeFound("correct.equals=" + DEFAULT_CORRECT);

        // Get all the answerMasterList where correct equals to UPDATED_CORRECT
        defaultAnswerMasterShouldNotBeFound("correct.equals=" + UPDATED_CORRECT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByCorrectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where correct not equals to DEFAULT_CORRECT
        defaultAnswerMasterShouldNotBeFound("correct.notEquals=" + DEFAULT_CORRECT);

        // Get all the answerMasterList where correct not equals to UPDATED_CORRECT
        defaultAnswerMasterShouldBeFound("correct.notEquals=" + UPDATED_CORRECT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByCorrectIsInShouldWork() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where correct in DEFAULT_CORRECT or UPDATED_CORRECT
        defaultAnswerMasterShouldBeFound("correct.in=" + DEFAULT_CORRECT + "," + UPDATED_CORRECT);

        // Get all the answerMasterList where correct equals to UPDATED_CORRECT
        defaultAnswerMasterShouldNotBeFound("correct.in=" + UPDATED_CORRECT);
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByCorrectIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerMasterRepository.saveAndFlush(answerMaster);

        // Get all the answerMasterList where correct is not null
        defaultAnswerMasterShouldBeFound("correct.specified=true");

        // Get all the answerMasterList where correct is null
        defaultAnswerMasterShouldNotBeFound("correct.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswerMastersByQuestionMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        QuestionMaster questionMaster = answerMaster.getQuestionMaster();
        answerMasterRepository.saveAndFlush(answerMaster);
        Long questionMasterId = questionMaster.getId();

        // Get all the answerMasterList where questionMaster equals to questionMasterId
        defaultAnswerMasterShouldBeFound("questionMasterId.equals=" + questionMasterId);

        // Get all the answerMasterList where questionMaster equals to questionMasterId + 1
        defaultAnswerMasterShouldNotBeFound("questionMasterId.equals=" + (questionMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnswerMasterShouldBeFound(String filter) throws Exception {
        restAnswerMasterMockMvc.perform(get("/api/answer-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].correct").value(hasItem(DEFAULT_CORRECT.booleanValue())));

        // Check, that the count call also returns 1
        restAnswerMasterMockMvc.perform(get("/api/answer-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnswerMasterShouldNotBeFound(String filter) throws Exception {
        restAnswerMasterMockMvc.perform(get("/api/answer-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnswerMasterMockMvc.perform(get("/api/answer-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAnswerMaster() throws Exception {
        // Get the answerMaster
        restAnswerMasterMockMvc.perform(get("/api/answer-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswerMaster() throws Exception {
        // Initialize the database
        answerMasterService.save(answerMaster);

        int databaseSizeBeforeUpdate = answerMasterRepository.findAll().size();

        // Update the answerMaster
        AnswerMaster updatedAnswerMaster = answerMasterRepository.findById(answerMaster.getId()).get();
        // Disconnect from session so that the updates on updatedAnswerMaster are not directly saved in db
        em.detach(updatedAnswerMaster);
        updatedAnswerMaster
            .text(UPDATED_TEXT)
            .correct(UPDATED_CORRECT);

        restAnswerMasterMockMvc.perform(put("/api/answer-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnswerMaster)))
            .andExpect(status().isOk());

        // Validate the AnswerMaster in the database
        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeUpdate);
        AnswerMaster testAnswerMaster = answerMasterList.get(answerMasterList.size() - 1);
        assertThat(testAnswerMaster.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testAnswerMaster.isCorrect()).isEqualTo(UPDATED_CORRECT);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswerMaster() throws Exception {
        int databaseSizeBeforeUpdate = answerMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerMasterMockMvc.perform(put("/api/answer-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerMaster)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerMaster in the database
        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnswerMaster() throws Exception {
        // Initialize the database
        answerMasterService.save(answerMaster);

        int databaseSizeBeforeDelete = answerMasterRepository.findAll().size();

        // Delete the answerMaster
        restAnswerMasterMockMvc.perform(delete("/api/answer-masters/{id}", answerMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnswerMaster> answerMasterList = answerMasterRepository.findAll();
        assertThat(answerMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
