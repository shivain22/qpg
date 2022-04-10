package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.CategoryMaster;
import com.qpg.domain.SubCategoryMaster;
import com.qpg.repository.CategoryMasterRepository;
import com.qpg.service.CategoryMasterService;
import com.qpg.service.dto.CategoryMasterCriteria;
import com.qpg.service.CategoryMasterQueryService;

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
 * Integration tests for the {@link CategoryMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CategoryMasterRepository categoryMasterRepository;

    @Autowired
    private CategoryMasterService categoryMasterService;

    @Autowired
    private CategoryMasterQueryService categoryMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMasterMockMvc;

    private CategoryMaster categoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryMaster createEntity(EntityManager em) {
        CategoryMaster categoryMaster = new CategoryMaster()
            .name(DEFAULT_NAME);
        return categoryMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryMaster createUpdatedEntity(EntityManager em) {
        CategoryMaster categoryMaster = new CategoryMaster()
            .name(UPDATED_NAME);
        return categoryMaster;
    }

    @BeforeEach
    public void initTest() {
        categoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryMaster() throws Exception {
        int databaseSizeBeforeCreate = categoryMasterRepository.findAll().size();
        // Create the CategoryMaster
        restCategoryMasterMockMvc.perform(post("/api/category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryMaster)))
            .andExpect(status().isCreated());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryMaster testCategoryMaster = categoryMasterList.get(categoryMasterList.size() - 1);
        assertThat(testCategoryMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCategoryMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryMasterRepository.findAll().size();

        // Create the CategoryMaster with an existing ID
        categoryMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMasterMockMvc.perform(post("/api/category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryMasterRepository.findAll().size();
        // set the field null
        categoryMaster.setName(null);

        // Create the CategoryMaster, which fails.


        restCategoryMasterMockMvc.perform(post("/api/category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryMaster)))
            .andExpect(status().isBadRequest());

        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryMasters() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList
        restCategoryMasterMockMvc.perform(get("/api/category-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCategoryMaster() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get the categoryMaster
        restCategoryMasterMockMvc.perform(get("/api/category-masters/{id}", categoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCategoryMastersByIdFiltering() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        Long id = categoryMaster.getId();

        defaultCategoryMasterShouldBeFound("id.equals=" + id);
        defaultCategoryMasterShouldNotBeFound("id.notEquals=" + id);

        defaultCategoryMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoryMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoryMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoryMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoryMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList where name equals to DEFAULT_NAME
        defaultCategoryMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the categoryMasterList where name equals to UPDATED_NAME
        defaultCategoryMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList where name not equals to DEFAULT_NAME
        defaultCategoryMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the categoryMasterList where name not equals to UPDATED_NAME
        defaultCategoryMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCategoryMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the categoryMasterList where name equals to UPDATED_NAME
        defaultCategoryMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList where name is not null
        defaultCategoryMasterShouldBeFound("name.specified=true");

        // Get all the categoryMasterList where name is null
        defaultCategoryMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoryMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList where name contains DEFAULT_NAME
        defaultCategoryMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the categoryMasterList where name contains UPDATED_NAME
        defaultCategoryMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);

        // Get all the categoryMasterList where name does not contain DEFAULT_NAME
        defaultCategoryMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the categoryMasterList where name does not contain UPDATED_NAME
        defaultCategoryMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCategoryMastersBySubCategoryMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryMasterRepository.saveAndFlush(categoryMaster);
        SubCategoryMaster subCategoryMaster = SubCategoryMasterResourceIT.createEntity(em);
        em.persist(subCategoryMaster);
        em.flush();
        categoryMaster.addSubCategoryMaster(subCategoryMaster);
        categoryMasterRepository.saveAndFlush(categoryMaster);
        Long subCategoryMasterId = subCategoryMaster.getId();

        // Get all the categoryMasterList where subCategoryMaster equals to subCategoryMasterId
        defaultCategoryMasterShouldBeFound("subCategoryMasterId.equals=" + subCategoryMasterId);

        // Get all the categoryMasterList where subCategoryMaster equals to subCategoryMasterId + 1
        defaultCategoryMasterShouldNotBeFound("subCategoryMasterId.equals=" + (subCategoryMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryMasterShouldBeFound(String filter) throws Exception {
        restCategoryMasterMockMvc.perform(get("/api/category-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCategoryMasterMockMvc.perform(get("/api/category-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryMasterShouldNotBeFound(String filter) throws Exception {
        restCategoryMasterMockMvc.perform(get("/api/category-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryMasterMockMvc.perform(get("/api/category-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryMaster() throws Exception {
        // Get the categoryMaster
        restCategoryMasterMockMvc.perform(get("/api/category-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryMaster() throws Exception {
        // Initialize the database
        categoryMasterService.save(categoryMaster);

        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();

        // Update the categoryMaster
        CategoryMaster updatedCategoryMaster = categoryMasterRepository.findById(categoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryMaster are not directly saved in db
        em.detach(updatedCategoryMaster);
        updatedCategoryMaster
            .name(UPDATED_NAME);

        restCategoryMasterMockMvc.perform(put("/api/category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoryMaster)))
            .andExpect(status().isOk());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
        CategoryMaster testCategoryMaster = categoryMasterList.get(categoryMasterList.size() - 1);
        assertThat(testCategoryMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = categoryMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMasterMockMvc.perform(put("/api/category-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryMaster in the database
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryMaster() throws Exception {
        // Initialize the database
        categoryMasterService.save(categoryMaster);

        int databaseSizeBeforeDelete = categoryMasterRepository.findAll().size();

        // Delete the categoryMaster
        restCategoryMasterMockMvc.perform(delete("/api/category-masters/{id}", categoryMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        assertThat(categoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
