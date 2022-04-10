package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.DifficultyTypeMaster;
import com.qpg.domain.QuestionMaster;
import com.qpg.repository.DifficultyTypeMasterRepository;
import com.qpg.service.DifficultyTypeMasterService;
import com.qpg.service.dto.DifficultyTypeMasterCriteria;
import com.qpg.service.DifficultyTypeMasterQueryService;

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
 * Integration tests for the {@link DifficultyTypeMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DifficultyTypeMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DifficultyTypeMasterRepository difficultyTypeMasterRepository;

    @Autowired
    private DifficultyTypeMasterService difficultyTypeMasterService;

    @Autowired
    private DifficultyTypeMasterQueryService difficultyTypeMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDifficultyTypeMasterMockMvc;

    private DifficultyTypeMaster difficultyTypeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DifficultyTypeMaster createEntity(EntityManager em) {
        DifficultyTypeMaster difficultyTypeMaster = new DifficultyTypeMaster()
            .name(DEFAULT_NAME);
        return difficultyTypeMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DifficultyTypeMaster createUpdatedEntity(EntityManager em) {
        DifficultyTypeMaster difficultyTypeMaster = new DifficultyTypeMaster()
            .name(UPDATED_NAME);
        return difficultyTypeMaster;
    }

    @BeforeEach
    public void initTest() {
        difficultyTypeMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createDifficultyTypeMaster() throws Exception {
        int databaseSizeBeforeCreate = difficultyTypeMasterRepository.findAll().size();
        // Create the DifficultyTypeMaster
        restDifficultyTypeMasterMockMvc.perform(post("/api/difficulty-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(difficultyTypeMaster)))
            .andExpect(status().isCreated());

        // Validate the DifficultyTypeMaster in the database
        List<DifficultyTypeMaster> difficultyTypeMasterList = difficultyTypeMasterRepository.findAll();
        assertThat(difficultyTypeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        DifficultyTypeMaster testDifficultyTypeMaster = difficultyTypeMasterList.get(difficultyTypeMasterList.size() - 1);
        assertThat(testDifficultyTypeMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDifficultyTypeMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = difficultyTypeMasterRepository.findAll().size();

        // Create the DifficultyTypeMaster with an existing ID
        difficultyTypeMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDifficultyTypeMasterMockMvc.perform(post("/api/difficulty-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(difficultyTypeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the DifficultyTypeMaster in the database
        List<DifficultyTypeMaster> difficultyTypeMasterList = difficultyTypeMasterRepository.findAll();
        assertThat(difficultyTypeMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = difficultyTypeMasterRepository.findAll().size();
        // set the field null
        difficultyTypeMaster.setName(null);

        // Create the DifficultyTypeMaster, which fails.


        restDifficultyTypeMasterMockMvc.perform(post("/api/difficulty-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(difficultyTypeMaster)))
            .andExpect(status().isBadRequest());

        List<DifficultyTypeMaster> difficultyTypeMasterList = difficultyTypeMasterRepository.findAll();
        assertThat(difficultyTypeMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDifficultyTypeMasters() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(difficultyTypeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDifficultyTypeMaster() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get the difficultyTypeMaster
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters/{id}", difficultyTypeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(difficultyTypeMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDifficultyTypeMastersByIdFiltering() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        Long id = difficultyTypeMaster.getId();

        defaultDifficultyTypeMasterShouldBeFound("id.equals=" + id);
        defaultDifficultyTypeMasterShouldNotBeFound("id.notEquals=" + id);

        defaultDifficultyTypeMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDifficultyTypeMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultDifficultyTypeMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDifficultyTypeMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDifficultyTypeMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList where name equals to DEFAULT_NAME
        defaultDifficultyTypeMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the difficultyTypeMasterList where name equals to UPDATED_NAME
        defaultDifficultyTypeMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDifficultyTypeMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList where name not equals to DEFAULT_NAME
        defaultDifficultyTypeMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the difficultyTypeMasterList where name not equals to UPDATED_NAME
        defaultDifficultyTypeMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDifficultyTypeMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDifficultyTypeMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the difficultyTypeMasterList where name equals to UPDATED_NAME
        defaultDifficultyTypeMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDifficultyTypeMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList where name is not null
        defaultDifficultyTypeMasterShouldBeFound("name.specified=true");

        // Get all the difficultyTypeMasterList where name is null
        defaultDifficultyTypeMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDifficultyTypeMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList where name contains DEFAULT_NAME
        defaultDifficultyTypeMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the difficultyTypeMasterList where name contains UPDATED_NAME
        defaultDifficultyTypeMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDifficultyTypeMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);

        // Get all the difficultyTypeMasterList where name does not contain DEFAULT_NAME
        defaultDifficultyTypeMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the difficultyTypeMasterList where name does not contain UPDATED_NAME
        defaultDifficultyTypeMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDifficultyTypeMastersByQuestionMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);
        QuestionMaster questionMaster = QuestionMasterResourceIT.createEntity(em);
        em.persist(questionMaster);
        em.flush();
        difficultyTypeMaster.addQuestionMaster(questionMaster);
        difficultyTypeMasterRepository.saveAndFlush(difficultyTypeMaster);
        Long questionMasterId = questionMaster.getId();

        // Get all the difficultyTypeMasterList where questionMaster equals to questionMasterId
        defaultDifficultyTypeMasterShouldBeFound("questionMasterId.equals=" + questionMasterId);

        // Get all the difficultyTypeMasterList where questionMaster equals to questionMasterId + 1
        defaultDifficultyTypeMasterShouldNotBeFound("questionMasterId.equals=" + (questionMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDifficultyTypeMasterShouldBeFound(String filter) throws Exception {
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(difficultyTypeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDifficultyTypeMasterShouldNotBeFound(String filter) throws Exception {
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDifficultyTypeMaster() throws Exception {
        // Get the difficultyTypeMaster
        restDifficultyTypeMasterMockMvc.perform(get("/api/difficulty-type-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDifficultyTypeMaster() throws Exception {
        // Initialize the database
        difficultyTypeMasterService.save(difficultyTypeMaster);

        int databaseSizeBeforeUpdate = difficultyTypeMasterRepository.findAll().size();

        // Update the difficultyTypeMaster
        DifficultyTypeMaster updatedDifficultyTypeMaster = difficultyTypeMasterRepository.findById(difficultyTypeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedDifficultyTypeMaster are not directly saved in db
        em.detach(updatedDifficultyTypeMaster);
        updatedDifficultyTypeMaster
            .name(UPDATED_NAME);

        restDifficultyTypeMasterMockMvc.perform(put("/api/difficulty-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDifficultyTypeMaster)))
            .andExpect(status().isOk());

        // Validate the DifficultyTypeMaster in the database
        List<DifficultyTypeMaster> difficultyTypeMasterList = difficultyTypeMasterRepository.findAll();
        assertThat(difficultyTypeMasterList).hasSize(databaseSizeBeforeUpdate);
        DifficultyTypeMaster testDifficultyTypeMaster = difficultyTypeMasterList.get(difficultyTypeMasterList.size() - 1);
        assertThat(testDifficultyTypeMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDifficultyTypeMaster() throws Exception {
        int databaseSizeBeforeUpdate = difficultyTypeMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDifficultyTypeMasterMockMvc.perform(put("/api/difficulty-type-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(difficultyTypeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the DifficultyTypeMaster in the database
        List<DifficultyTypeMaster> difficultyTypeMasterList = difficultyTypeMasterRepository.findAll();
        assertThat(difficultyTypeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDifficultyTypeMaster() throws Exception {
        // Initialize the database
        difficultyTypeMasterService.save(difficultyTypeMaster);

        int databaseSizeBeforeDelete = difficultyTypeMasterRepository.findAll().size();

        // Delete the difficultyTypeMaster
        restDifficultyTypeMasterMockMvc.perform(delete("/api/difficulty-type-masters/{id}", difficultyTypeMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DifficultyTypeMaster> difficultyTypeMasterList = difficultyTypeMasterRepository.findAll();
        assertThat(difficultyTypeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
