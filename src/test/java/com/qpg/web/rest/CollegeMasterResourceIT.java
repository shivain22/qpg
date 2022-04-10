package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.CollegeMaster;
import com.qpg.domain.DepartmentMaster;
import com.qpg.repository.CollegeMasterRepository;
import com.qpg.service.CollegeMasterService;
import com.qpg.service.dto.CollegeMasterCriteria;
import com.qpg.service.CollegeMasterQueryService;

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
 * Integration tests for the {@link CollegeMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CollegeMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CollegeMasterRepository collegeMasterRepository;

    @Autowired
    private CollegeMasterService collegeMasterService;

    @Autowired
    private CollegeMasterQueryService collegeMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollegeMasterMockMvc;

    private CollegeMaster collegeMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollegeMaster createEntity(EntityManager em) {
        CollegeMaster collegeMaster = new CollegeMaster()
            .name(DEFAULT_NAME);
        return collegeMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollegeMaster createUpdatedEntity(EntityManager em) {
        CollegeMaster collegeMaster = new CollegeMaster()
            .name(UPDATED_NAME);
        return collegeMaster;
    }

    @BeforeEach
    public void initTest() {
        collegeMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollegeMaster() throws Exception {
        int databaseSizeBeforeCreate = collegeMasterRepository.findAll().size();
        // Create the CollegeMaster
        restCollegeMasterMockMvc.perform(post("/api/college-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collegeMaster)))
            .andExpect(status().isCreated());

        // Validate the CollegeMaster in the database
        List<CollegeMaster> collegeMasterList = collegeMasterRepository.findAll();
        assertThat(collegeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CollegeMaster testCollegeMaster = collegeMasterList.get(collegeMasterList.size() - 1);
        assertThat(testCollegeMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCollegeMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collegeMasterRepository.findAll().size();

        // Create the CollegeMaster with an existing ID
        collegeMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollegeMasterMockMvc.perform(post("/api/college-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collegeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CollegeMaster in the database
        List<CollegeMaster> collegeMasterList = collegeMasterRepository.findAll();
        assertThat(collegeMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collegeMasterRepository.findAll().size();
        // set the field null
        collegeMaster.setName(null);

        // Create the CollegeMaster, which fails.


        restCollegeMasterMockMvc.perform(post("/api/college-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collegeMaster)))
            .andExpect(status().isBadRequest());

        List<CollegeMaster> collegeMasterList = collegeMasterRepository.findAll();
        assertThat(collegeMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollegeMasters() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList
        restCollegeMasterMockMvc.perform(get("/api/college-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collegeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCollegeMaster() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get the collegeMaster
        restCollegeMasterMockMvc.perform(get("/api/college-masters/{id}", collegeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collegeMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCollegeMastersByIdFiltering() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        Long id = collegeMaster.getId();

        defaultCollegeMasterShouldBeFound("id.equals=" + id);
        defaultCollegeMasterShouldNotBeFound("id.notEquals=" + id);

        defaultCollegeMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCollegeMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultCollegeMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCollegeMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCollegeMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList where name equals to DEFAULT_NAME
        defaultCollegeMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the collegeMasterList where name equals to UPDATED_NAME
        defaultCollegeMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollegeMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList where name not equals to DEFAULT_NAME
        defaultCollegeMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the collegeMasterList where name not equals to UPDATED_NAME
        defaultCollegeMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollegeMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCollegeMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the collegeMasterList where name equals to UPDATED_NAME
        defaultCollegeMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollegeMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList where name is not null
        defaultCollegeMasterShouldBeFound("name.specified=true");

        // Get all the collegeMasterList where name is null
        defaultCollegeMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCollegeMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList where name contains DEFAULT_NAME
        defaultCollegeMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the collegeMasterList where name contains UPDATED_NAME
        defaultCollegeMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollegeMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);

        // Get all the collegeMasterList where name does not contain DEFAULT_NAME
        defaultCollegeMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the collegeMasterList where name does not contain UPDATED_NAME
        defaultCollegeMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCollegeMastersByDepartmentMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        collegeMasterRepository.saveAndFlush(collegeMaster);
        DepartmentMaster departmentMaster = DepartmentMasterResourceIT.createEntity(em);
        em.persist(departmentMaster);
        em.flush();
        collegeMaster.addDepartmentMaster(departmentMaster);
        collegeMasterRepository.saveAndFlush(collegeMaster);
        Long departmentMasterId = departmentMaster.getId();

        // Get all the collegeMasterList where departmentMaster equals to departmentMasterId
        defaultCollegeMasterShouldBeFound("departmentMasterId.equals=" + departmentMasterId);

        // Get all the collegeMasterList where departmentMaster equals to departmentMasterId + 1
        defaultCollegeMasterShouldNotBeFound("departmentMasterId.equals=" + (departmentMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCollegeMasterShouldBeFound(String filter) throws Exception {
        restCollegeMasterMockMvc.perform(get("/api/college-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collegeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCollegeMasterMockMvc.perform(get("/api/college-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCollegeMasterShouldNotBeFound(String filter) throws Exception {
        restCollegeMasterMockMvc.perform(get("/api/college-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCollegeMasterMockMvc.perform(get("/api/college-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCollegeMaster() throws Exception {
        // Get the collegeMaster
        restCollegeMasterMockMvc.perform(get("/api/college-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollegeMaster() throws Exception {
        // Initialize the database
        collegeMasterService.save(collegeMaster);

        int databaseSizeBeforeUpdate = collegeMasterRepository.findAll().size();

        // Update the collegeMaster
        CollegeMaster updatedCollegeMaster = collegeMasterRepository.findById(collegeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCollegeMaster are not directly saved in db
        em.detach(updatedCollegeMaster);
        updatedCollegeMaster
            .name(UPDATED_NAME);

        restCollegeMasterMockMvc.perform(put("/api/college-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollegeMaster)))
            .andExpect(status().isOk());

        // Validate the CollegeMaster in the database
        List<CollegeMaster> collegeMasterList = collegeMasterRepository.findAll();
        assertThat(collegeMasterList).hasSize(databaseSizeBeforeUpdate);
        CollegeMaster testCollegeMaster = collegeMasterList.get(collegeMasterList.size() - 1);
        assertThat(testCollegeMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCollegeMaster() throws Exception {
        int databaseSizeBeforeUpdate = collegeMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegeMasterMockMvc.perform(put("/api/college-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collegeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CollegeMaster in the database
        List<CollegeMaster> collegeMasterList = collegeMasterRepository.findAll();
        assertThat(collegeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollegeMaster() throws Exception {
        // Initialize the database
        collegeMasterService.save(collegeMaster);

        int databaseSizeBeforeDelete = collegeMasterRepository.findAll().size();

        // Delete the collegeMaster
        restCollegeMasterMockMvc.perform(delete("/api/college-masters/{id}", collegeMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollegeMaster> collegeMasterList = collegeMasterRepository.findAll();
        assertThat(collegeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
