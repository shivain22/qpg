package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.SubSubjectMaster;
import com.qpg.domain.SubjectMaster;
import com.qpg.domain.TopicMaster;
import com.qpg.repository.SubSubjectMasterRepository;
import com.qpg.service.SubSubjectMasterService;
import com.qpg.service.dto.SubSubjectMasterCriteria;
import com.qpg.service.SubSubjectMasterQueryService;

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
 * Integration tests for the {@link SubSubjectMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubSubjectMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubSubjectMasterRepository subSubjectMasterRepository;

    @Autowired
    private SubSubjectMasterService subSubjectMasterService;

    @Autowired
    private SubSubjectMasterQueryService subSubjectMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubSubjectMasterMockMvc;

    private SubSubjectMaster subSubjectMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubSubjectMaster createEntity(EntityManager em) {
        SubSubjectMaster subSubjectMaster = new SubSubjectMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        SubjectMaster subjectMaster;
        if (TestUtil.findAll(em, SubjectMaster.class).isEmpty()) {
            subjectMaster = SubjectMasterResourceIT.createEntity(em);
            em.persist(subjectMaster);
            em.flush();
        } else {
            subjectMaster = TestUtil.findAll(em, SubjectMaster.class).get(0);
        }
        subSubjectMaster.setSubjectMaster(subjectMaster);
        return subSubjectMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubSubjectMaster createUpdatedEntity(EntityManager em) {
        SubSubjectMaster subSubjectMaster = new SubSubjectMaster()
            .name(UPDATED_NAME);
        // Add required entity
        SubjectMaster subjectMaster;
        if (TestUtil.findAll(em, SubjectMaster.class).isEmpty()) {
            subjectMaster = SubjectMasterResourceIT.createUpdatedEntity(em);
            em.persist(subjectMaster);
            em.flush();
        } else {
            subjectMaster = TestUtil.findAll(em, SubjectMaster.class).get(0);
        }
        subSubjectMaster.setSubjectMaster(subjectMaster);
        return subSubjectMaster;
    }

    @BeforeEach
    public void initTest() {
        subSubjectMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubSubjectMaster() throws Exception {
        int databaseSizeBeforeCreate = subSubjectMasterRepository.findAll().size();
        // Create the SubSubjectMaster
        restSubSubjectMasterMockMvc.perform(post("/api/sub-subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subSubjectMaster)))
            .andExpect(status().isCreated());

        // Validate the SubSubjectMaster in the database
        List<SubSubjectMaster> subSubjectMasterList = subSubjectMasterRepository.findAll();
        assertThat(subSubjectMasterList).hasSize(databaseSizeBeforeCreate + 1);
        SubSubjectMaster testSubSubjectMaster = subSubjectMasterList.get(subSubjectMasterList.size() - 1);
        assertThat(testSubSubjectMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubSubjectMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subSubjectMasterRepository.findAll().size();

        // Create the SubSubjectMaster with an existing ID
        subSubjectMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubSubjectMasterMockMvc.perform(post("/api/sub-subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subSubjectMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubSubjectMaster in the database
        List<SubSubjectMaster> subSubjectMasterList = subSubjectMasterRepository.findAll();
        assertThat(subSubjectMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subSubjectMasterRepository.findAll().size();
        // set the field null
        subSubjectMaster.setName(null);

        // Create the SubSubjectMaster, which fails.


        restSubSubjectMasterMockMvc.perform(post("/api/sub-subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subSubjectMaster)))
            .andExpect(status().isBadRequest());

        List<SubSubjectMaster> subSubjectMasterList = subSubjectMasterRepository.findAll();
        assertThat(subSubjectMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubSubjectMasters() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subSubjectMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSubSubjectMaster() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get the subSubjectMaster
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters/{id}", subSubjectMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subSubjectMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getSubSubjectMastersByIdFiltering() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        Long id = subSubjectMaster.getId();

        defaultSubSubjectMasterShouldBeFound("id.equals=" + id);
        defaultSubSubjectMasterShouldNotBeFound("id.notEquals=" + id);

        defaultSubSubjectMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubSubjectMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultSubSubjectMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubSubjectMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubSubjectMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList where name equals to DEFAULT_NAME
        defaultSubSubjectMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the subSubjectMasterList where name equals to UPDATED_NAME
        defaultSubSubjectMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubSubjectMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList where name not equals to DEFAULT_NAME
        defaultSubSubjectMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the subSubjectMasterList where name not equals to UPDATED_NAME
        defaultSubSubjectMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubSubjectMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSubSubjectMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the subSubjectMasterList where name equals to UPDATED_NAME
        defaultSubSubjectMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubSubjectMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList where name is not null
        defaultSubSubjectMasterShouldBeFound("name.specified=true");

        // Get all the subSubjectMasterList where name is null
        defaultSubSubjectMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubSubjectMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList where name contains DEFAULT_NAME
        defaultSubSubjectMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the subSubjectMasterList where name contains UPDATED_NAME
        defaultSubSubjectMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubSubjectMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);

        // Get all the subSubjectMasterList where name does not contain DEFAULT_NAME
        defaultSubSubjectMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the subSubjectMasterList where name does not contain UPDATED_NAME
        defaultSubSubjectMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSubSubjectMastersBySubjectMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubjectMaster subjectMaster = subSubjectMaster.getSubjectMaster();
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);
        Long subjectMasterId = subjectMaster.getId();

        // Get all the subSubjectMasterList where subjectMaster equals to subjectMasterId
        defaultSubSubjectMasterShouldBeFound("subjectMasterId.equals=" + subjectMasterId);

        // Get all the subSubjectMasterList where subjectMaster equals to subjectMasterId + 1
        defaultSubSubjectMasterShouldNotBeFound("subjectMasterId.equals=" + (subjectMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllSubSubjectMastersByTopicMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);
        TopicMaster topicMaster = TopicMasterResourceIT.createEntity(em);
        em.persist(topicMaster);
        em.flush();
        subSubjectMaster.addTopicMaster(topicMaster);
        subSubjectMasterRepository.saveAndFlush(subSubjectMaster);
        Long topicMasterId = topicMaster.getId();

        // Get all the subSubjectMasterList where topicMaster equals to topicMasterId
        defaultSubSubjectMasterShouldBeFound("topicMasterId.equals=" + topicMasterId);

        // Get all the subSubjectMasterList where topicMaster equals to topicMasterId + 1
        defaultSubSubjectMasterShouldNotBeFound("topicMasterId.equals=" + (topicMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubSubjectMasterShouldBeFound(String filter) throws Exception {
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subSubjectMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubSubjectMasterShouldNotBeFound(String filter) throws Exception {
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSubSubjectMaster() throws Exception {
        // Get the subSubjectMaster
        restSubSubjectMasterMockMvc.perform(get("/api/sub-subject-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubSubjectMaster() throws Exception {
        // Initialize the database
        subSubjectMasterService.save(subSubjectMaster);

        int databaseSizeBeforeUpdate = subSubjectMasterRepository.findAll().size();

        // Update the subSubjectMaster
        SubSubjectMaster updatedSubSubjectMaster = subSubjectMasterRepository.findById(subSubjectMaster.getId()).get();
        // Disconnect from session so that the updates on updatedSubSubjectMaster are not directly saved in db
        em.detach(updatedSubSubjectMaster);
        updatedSubSubjectMaster
            .name(UPDATED_NAME);

        restSubSubjectMasterMockMvc.perform(put("/api/sub-subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubSubjectMaster)))
            .andExpect(status().isOk());

        // Validate the SubSubjectMaster in the database
        List<SubSubjectMaster> subSubjectMasterList = subSubjectMasterRepository.findAll();
        assertThat(subSubjectMasterList).hasSize(databaseSizeBeforeUpdate);
        SubSubjectMaster testSubSubjectMaster = subSubjectMasterList.get(subSubjectMasterList.size() - 1);
        assertThat(testSubSubjectMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubSubjectMaster() throws Exception {
        int databaseSizeBeforeUpdate = subSubjectMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubSubjectMasterMockMvc.perform(put("/api/sub-subject-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subSubjectMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubSubjectMaster in the database
        List<SubSubjectMaster> subSubjectMasterList = subSubjectMasterRepository.findAll();
        assertThat(subSubjectMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubSubjectMaster() throws Exception {
        // Initialize the database
        subSubjectMasterService.save(subSubjectMaster);

        int databaseSizeBeforeDelete = subSubjectMasterRepository.findAll().size();

        // Delete the subSubjectMaster
        restSubSubjectMasterMockMvc.perform(delete("/api/sub-subject-masters/{id}", subSubjectMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubSubjectMaster> subSubjectMasterList = subSubjectMasterRepository.findAll();
        assertThat(subSubjectMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
