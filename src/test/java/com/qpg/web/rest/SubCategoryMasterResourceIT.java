package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.SubCategoryMaster;
import com.qpg.domain.SubjectMaster;
import com.qpg.domain.CategoryMaster;
import com.qpg.repository.SubCategoryMasterRepository;
import com.qpg.service.SubCategoryMasterService;
import com.qpg.service.dto.SubCategoryMasterCriteria;
import com.qpg.service.SubCategoryMasterQueryService;

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
 * Integration tests for the {@link SubCategoryMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubCategoryMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubCategoryMasterRepository subCategoryMasterRepository;

    @Autowired
    private SubCategoryMasterService subCategoryMasterService;

    @Autowired
    private SubCategoryMasterQueryService subCategoryMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubCategoryMasterMockMvc;

    private SubCategoryMaster subCategoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategoryMaster createEntity(EntityManager em) {
        SubCategoryMaster subCategoryMaster = new SubCategoryMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        CategoryMaster categoryMaster;
        if (TestUtil.findAll(em, CategoryMaster.class).isEmpty()) {
            categoryMaster = CategoryMasterResourceIT.createEntity(em);
            em.persist(categoryMaster);
            em.flush();
        } else {
            categoryMaster = TestUtil.findAll(em, CategoryMaster.class).get(0);
        }
        subCategoryMaster.setCategoryMaster(categoryMaster);
        return subCategoryMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategoryMaster createUpdatedEntity(EntityManager em) {
        SubCategoryMaster subCategoryMaster = new SubCategoryMaster()
            .name(UPDATED_NAME);
        // Add required entity
        CategoryMaster categoryMaster;
        if (TestUtil.findAll(em, CategoryMaster.class).isEmpty()) {
            categoryMaster = CategoryMasterResourceIT.createUpdatedEntity(em);
            em.persist(categoryMaster);
            em.flush();
        } else {
            categoryMaster = TestUtil.findAll(em, CategoryMaster.class).get(0);
        }
        subCategoryMaster.setCategoryMaster(categoryMaster);
        return subCategoryMaster;
    }

    @BeforeEach
    public void initTest() {
        subCategoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubCategoryMaster() throws Exception {
        int databaseSizeBeforeCreate = subCategoryMasterRepository.findAll().size();
        // Create the SubCategoryMaster
        restSubCategoryMasterMockMvc.perform(post("/api/sub-category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryMaster)))
            .andExpect(status().isCreated());

        // Validate the SubCategoryMaster in the database
        List<SubCategoryMaster> subCategoryMasterList = subCategoryMasterRepository.findAll();
        assertThat(subCategoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategoryMaster testSubCategoryMaster = subCategoryMasterList.get(subCategoryMasterList.size() - 1);
        assertThat(testSubCategoryMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubCategoryMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subCategoryMasterRepository.findAll().size();

        // Create the SubCategoryMaster with an existing ID
        subCategoryMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoryMasterMockMvc.perform(post("/api/sub-category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubCategoryMaster in the database
        List<SubCategoryMaster> subCategoryMasterList = subCategoryMasterRepository.findAll();
        assertThat(subCategoryMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryMasterRepository.findAll().size();
        // set the field null
        subCategoryMaster.setName(null);

        // Create the SubCategoryMaster, which fails.


        restSubCategoryMasterMockMvc.perform(post("/api/sub-category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryMaster)))
            .andExpect(status().isBadRequest());

        List<SubCategoryMaster> subCategoryMasterList = subCategoryMasterRepository.findAll();
        assertThat(subCategoryMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubCategoryMasters() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSubCategoryMaster() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get the subCategoryMaster
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters/{id}", subCategoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subCategoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getSubCategoryMastersByIdFiltering() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        Long id = subCategoryMaster.getId();

        defaultSubCategoryMasterShouldBeFound("id.equals=" + id);
        defaultSubCategoryMasterShouldNotBeFound("id.notEquals=" + id);

        defaultSubCategoryMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubCategoryMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultSubCategoryMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubCategoryMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubCategoryMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList where name equals to DEFAULT_NAME
        defaultSubCategoryMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the subCategoryMasterList where name equals to UPDATED_NAME
        defaultSubCategoryMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubCategoryMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList where name not equals to DEFAULT_NAME
        defaultSubCategoryMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the subCategoryMasterList where name not equals to UPDATED_NAME
        defaultSubCategoryMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubCategoryMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSubCategoryMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the subCategoryMasterList where name equals to UPDATED_NAME
        defaultSubCategoryMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubCategoryMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList where name is not null
        defaultSubCategoryMasterShouldBeFound("name.specified=true");

        // Get all the subCategoryMasterList where name is null
        defaultSubCategoryMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubCategoryMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList where name contains DEFAULT_NAME
        defaultSubCategoryMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the subCategoryMasterList where name contains UPDATED_NAME
        defaultSubCategoryMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubCategoryMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);

        // Get all the subCategoryMasterList where name does not contain DEFAULT_NAME
        defaultSubCategoryMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the subCategoryMasterList where name does not contain UPDATED_NAME
        defaultSubCategoryMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSubCategoryMastersBySubjectMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);
        SubjectMaster subjectMaster = SubjectMasterResourceIT.createEntity(em);
        em.persist(subjectMaster);
        em.flush();
        subCategoryMaster.addSubjectMaster(subjectMaster);
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);
        Long subjectMasterId = subjectMaster.getId();

        // Get all the subCategoryMasterList where subjectMaster equals to subjectMasterId
        defaultSubCategoryMasterShouldBeFound("subjectMasterId.equals=" + subjectMasterId);

        // Get all the subCategoryMasterList where subjectMaster equals to subjectMasterId + 1
        defaultSubCategoryMasterShouldNotBeFound("subjectMasterId.equals=" + (subjectMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllSubCategoryMastersByCategoryMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CategoryMaster categoryMaster = subCategoryMaster.getCategoryMaster();
        subCategoryMasterRepository.saveAndFlush(subCategoryMaster);
        Long categoryMasterId = categoryMaster.getId();

        // Get all the subCategoryMasterList where categoryMaster equals to categoryMasterId
        defaultSubCategoryMasterShouldBeFound("categoryMasterId.equals=" + categoryMasterId);

        // Get all the subCategoryMasterList where categoryMaster equals to categoryMasterId + 1
        defaultSubCategoryMasterShouldNotBeFound("categoryMasterId.equals=" + (categoryMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubCategoryMasterShouldBeFound(String filter) throws Exception {
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubCategoryMasterShouldNotBeFound(String filter) throws Exception {
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSubCategoryMaster() throws Exception {
        // Get the subCategoryMaster
        restSubCategoryMasterMockMvc.perform(get("/api/sub-category-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubCategoryMaster() throws Exception {
        // Initialize the database
        subCategoryMasterService.save(subCategoryMaster);

        int databaseSizeBeforeUpdate = subCategoryMasterRepository.findAll().size();

        // Update the subCategoryMaster
        SubCategoryMaster updatedSubCategoryMaster = subCategoryMasterRepository.findById(subCategoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedSubCategoryMaster are not directly saved in db
        em.detach(updatedSubCategoryMaster);
        updatedSubCategoryMaster
            .name(UPDATED_NAME);

        restSubCategoryMasterMockMvc.perform(put("/api/sub-category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubCategoryMaster)))
            .andExpect(status().isOk());

        // Validate the SubCategoryMaster in the database
        List<SubCategoryMaster> subCategoryMasterList = subCategoryMasterRepository.findAll();
        assertThat(subCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
        SubCategoryMaster testSubCategoryMaster = subCategoryMasterList.get(subCategoryMasterList.size() - 1);
        assertThat(testSubCategoryMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoryMasterMockMvc.perform(put("/api/sub-category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subCategoryMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubCategoryMaster in the database
        List<SubCategoryMaster> subCategoryMasterList = subCategoryMasterRepository.findAll();
        assertThat(subCategoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubCategoryMaster() throws Exception {
        // Initialize the database
        subCategoryMasterService.save(subCategoryMaster);

        int databaseSizeBeforeDelete = subCategoryMasterRepository.findAll().size();

        // Delete the subCategoryMaster
        restSubCategoryMasterMockMvc.perform(delete("/api/sub-category-masters/{id}", subCategoryMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubCategoryMaster> subCategoryMasterList = subCategoryMasterRepository.findAll();
        assertThat(subCategoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
