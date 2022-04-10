package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.CourseMaster;
import com.qpg.domain.DepartmentMaster;
import com.qpg.domain.CategoryMaster;
import com.qpg.repository.CourseMasterRepository;
import com.qpg.service.CourseMasterService;
import com.qpg.service.dto.CourseMasterCriteria;
import com.qpg.service.CourseMasterQueryService;

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
 * Integration tests for the {@link CourseMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CourseMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CourseMasterRepository courseMasterRepository;

    @Autowired
    private CourseMasterService courseMasterService;

    @Autowired
    private CourseMasterQueryService courseMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseMasterMockMvc;

    private CourseMaster courseMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseMaster createEntity(EntityManager em) {
        CourseMaster courseMaster = new CourseMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        DepartmentMaster departmentMaster;
        if (TestUtil.findAll(em, DepartmentMaster.class).isEmpty()) {
            departmentMaster = DepartmentMasterResourceIT.createEntity(em);
            em.persist(departmentMaster);
            em.flush();
        } else {
            departmentMaster = TestUtil.findAll(em, DepartmentMaster.class).get(0);
        }
        courseMaster.setDepartmentMaster(departmentMaster);
        return courseMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseMaster createUpdatedEntity(EntityManager em) {
        CourseMaster courseMaster = new CourseMaster()
            .name(UPDATED_NAME);
        // Add required entity
        DepartmentMaster departmentMaster;
        if (TestUtil.findAll(em, DepartmentMaster.class).isEmpty()) {
            departmentMaster = DepartmentMasterResourceIT.createUpdatedEntity(em);
            em.persist(departmentMaster);
            em.flush();
        } else {
            departmentMaster = TestUtil.findAll(em, DepartmentMaster.class).get(0);
        }
        courseMaster.setDepartmentMaster(departmentMaster);
        return courseMaster;
    }

    @BeforeEach
    public void initTest() {
        courseMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseMaster() throws Exception {
        int databaseSizeBeforeCreate = courseMasterRepository.findAll().size();
        // Create the CourseMaster
        restCourseMasterMockMvc.perform(post("/api/course-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseMaster)))
            .andExpect(status().isCreated());

        // Validate the CourseMaster in the database
        List<CourseMaster> courseMasterList = courseMasterRepository.findAll();
        assertThat(courseMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CourseMaster testCourseMaster = courseMasterList.get(courseMasterList.size() - 1);
        assertThat(testCourseMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCourseMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseMasterRepository.findAll().size();

        // Create the CourseMaster with an existing ID
        courseMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMasterMockMvc.perform(post("/api/course-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CourseMaster in the database
        List<CourseMaster> courseMasterList = courseMasterRepository.findAll();
        assertThat(courseMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseMasterRepository.findAll().size();
        // set the field null
        courseMaster.setName(null);

        // Create the CourseMaster, which fails.


        restCourseMasterMockMvc.perform(post("/api/course-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseMaster)))
            .andExpect(status().isBadRequest());

        List<CourseMaster> courseMasterList = courseMasterRepository.findAll();
        assertThat(courseMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseMasters() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList
        restCourseMasterMockMvc.perform(get("/api/course-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCourseMaster() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get the courseMaster
        restCourseMasterMockMvc.perform(get("/api/course-masters/{id}", courseMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCourseMastersByIdFiltering() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        Long id = courseMaster.getId();

        defaultCourseMasterShouldBeFound("id.equals=" + id);
        defaultCourseMasterShouldNotBeFound("id.notEquals=" + id);

        defaultCourseMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourseMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultCourseMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourseMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCourseMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList where name equals to DEFAULT_NAME
        defaultCourseMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the courseMasterList where name equals to UPDATED_NAME
        defaultCourseMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList where name not equals to DEFAULT_NAME
        defaultCourseMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the courseMasterList where name not equals to UPDATED_NAME
        defaultCourseMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCourseMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the courseMasterList where name equals to UPDATED_NAME
        defaultCourseMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList where name is not null
        defaultCourseMasterShouldBeFound("name.specified=true");

        // Get all the courseMasterList where name is null
        defaultCourseMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourseMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList where name contains DEFAULT_NAME
        defaultCourseMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the courseMasterList where name contains UPDATED_NAME
        defaultCourseMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCourseMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);

        // Get all the courseMasterList where name does not contain DEFAULT_NAME
        defaultCourseMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the courseMasterList where name does not contain UPDATED_NAME
        defaultCourseMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCourseMastersByDepartmentMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        DepartmentMaster departmentMaster = courseMaster.getDepartmentMaster();
        courseMasterRepository.saveAndFlush(courseMaster);
        Long departmentMasterId = departmentMaster.getId();

        // Get all the courseMasterList where departmentMaster equals to departmentMasterId
        defaultCourseMasterShouldBeFound("departmentMasterId.equals=" + departmentMasterId);

        // Get all the courseMasterList where departmentMaster equals to departmentMasterId + 1
        defaultCourseMasterShouldNotBeFound("departmentMasterId.equals=" + (departmentMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllCourseMastersByCategoryMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        courseMasterRepository.saveAndFlush(courseMaster);
        CategoryMaster categoryMaster = CategoryMasterResourceIT.createEntity(em);
        em.persist(categoryMaster);
        em.flush();
        courseMaster.addCategoryMaster(categoryMaster);
        courseMasterRepository.saveAndFlush(courseMaster);
        Long categoryMasterId = categoryMaster.getId();

        // Get all the courseMasterList where categoryMaster equals to categoryMasterId
        defaultCourseMasterShouldBeFound("categoryMasterId.equals=" + categoryMasterId);

        // Get all the courseMasterList where categoryMaster equals to categoryMasterId + 1
        defaultCourseMasterShouldNotBeFound("categoryMasterId.equals=" + (categoryMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourseMasterShouldBeFound(String filter) throws Exception {
        restCourseMasterMockMvc.perform(get("/api/course-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCourseMasterMockMvc.perform(get("/api/course-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourseMasterShouldNotBeFound(String filter) throws Exception {
        restCourseMasterMockMvc.perform(get("/api/course-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourseMasterMockMvc.perform(get("/api/course-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCourseMaster() throws Exception {
        // Get the courseMaster
        restCourseMasterMockMvc.perform(get("/api/course-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseMaster() throws Exception {
        // Initialize the database
        courseMasterService.save(courseMaster);

        int databaseSizeBeforeUpdate = courseMasterRepository.findAll().size();

        // Update the courseMaster
        CourseMaster updatedCourseMaster = courseMasterRepository.findById(courseMaster.getId()).get();
        // Disconnect from session so that the updates on updatedCourseMaster are not directly saved in db
        em.detach(updatedCourseMaster);
        updatedCourseMaster
            .name(UPDATED_NAME);

        restCourseMasterMockMvc.perform(put("/api/course-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourseMaster)))
            .andExpect(status().isOk());

        // Validate the CourseMaster in the database
        List<CourseMaster> courseMasterList = courseMasterRepository.findAll();
        assertThat(courseMasterList).hasSize(databaseSizeBeforeUpdate);
        CourseMaster testCourseMaster = courseMasterList.get(courseMasterList.size() - 1);
        assertThat(testCourseMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseMaster() throws Exception {
        int databaseSizeBeforeUpdate = courseMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMasterMockMvc.perform(put("/api/course-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseMaster)))
            .andExpect(status().isBadRequest());

        // Validate the CourseMaster in the database
        List<CourseMaster> courseMasterList = courseMasterRepository.findAll();
        assertThat(courseMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourseMaster() throws Exception {
        // Initialize the database
        courseMasterService.save(courseMaster);

        int databaseSizeBeforeDelete = courseMasterRepository.findAll().size();

        // Delete the courseMaster
        restCourseMasterMockMvc.perform(delete("/api/course-masters/{id}", courseMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseMaster> courseMasterList = courseMasterRepository.findAll();
        assertThat(courseMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
