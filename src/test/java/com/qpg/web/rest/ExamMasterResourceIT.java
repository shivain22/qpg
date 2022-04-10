package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.ExamMaster;
import com.qpg.domain.QuestionBluePrintMaster;
import com.qpg.repository.ExamMasterRepository;
import com.qpg.service.ExamMasterService;
import com.qpg.service.dto.ExamMasterCriteria;
import com.qpg.service.ExamMasterQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExamMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamMasterResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ExamMasterRepository examMasterRepository;

    @Autowired
    private ExamMasterService examMasterService;

    @Autowired
    private ExamMasterQueryService examMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamMasterMockMvc;

    private ExamMaster examMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamMaster createEntity(EntityManager em) {
        ExamMaster examMaster = new ExamMaster()
            .title(DEFAULT_TITLE)
            .startDate(DEFAULT_START_DATE);
        return examMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamMaster createUpdatedEntity(EntityManager em) {
        ExamMaster examMaster = new ExamMaster()
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE);
        return examMaster;
    }

    @BeforeEach
    public void initTest() {
        examMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamMaster() throws Exception {
        int databaseSizeBeforeCreate = examMasterRepository.findAll().size();
        // Create the ExamMaster
        restExamMasterMockMvc.perform(post("/api/exam-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examMaster)))
            .andExpect(status().isCreated());

        // Validate the ExamMaster in the database
        List<ExamMaster> examMasterList = examMasterRepository.findAll();
        assertThat(examMasterList).hasSize(databaseSizeBeforeCreate + 1);
        ExamMaster testExamMaster = examMasterList.get(examMasterList.size() - 1);
        assertThat(testExamMaster.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExamMaster.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createExamMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examMasterRepository.findAll().size();

        // Create the ExamMaster with an existing ID
        examMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamMasterMockMvc.perform(post("/api/exam-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examMaster)))
            .andExpect(status().isBadRequest());

        // Validate the ExamMaster in the database
        List<ExamMaster> examMasterList = examMasterRepository.findAll();
        assertThat(examMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = examMasterRepository.findAll().size();
        // set the field null
        examMaster.setTitle(null);

        // Create the ExamMaster, which fails.


        restExamMasterMockMvc.perform(post("/api/exam-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examMaster)))
            .andExpect(status().isBadRequest());

        List<ExamMaster> examMasterList = examMasterRepository.findAll();
        assertThat(examMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamMasters() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList
        restExamMasterMockMvc.perform(get("/api/exam-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void getExamMaster() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get the examMaster
        restExamMasterMockMvc.perform(get("/api/exam-masters/{id}", examMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examMaster.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }


    @Test
    @Transactional
    public void getExamMastersByIdFiltering() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        Long id = examMaster.getId();

        defaultExamMasterShouldBeFound("id.equals=" + id);
        defaultExamMasterShouldNotBeFound("id.notEquals=" + id);

        defaultExamMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExamMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultExamMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExamMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExamMastersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where title equals to DEFAULT_TITLE
        defaultExamMasterShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the examMasterList where title equals to UPDATED_TITLE
        defaultExamMasterShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where title not equals to DEFAULT_TITLE
        defaultExamMasterShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the examMasterList where title not equals to UPDATED_TITLE
        defaultExamMasterShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultExamMasterShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the examMasterList where title equals to UPDATED_TITLE
        defaultExamMasterShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where title is not null
        defaultExamMasterShouldBeFound("title.specified=true");

        // Get all the examMasterList where title is null
        defaultExamMasterShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllExamMastersByTitleContainsSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where title contains DEFAULT_TITLE
        defaultExamMasterShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the examMasterList where title contains UPDATED_TITLE
        defaultExamMasterShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where title does not contain DEFAULT_TITLE
        defaultExamMasterShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the examMasterList where title does not contain UPDATED_TITLE
        defaultExamMasterShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate equals to DEFAULT_START_DATE
        defaultExamMasterShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the examMasterList where startDate equals to UPDATED_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate not equals to DEFAULT_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the examMasterList where startDate not equals to UPDATED_START_DATE
        defaultExamMasterShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultExamMasterShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the examMasterList where startDate equals to UPDATED_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate is not null
        defaultExamMasterShouldBeFound("startDate.specified=true");

        // Get all the examMasterList where startDate is null
        defaultExamMasterShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultExamMasterShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the examMasterList where startDate is greater than or equal to UPDATED_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate is less than or equal to DEFAULT_START_DATE
        defaultExamMasterShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the examMasterList where startDate is less than or equal to SMALLER_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate is less than DEFAULT_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the examMasterList where startDate is less than UPDATED_START_DATE
        defaultExamMasterShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExamMastersByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);

        // Get all the examMasterList where startDate is greater than DEFAULT_START_DATE
        defaultExamMasterShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the examMasterList where startDate is greater than SMALLER_START_DATE
        defaultExamMasterShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllExamMastersByQuestionBluePrintMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        examMasterRepository.saveAndFlush(examMaster);
        QuestionBluePrintMaster questionBluePrintMaster = QuestionBluePrintMasterResourceIT.createEntity(em);
        em.persist(questionBluePrintMaster);
        em.flush();
        examMasterRepository.saveAndFlush(examMaster);
        Long questionBluePrintMasterId = questionBluePrintMaster.getId();

        // Get all the examMasterList where questionBluePrintMaster equals to questionBluePrintMasterId
        defaultExamMasterShouldBeFound("questionBluePrintMasterId.equals=" + questionBluePrintMasterId);

        // Get all the examMasterList where questionBluePrintMaster equals to questionBluePrintMasterId + 1
        defaultExamMasterShouldNotBeFound("questionBluePrintMasterId.equals=" + (questionBluePrintMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExamMasterShouldBeFound(String filter) throws Exception {
        restExamMasterMockMvc.perform(get("/api/exam-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));

        // Check, that the count call also returns 1
        restExamMasterMockMvc.perform(get("/api/exam-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExamMasterShouldNotBeFound(String filter) throws Exception {
        restExamMasterMockMvc.perform(get("/api/exam-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExamMasterMockMvc.perform(get("/api/exam-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExamMaster() throws Exception {
        // Get the examMaster
        restExamMasterMockMvc.perform(get("/api/exam-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamMaster() throws Exception {
        // Initialize the database
        examMasterService.save(examMaster);

        int databaseSizeBeforeUpdate = examMasterRepository.findAll().size();

        // Update the examMaster
        ExamMaster updatedExamMaster = examMasterRepository.findById(examMaster.getId()).get();
        // Disconnect from session so that the updates on updatedExamMaster are not directly saved in db
        em.detach(updatedExamMaster);
        updatedExamMaster
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE);

        restExamMasterMockMvc.perform(put("/api/exam-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamMaster)))
            .andExpect(status().isOk());

        // Validate the ExamMaster in the database
        List<ExamMaster> examMasterList = examMasterRepository.findAll();
        assertThat(examMasterList).hasSize(databaseSizeBeforeUpdate);
        ExamMaster testExamMaster = examMasterList.get(examMasterList.size() - 1);
        assertThat(testExamMaster.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExamMaster.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamMaster() throws Exception {
        int databaseSizeBeforeUpdate = examMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamMasterMockMvc.perform(put("/api/exam-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examMaster)))
            .andExpect(status().isBadRequest());

        // Validate the ExamMaster in the database
        List<ExamMaster> examMasterList = examMasterRepository.findAll();
        assertThat(examMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamMaster() throws Exception {
        // Initialize the database
        examMasterService.save(examMaster);

        int databaseSizeBeforeDelete = examMasterRepository.findAll().size();

        // Delete the examMaster
        restExamMasterMockMvc.perform(delete("/api/exam-masters/{id}", examMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamMaster> examMasterList = examMasterRepository.findAll();
        assertThat(examMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
