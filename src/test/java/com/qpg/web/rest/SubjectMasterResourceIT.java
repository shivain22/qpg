package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.SubjectMaster;
import com.qpg.domain.SubCategoryMaster;
import com.qpg.repository.SubjectMasterRepository;
import com.qpg.service.SubjectMasterService;
import com.qpg.service.dto.SubjectMasterCriteria;
import com.qpg.service.SubjectMasterQueryService;

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
 * Integration tests for the {@link SubjectMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubjectMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubjectMasterRepository subjectMasterRepository;

    @Autowired
    private SubjectMasterService subjectMasterService;

    @Autowired
    private SubjectMasterQueryService subjectMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubjectMasterMockMvc;

    private SubjectMaster subjectMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectMaster createEntity(EntityManager em) {
        SubjectMaster subjectMaster = new SubjectMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        SubCategoryMaster subCategoryMaster;
        if (TestUtil.findAll(em, SubCategoryMaster.class).isEmpty()) {
            subCategoryMaster = SubCategoryMasterResourceIT.createEntity(em);
            em.persist(subCategoryMaster);
            em.flush();
        } else {
            subCategoryMaster = TestUtil.findAll(em, SubCategoryMaster.class).get(0);
        }
        subjectMaster.setSubCategoryMaster(subCategoryMaster);
        return subjectMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectMaster createUpdatedEntity(EntityManager em) {
        SubjectMaster subjectMaster = new SubjectMaster()
            .name(UPDATED_NAME);
        // Add required entity
        SubCategoryMaster subCategoryMaster;
        if (TestUtil.findAll(em, SubCategoryMaster.class).isEmpty()) {
            subCategoryMaster = SubCategoryMasterResourceIT.createUpdatedEntity(em);
            em.persist(subCategoryMaster);
            em.flush();
        } else {
            subCategoryMaster = TestUtil.findAll(em, SubCategoryMaster.class).get(0);
        }
        subjectMaster.setSubCategoryMaster(subCategoryMaster);
        return subjectMaster;
    }

    @BeforeEach
    public void initTest() {
        subjectMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubjectMaster() throws Exception {
        int databaseSizeBeforeCreate = subjectMasterRepository.findAll().size();
        // Create the SubjectMaster
        restSubjectMasterMockMvc.perform(post("/api/subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subjectMaster)))
            .andExpect(status().isCreated());

        // Validate the SubjectMaster in the database
        List<SubjectMaster> subjectMasterList = subjectMasterRepository.findAll();
        assertThat(subjectMasterList).hasSize(databaseSizeBeforeCreate + 1);
        SubjectMaster testSubjectMaster = subjectMasterList.get(subjectMasterList.size() - 1);
        assertThat(testSubjectMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubjectMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectMasterRepository.findAll().size();

        // Create the SubjectMaster with an existing ID
        subjectMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectMasterMockMvc.perform(post("/api/subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subjectMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubjectMaster in the database
        List<SubjectMaster> subjectMasterList = subjectMasterRepository.findAll();
        assertThat(subjectMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectMasterRepository.findAll().size();
        // set the field null
        subjectMaster.setName(null);

        // Create the SubjectMaster, which fails.


        restSubjectMasterMockMvc.perform(post("/api/subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subjectMaster)))
            .andExpect(status().isBadRequest());

        List<SubjectMaster> subjectMasterList = subjectMasterRepository.findAll();
        assertThat(subjectMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubjectMasters() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList
        restSubjectMasterMockMvc.perform(get("/api/subject-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSubjectMaster() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get the subjectMaster
        restSubjectMasterMockMvc.perform(get("/api/subject-masters/{id}", subjectMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subjectMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getSubjectMastersByIdFiltering() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        Long id = subjectMaster.getId();

        defaultSubjectMasterShouldBeFound("id.equals=" + id);
        defaultSubjectMasterShouldNotBeFound("id.notEquals=" + id);

        defaultSubjectMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubjectMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultSubjectMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubjectMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubjectMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList where name equals to DEFAULT_NAME
        defaultSubjectMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the subjectMasterList where name equals to UPDATED_NAME
        defaultSubjectMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList where name not equals to DEFAULT_NAME
        defaultSubjectMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the subjectMasterList where name not equals to UPDATED_NAME
        defaultSubjectMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSubjectMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the subjectMasterList where name equals to UPDATED_NAME
        defaultSubjectMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList where name is not null
        defaultSubjectMasterShouldBeFound("name.specified=true");

        // Get all the subjectMasterList where name is null
        defaultSubjectMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubjectMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList where name contains DEFAULT_NAME
        defaultSubjectMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the subjectMasterList where name contains UPDATED_NAME
        defaultSubjectMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        subjectMasterRepository.saveAndFlush(subjectMaster);

        // Get all the subjectMasterList where name does not contain DEFAULT_NAME
        defaultSubjectMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the subjectMasterList where name does not contain UPDATED_NAME
        defaultSubjectMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSubjectMastersBySubCategoryMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubCategoryMaster subCategoryMaster = subjectMaster.getSubCategoryMaster();
        subjectMasterRepository.saveAndFlush(subjectMaster);
        Long subCategoryMasterId = subCategoryMaster.getId();

        // Get all the subjectMasterList where subCategoryMaster equals to subCategoryMasterId
        defaultSubjectMasterShouldBeFound("subCategoryMasterId.equals=" + subCategoryMasterId);

        // Get all the subjectMasterList where subCategoryMaster equals to subCategoryMasterId + 1
        defaultSubjectMasterShouldNotBeFound("subCategoryMasterId.equals=" + (subCategoryMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubjectMasterShouldBeFound(String filter) throws Exception {
        restSubjectMasterMockMvc.perform(get("/api/subject-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restSubjectMasterMockMvc.perform(get("/api/subject-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubjectMasterShouldNotBeFound(String filter) throws Exception {
        restSubjectMasterMockMvc.perform(get("/api/subject-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubjectMasterMockMvc.perform(get("/api/subject-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSubjectMaster() throws Exception {
        // Get the subjectMaster
        restSubjectMasterMockMvc.perform(get("/api/subject-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjectMaster() throws Exception {
        // Initialize the database
        subjectMasterService.save(subjectMaster);

        int databaseSizeBeforeUpdate = subjectMasterRepository.findAll().size();

        // Update the subjectMaster
        SubjectMaster updatedSubjectMaster = subjectMasterRepository.findById(subjectMaster.getId()).get();
        // Disconnect from session so that the updates on updatedSubjectMaster are not directly saved in db
        em.detach(updatedSubjectMaster);
        updatedSubjectMaster
            .name(UPDATED_NAME);

        restSubjectMasterMockMvc.perform(put("/api/subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubjectMaster)))
            .andExpect(status().isOk());

        // Validate the SubjectMaster in the database
        List<SubjectMaster> subjectMasterList = subjectMasterRepository.findAll();
        assertThat(subjectMasterList).hasSize(databaseSizeBeforeUpdate);
        SubjectMaster testSubjectMaster = subjectMasterList.get(subjectMasterList.size() - 1);
        assertThat(testSubjectMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubjectMaster() throws Exception {
        int databaseSizeBeforeUpdate = subjectMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectMasterMockMvc.perform(put("/api/subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subjectMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubjectMaster in the database
        List<SubjectMaster> subjectMasterList = subjectMasterRepository.findAll();
        assertThat(subjectMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubjectMaster() throws Exception {
        // Initialize the database
        subjectMasterService.save(subjectMaster);

        int databaseSizeBeforeDelete = subjectMasterRepository.findAll().size();

        // Delete the subjectMaster
        restSubjectMasterMockMvc.perform(delete("/api/subject-masters/{id}", subjectMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubjectMaster> subjectMasterList = subjectMasterRepository.findAll();
        assertThat(subjectMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
