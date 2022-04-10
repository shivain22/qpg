package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.DepartmentMaster;
import com.qpg.domain.CollegeMaster;
import com.qpg.domain.CourseMaster;
import com.qpg.repository.DepartmentMasterRepository;
import com.qpg.service.DepartmentMasterService;
import com.qpg.service.dto.DepartmentMasterCriteria;
import com.qpg.service.DepartmentMasterQueryService;

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
 * Integration tests for the {@link DepartmentMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepartmentMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DepartmentMasterRepository departmentMasterRepository;

    @Autowired
    private DepartmentMasterService departmentMasterService;

    @Autowired
    private DepartmentMasterQueryService departmentMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartmentMasterMockMvc;

    private DepartmentMaster departmentMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartmentMaster createEntity(EntityManager em) {
        DepartmentMaster departmentMaster = new DepartmentMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        CollegeMaster collegeMaster;
        if (TestUtil.findAll(em, CollegeMaster.class).isEmpty()) {
            collegeMaster = CollegeMasterResourceIT.createEntity(em);
            em.persist(collegeMaster);
            em.flush();
        } else {
            collegeMaster = TestUtil.findAll(em, CollegeMaster.class).get(0);
        }
        departmentMaster.setCollegeMaster(collegeMaster);
        return departmentMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartmentMaster createUpdatedEntity(EntityManager em) {
        DepartmentMaster departmentMaster = new DepartmentMaster()
            .name(UPDATED_NAME);
        // Add required entity
        CollegeMaster collegeMaster;
        if (TestUtil.findAll(em, CollegeMaster.class).isEmpty()) {
            collegeMaster = CollegeMasterResourceIT.createUpdatedEntity(em);
            em.persist(collegeMaster);
            em.flush();
        } else {
            collegeMaster = TestUtil.findAll(em, CollegeMaster.class).get(0);
        }
        departmentMaster.setCollegeMaster(collegeMaster);
        return departmentMaster;
    }

    @BeforeEach
    public void initTest() {
        departmentMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartmentMaster() throws Exception {
        int databaseSizeBeforeCreate = departmentMasterRepository.findAll().size();
        // Create the DepartmentMaster
        restDepartmentMasterMockMvc.perform(post("/api/department-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentMaster)))
            .andExpect(status().isCreated());

        // Validate the DepartmentMaster in the database
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findAll();
        assertThat(departmentMasterList).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentMaster testDepartmentMaster = departmentMasterList.get(departmentMasterList.size() - 1);
        assertThat(testDepartmentMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDepartmentMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentMasterRepository.findAll().size();

        // Create the DepartmentMaster with an existing ID
        departmentMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentMasterMockMvc.perform(post("/api/department-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentMaster)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentMaster in the database
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findAll();
        assertThat(departmentMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmentMasterRepository.findAll().size();
        // set the field null
        departmentMaster.setName(null);

        // Create the DepartmentMaster, which fails.


        restDepartmentMasterMockMvc.perform(post("/api/department-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentMaster)))
            .andExpect(status().isBadRequest());

        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findAll();
        assertThat(departmentMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartmentMasters() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList
        restDepartmentMasterMockMvc.perform(get("/api/department-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDepartmentMaster() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get the departmentMaster
        restDepartmentMasterMockMvc.perform(get("/api/department-masters/{id}", departmentMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departmentMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDepartmentMastersByIdFiltering() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        Long id = departmentMaster.getId();

        defaultDepartmentMasterShouldBeFound("id.equals=" + id);
        defaultDepartmentMasterShouldNotBeFound("id.notEquals=" + id);

        defaultDepartmentMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepartmentMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultDepartmentMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepartmentMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDepartmentMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList where name equals to DEFAULT_NAME
        defaultDepartmentMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the departmentMasterList where name equals to UPDATED_NAME
        defaultDepartmentMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDepartmentMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList where name not equals to DEFAULT_NAME
        defaultDepartmentMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the departmentMasterList where name not equals to UPDATED_NAME
        defaultDepartmentMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDepartmentMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDepartmentMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the departmentMasterList where name equals to UPDATED_NAME
        defaultDepartmentMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDepartmentMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList where name is not null
        defaultDepartmentMasterShouldBeFound("name.specified=true");

        // Get all the departmentMasterList where name is null
        defaultDepartmentMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDepartmentMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList where name contains DEFAULT_NAME
        defaultDepartmentMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the departmentMasterList where name contains UPDATED_NAME
        defaultDepartmentMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDepartmentMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);

        // Get all the departmentMasterList where name does not contain DEFAULT_NAME
        defaultDepartmentMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the departmentMasterList where name does not contain UPDATED_NAME
        defaultDepartmentMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDepartmentMastersByCollegeMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CollegeMaster collegeMaster = departmentMaster.getCollegeMaster();
        departmentMasterRepository.saveAndFlush(departmentMaster);
        Long collegeMasterId = collegeMaster.getId();

        // Get all the departmentMasterList where collegeMaster equals to collegeMasterId
        defaultDepartmentMasterShouldBeFound("collegeMasterId.equals=" + collegeMasterId);

        // Get all the departmentMasterList where collegeMaster equals to collegeMasterId + 1
        defaultDepartmentMasterShouldNotBeFound("collegeMasterId.equals=" + (collegeMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllDepartmentMastersByCourseMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentMasterRepository.saveAndFlush(departmentMaster);
        CourseMaster courseMaster = CourseMasterResourceIT.createEntity(em);
        em.persist(courseMaster);
        em.flush();
        departmentMaster.addCourseMaster(courseMaster);
        departmentMasterRepository.saveAndFlush(departmentMaster);
        Long courseMasterId = courseMaster.getId();

        // Get all the departmentMasterList where courseMaster equals to courseMasterId
        defaultDepartmentMasterShouldBeFound("courseMasterId.equals=" + courseMasterId);

        // Get all the departmentMasterList where courseMaster equals to courseMasterId + 1
        defaultDepartmentMasterShouldNotBeFound("courseMasterId.equals=" + (courseMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartmentMasterShouldBeFound(String filter) throws Exception {
        restDepartmentMasterMockMvc.perform(get("/api/department-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDepartmentMasterMockMvc.perform(get("/api/department-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartmentMasterShouldNotBeFound(String filter) throws Exception {
        restDepartmentMasterMockMvc.perform(get("/api/department-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentMasterMockMvc.perform(get("/api/department-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDepartmentMaster() throws Exception {
        // Get the departmentMaster
        restDepartmentMasterMockMvc.perform(get("/api/department-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartmentMaster() throws Exception {
        // Initialize the database
        departmentMasterService.save(departmentMaster);

        int databaseSizeBeforeUpdate = departmentMasterRepository.findAll().size();

        // Update the departmentMaster
        DepartmentMaster updatedDepartmentMaster = departmentMasterRepository.findById(departmentMaster.getId()).get();
        // Disconnect from session so that the updates on updatedDepartmentMaster are not directly saved in db
        em.detach(updatedDepartmentMaster);
        updatedDepartmentMaster
            .name(UPDATED_NAME);

        restDepartmentMasterMockMvc.perform(put("/api/department-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartmentMaster)))
            .andExpect(status().isOk());

        // Validate the DepartmentMaster in the database
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findAll();
        assertThat(departmentMasterList).hasSize(databaseSizeBeforeUpdate);
        DepartmentMaster testDepartmentMaster = departmentMasterList.get(departmentMasterList.size() - 1);
        assertThat(testDepartmentMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartmentMaster() throws Exception {
        int databaseSizeBeforeUpdate = departmentMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMasterMockMvc.perform(put("/api/department-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentMaster)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentMaster in the database
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findAll();
        assertThat(departmentMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartmentMaster() throws Exception {
        // Initialize the database
        departmentMasterService.save(departmentMaster);

        int databaseSizeBeforeDelete = departmentMasterRepository.findAll().size();

        // Delete the departmentMaster
        restDepartmentMasterMockMvc.perform(delete("/api/department-masters/{id}", departmentMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findAll();
        assertThat(departmentMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
