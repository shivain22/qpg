package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QuestionMaster;
import com.qpg.domain.QuestionTypeMaster;
import com.qpg.domain.DifficultyTypeMaster;
import com.qpg.domain.SubTopicMaster;
import com.qpg.domain.QuestionMaster;
import com.qpg.domain.AnswerMaster;
import com.qpg.repository.QuestionMasterRepository;
import com.qpg.service.QuestionMasterService;
import com.qpg.service.dto.QuestionMasterCriteria;
import com.qpg.service.QuestionMasterQueryService;

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
 * Integration tests for the {@link QuestionMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionMasterResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHTAGE = 1D;
    private static final Double UPDATED_WEIGHTAGE = 2D;
    private static final Double SMALLER_WEIGHTAGE = 1D - 1D;

    @Autowired
    private QuestionMasterRepository questionMasterRepository;

    @Autowired
    private QuestionMasterService questionMasterService;

    @Autowired
    private QuestionMasterQueryService questionMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionMasterMockMvc;

    private QuestionMaster questionMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionMaster createEntity(EntityManager em) {
        QuestionMaster questionMaster = new QuestionMaster()
            .text(DEFAULT_TEXT)
            .weightage(DEFAULT_WEIGHTAGE);
        // Add required entity
        QuestionTypeMaster questionTypeMaster;
        if (TestUtil.findAll(em, QuestionTypeMaster.class).isEmpty()) {
            questionTypeMaster = QuestionTypeMasterResourceIT.createEntity(em);
            em.persist(questionTypeMaster);
            em.flush();
        } else {
            questionTypeMaster = TestUtil.findAll(em, QuestionTypeMaster.class).get(0);
        }
        questionMaster.setQuestionTypeMaster(questionTypeMaster);
        // Add required entity
        SubTopicMaster subTopicMaster;
        if (TestUtil.findAll(em, SubTopicMaster.class).isEmpty()) {
            subTopicMaster = SubTopicMasterResourceIT.createEntity(em);
            em.persist(subTopicMaster);
            em.flush();
        } else {
            subTopicMaster = TestUtil.findAll(em, SubTopicMaster.class).get(0);
        }
        questionMaster.setSubTopicMaster(subTopicMaster);
        return questionMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionMaster createUpdatedEntity(EntityManager em) {
        QuestionMaster questionMaster = new QuestionMaster()
            .text(UPDATED_TEXT)
            .weightage(UPDATED_WEIGHTAGE);
        // Add required entity
        QuestionTypeMaster questionTypeMaster;
        if (TestUtil.findAll(em, QuestionTypeMaster.class).isEmpty()) {
            questionTypeMaster = QuestionTypeMasterResourceIT.createUpdatedEntity(em);
            em.persist(questionTypeMaster);
            em.flush();
        } else {
            questionTypeMaster = TestUtil.findAll(em, QuestionTypeMaster.class).get(0);
        }
        questionMaster.setQuestionTypeMaster(questionTypeMaster);
        // Add required entity
        SubTopicMaster subTopicMaster;
        if (TestUtil.findAll(em, SubTopicMaster.class).isEmpty()) {
            subTopicMaster = SubTopicMasterResourceIT.createUpdatedEntity(em);
            em.persist(subTopicMaster);
            em.flush();
        } else {
            subTopicMaster = TestUtil.findAll(em, SubTopicMaster.class).get(0);
        }
        questionMaster.setSubTopicMaster(subTopicMaster);
        return questionMaster;
    }

    @BeforeEach
    public void initTest() {
        questionMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionMaster() throws Exception {
        int databaseSizeBeforeCreate = questionMasterRepository.findAll().size();
        // Create the QuestionMaster
        restQuestionMasterMockMvc.perform(post("/api/question-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionMaster)))
            .andExpect(status().isCreated());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionMaster testQuestionMaster = questionMasterList.get(questionMasterList.size() - 1);
        assertThat(testQuestionMaster.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQuestionMaster.getWeightage()).isEqualTo(DEFAULT_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void createQuestionMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionMasterRepository.findAll().size();

        // Create the QuestionMaster with an existing ID
        questionMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMasterMockMvc.perform(post("/api/question-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionMasters() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList
        restQuestionMasterMockMvc.perform(get("/api/question-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getQuestionMaster() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get the questionMaster
        restQuestionMasterMockMvc.perform(get("/api/question-masters/{id}", questionMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionMaster.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.weightage").value(DEFAULT_WEIGHTAGE.doubleValue()));
    }


    @Test
    @Transactional
    public void getQuestionMastersByIdFiltering() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        Long id = questionMaster.getId();

        defaultQuestionMasterShouldBeFound("id.equals=" + id);
        defaultQuestionMasterShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where text equals to DEFAULT_TEXT
        defaultQuestionMasterShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the questionMasterList where text equals to UPDATED_TEXT
        defaultQuestionMasterShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where text not equals to DEFAULT_TEXT
        defaultQuestionMasterShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the questionMasterList where text not equals to UPDATED_TEXT
        defaultQuestionMasterShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByTextIsInShouldWork() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultQuestionMasterShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the questionMasterList where text equals to UPDATED_TEXT
        defaultQuestionMasterShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where text is not null
        defaultQuestionMasterShouldBeFound("text.specified=true");

        // Get all the questionMasterList where text is null
        defaultQuestionMasterShouldNotBeFound("text.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuestionMastersByTextContainsSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where text contains DEFAULT_TEXT
        defaultQuestionMasterShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the questionMasterList where text contains UPDATED_TEXT
        defaultQuestionMasterShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByTextNotContainsSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where text does not contain DEFAULT_TEXT
        defaultQuestionMasterShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the questionMasterList where text does not contain UPDATED_TEXT
        defaultQuestionMasterShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage equals to DEFAULT_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.equals=" + DEFAULT_WEIGHTAGE);

        // Get all the questionMasterList where weightage equals to UPDATED_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.equals=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage not equals to DEFAULT_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.notEquals=" + DEFAULT_WEIGHTAGE);

        // Get all the questionMasterList where weightage not equals to UPDATED_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.notEquals=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsInShouldWork() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage in DEFAULT_WEIGHTAGE or UPDATED_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.in=" + DEFAULT_WEIGHTAGE + "," + UPDATED_WEIGHTAGE);

        // Get all the questionMasterList where weightage equals to UPDATED_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.in=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage is not null
        defaultQuestionMasterShouldBeFound("weightage.specified=true");

        // Get all the questionMasterList where weightage is null
        defaultQuestionMasterShouldNotBeFound("weightage.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage is greater than or equal to DEFAULT_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.greaterThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the questionMasterList where weightage is greater than or equal to UPDATED_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.greaterThanOrEqual=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage is less than or equal to DEFAULT_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.lessThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the questionMasterList where weightage is less than or equal to SMALLER_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.lessThanOrEqual=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsLessThanSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage is less than DEFAULT_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.lessThan=" + DEFAULT_WEIGHTAGE);

        // Get all the questionMasterList where weightage is less than UPDATED_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.lessThan=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void getAllQuestionMastersByWeightageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList where weightage is greater than DEFAULT_WEIGHTAGE
        defaultQuestionMasterShouldNotBeFound("weightage.greaterThan=" + DEFAULT_WEIGHTAGE);

        // Get all the questionMasterList where weightage is greater than SMALLER_WEIGHTAGE
        defaultQuestionMasterShouldBeFound("weightage.greaterThan=" + SMALLER_WEIGHTAGE);
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByQuestionTypeMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        QuestionTypeMaster questionTypeMaster = questionMaster.getQuestionTypeMaster();
        questionMasterRepository.saveAndFlush(questionMaster);
        Long questionTypeMasterId = questionTypeMaster.getId();

        // Get all the questionMasterList where questionTypeMaster equals to questionTypeMasterId
        defaultQuestionMasterShouldBeFound("questionTypeMasterId.equals=" + questionTypeMasterId);

        // Get all the questionMasterList where questionTypeMaster equals to questionTypeMasterId + 1
        defaultQuestionMasterShouldNotBeFound("questionTypeMasterId.equals=" + (questionTypeMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByDifficultyTypeMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);
        DifficultyTypeMaster difficultyTypeMaster = DifficultyTypeMasterResourceIT.createEntity(em);
        em.persist(difficultyTypeMaster);
        em.flush();
        questionMaster.setDifficultyTypeMaster(difficultyTypeMaster);
        questionMasterRepository.saveAndFlush(questionMaster);
        Long difficultyTypeMasterId = difficultyTypeMaster.getId();

        // Get all the questionMasterList where difficultyTypeMaster equals to difficultyTypeMasterId
        defaultQuestionMasterShouldBeFound("difficultyTypeMasterId.equals=" + difficultyTypeMasterId);

        // Get all the questionMasterList where difficultyTypeMaster equals to difficultyTypeMasterId + 1
        defaultQuestionMasterShouldNotBeFound("difficultyTypeMasterId.equals=" + (difficultyTypeMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionMastersBySubTopicMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubTopicMaster subTopicMaster = questionMaster.getSubTopicMaster();
        questionMasterRepository.saveAndFlush(questionMaster);
        Long subTopicMasterId = subTopicMaster.getId();

        // Get all the questionMasterList where subTopicMaster equals to subTopicMasterId
        defaultQuestionMasterShouldBeFound("subTopicMasterId.equals=" + subTopicMasterId);

        // Get all the questionMasterList where subTopicMaster equals to subTopicMasterId + 1
        defaultQuestionMasterShouldNotBeFound("subTopicMasterId.equals=" + (subTopicMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByParentQuestionMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);
        QuestionMaster parentQuestionMaster = QuestionMasterResourceIT.createEntity(em);
        em.persist(parentQuestionMaster);
        em.flush();
        questionMaster.setParentQuestionMaster(parentQuestionMaster);
        questionMasterRepository.saveAndFlush(questionMaster);
        Long parentQuestionMasterId = parentQuestionMaster.getId();

        // Get all the questionMasterList where parentQuestionMaster equals to parentQuestionMasterId
        defaultQuestionMasterShouldBeFound("parentQuestionMasterId.equals=" + parentQuestionMasterId);

        // Get all the questionMasterList where parentQuestionMaster equals to parentQuestionMasterId + 1
        defaultQuestionMasterShouldNotBeFound("parentQuestionMasterId.equals=" + (parentQuestionMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByQuestionMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);
        QuestionMaster questionMaster = QuestionMasterResourceIT.createEntity(em);
        em.persist(questionMaster);
        em.flush();
        questionMaster.addQuestionMaster(questionMaster);
        questionMasterRepository.saveAndFlush(questionMaster);
        Long questionMasterId = questionMaster.getId();

        // Get all the questionMasterList where questionMaster equals to questionMasterId
        defaultQuestionMasterShouldBeFound("questionMasterId.equals=" + questionMasterId);

        // Get all the questionMasterList where questionMaster equals to questionMasterId + 1
        defaultQuestionMasterShouldNotBeFound("questionMasterId.equals=" + (questionMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionMastersByAnswerMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);
        AnswerMaster answerMaster = AnswerMasterResourceIT.createEntity(em);
        em.persist(answerMaster);
        em.flush();
        questionMaster.addAnswerMaster(answerMaster);
        questionMasterRepository.saveAndFlush(questionMaster);
        Long answerMasterId = answerMaster.getId();

        // Get all the questionMasterList where answerMaster equals to answerMasterId
        defaultQuestionMasterShouldBeFound("answerMasterId.equals=" + answerMasterId);

        // Get all the questionMasterList where answerMaster equals to answerMasterId + 1
        defaultQuestionMasterShouldNotBeFound("answerMasterId.equals=" + (answerMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionMasterShouldBeFound(String filter) throws Exception {
        restQuestionMasterMockMvc.perform(get("/api/question-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE.doubleValue())));

        // Check, that the count call also returns 1
        restQuestionMasterMockMvc.perform(get("/api/question-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionMasterShouldNotBeFound(String filter) throws Exception {
        restQuestionMasterMockMvc.perform(get("/api/question-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionMasterMockMvc.perform(get("/api/question-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionMaster() throws Exception {
        // Get the questionMaster
        restQuestionMasterMockMvc.perform(get("/api/question-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionMaster() throws Exception {
        // Initialize the database
        questionMasterService.save(questionMaster);

        int databaseSizeBeforeUpdate = questionMasterRepository.findAll().size();

        // Update the questionMaster
        QuestionMaster updatedQuestionMaster = questionMasterRepository.findById(questionMaster.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionMaster are not directly saved in db
        em.detach(updatedQuestionMaster);
        updatedQuestionMaster
            .text(UPDATED_TEXT)
            .weightage(UPDATED_WEIGHTAGE);

        restQuestionMasterMockMvc.perform(put("/api/question-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionMaster)))
            .andExpect(status().isOk());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeUpdate);
        QuestionMaster testQuestionMaster = questionMasterList.get(questionMasterList.size() - 1);
        assertThat(testQuestionMaster.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQuestionMaster.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionMaster() throws Exception {
        int databaseSizeBeforeUpdate = questionMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMasterMockMvc.perform(put("/api/question-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionMaster() throws Exception {
        // Initialize the database
        questionMasterService.save(questionMaster);

        int databaseSizeBeforeDelete = questionMasterRepository.findAll().size();

        // Delete the questionMaster
        restQuestionMasterMockMvc.perform(delete("/api/question-masters/{id}", questionMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
