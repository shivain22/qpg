package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.TopicMaster;
import com.qpg.domain.SubSubjectMaster;
import com.qpg.repository.TopicMasterRepository;
import com.qpg.service.TopicMasterService;
import com.qpg.service.dto.TopicMasterCriteria;
import com.qpg.service.TopicMasterQueryService;

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
 * Integration tests for the {@link TopicMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TopicMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TopicMasterRepository topicMasterRepository;

    @Autowired
    private TopicMasterService topicMasterService;

    @Autowired
    private TopicMasterQueryService topicMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicMasterMockMvc;

    private TopicMaster topicMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicMaster createEntity(EntityManager em) {
        TopicMaster topicMaster = new TopicMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        SubSubjectMaster subSubjectMaster;
        if (TestUtil.findAll(em, SubSubjectMaster.class).isEmpty()) {
            subSubjectMaster = SubSubjectMasterResourceIT.createEntity(em);
            em.persist(subSubjectMaster);
            em.flush();
        } else {
            subSubjectMaster = TestUtil.findAll(em, SubSubjectMaster.class).get(0);
        }
        topicMaster.setSubSubjectMaster(subSubjectMaster);
        return topicMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopicMaster createUpdatedEntity(EntityManager em) {
        TopicMaster topicMaster = new TopicMaster()
            .name(UPDATED_NAME);
        // Add required entity
        SubSubjectMaster subSubjectMaster;
        if (TestUtil.findAll(em, SubSubjectMaster.class).isEmpty()) {
            subSubjectMaster = SubSubjectMasterResourceIT.createUpdatedEntity(em);
            em.persist(subSubjectMaster);
            em.flush();
        } else {
            subSubjectMaster = TestUtil.findAll(em, SubSubjectMaster.class).get(0);
        }
        topicMaster.setSubSubjectMaster(subSubjectMaster);
        return topicMaster;
    }

    @BeforeEach
    public void initTest() {
        topicMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopicMaster() throws Exception {
        int databaseSizeBeforeCreate = topicMasterRepository.findAll().size();
        // Create the TopicMaster
        restTopicMasterMockMvc.perform(post("/api/topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicMaster)))
            .andExpect(status().isCreated());

        // Validate the TopicMaster in the database
        List<TopicMaster> topicMasterList = topicMasterRepository.findAll();
        assertThat(topicMasterList).hasSize(databaseSizeBeforeCreate + 1);
        TopicMaster testTopicMaster = topicMasterList.get(topicMasterList.size() - 1);
        assertThat(testTopicMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTopicMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topicMasterRepository.findAll().size();

        // Create the TopicMaster with an existing ID
        topicMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicMasterMockMvc.perform(post("/api/topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicMaster)))
            .andExpect(status().isBadRequest());

        // Validate the TopicMaster in the database
        List<TopicMaster> topicMasterList = topicMasterRepository.findAll();
        assertThat(topicMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicMasterRepository.findAll().size();
        // set the field null
        topicMaster.setName(null);

        // Create the TopicMaster, which fails.


        restTopicMasterMockMvc.perform(post("/api/topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicMaster)))
            .andExpect(status().isBadRequest());

        List<TopicMaster> topicMasterList = topicMasterRepository.findAll();
        assertThat(topicMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopicMasters() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList
        restTopicMasterMockMvc.perform(get("/api/topic-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTopicMaster() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get the topicMaster
        restTopicMasterMockMvc.perform(get("/api/topic-masters/{id}", topicMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topicMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTopicMastersByIdFiltering() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        Long id = topicMaster.getId();

        defaultTopicMasterShouldBeFound("id.equals=" + id);
        defaultTopicMasterShouldNotBeFound("id.notEquals=" + id);

        defaultTopicMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTopicMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultTopicMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTopicMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTopicMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList where name equals to DEFAULT_NAME
        defaultTopicMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the topicMasterList where name equals to UPDATED_NAME
        defaultTopicMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTopicMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList where name not equals to DEFAULT_NAME
        defaultTopicMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the topicMasterList where name not equals to UPDATED_NAME
        defaultTopicMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTopicMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTopicMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the topicMasterList where name equals to UPDATED_NAME
        defaultTopicMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTopicMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList where name is not null
        defaultTopicMasterShouldBeFound("name.specified=true");

        // Get all the topicMasterList where name is null
        defaultTopicMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTopicMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList where name contains DEFAULT_NAME
        defaultTopicMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the topicMasterList where name contains UPDATED_NAME
        defaultTopicMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTopicMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        topicMasterRepository.saveAndFlush(topicMaster);

        // Get all the topicMasterList where name does not contain DEFAULT_NAME
        defaultTopicMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the topicMasterList where name does not contain UPDATED_NAME
        defaultTopicMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTopicMastersBySubSubjectMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubSubjectMaster subSubjectMaster = topicMaster.getSubSubjectMaster();
        topicMasterRepository.saveAndFlush(topicMaster);
        Long subSubjectMasterId = subSubjectMaster.getId();

        // Get all the topicMasterList where subSubjectMaster equals to subSubjectMasterId
        defaultTopicMasterShouldBeFound("subSubjectMasterId.equals=" + subSubjectMasterId);

        // Get all the topicMasterList where subSubjectMaster equals to subSubjectMasterId + 1
        defaultTopicMasterShouldNotBeFound("subSubjectMasterId.equals=" + (subSubjectMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTopicMasterShouldBeFound(String filter) throws Exception {
        restTopicMasterMockMvc.perform(get("/api/topic-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topicMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTopicMasterMockMvc.perform(get("/api/topic-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTopicMasterShouldNotBeFound(String filter) throws Exception {
        restTopicMasterMockMvc.perform(get("/api/topic-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTopicMasterMockMvc.perform(get("/api/topic-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTopicMaster() throws Exception {
        // Get the topicMaster
        restTopicMasterMockMvc.perform(get("/api/topic-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopicMaster() throws Exception {
        // Initialize the database
        topicMasterService.save(topicMaster);

        int databaseSizeBeforeUpdate = topicMasterRepository.findAll().size();

        // Update the topicMaster
        TopicMaster updatedTopicMaster = topicMasterRepository.findById(topicMaster.getId()).get();
        // Disconnect from session so that the updates on updatedTopicMaster are not directly saved in db
        em.detach(updatedTopicMaster);
        updatedTopicMaster
            .name(UPDATED_NAME);

        restTopicMasterMockMvc.perform(put("/api/topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTopicMaster)))
            .andExpect(status().isOk());

        // Validate the TopicMaster in the database
        List<TopicMaster> topicMasterList = topicMasterRepository.findAll();
        assertThat(topicMasterList).hasSize(databaseSizeBeforeUpdate);
        TopicMaster testTopicMaster = topicMasterList.get(topicMasterList.size() - 1);
        assertThat(testTopicMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTopicMaster() throws Exception {
        int databaseSizeBeforeUpdate = topicMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicMasterMockMvc.perform(put("/api/topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topicMaster)))
            .andExpect(status().isBadRequest());

        // Validate the TopicMaster in the database
        List<TopicMaster> topicMasterList = topicMasterRepository.findAll();
        assertThat(topicMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTopicMaster() throws Exception {
        // Initialize the database
        topicMasterService.save(topicMaster);

        int databaseSizeBeforeDelete = topicMasterRepository.findAll().size();

        // Delete the topicMaster
        restTopicMasterMockMvc.perform(delete("/api/topic-masters/{id}", topicMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopicMaster> topicMasterList = topicMasterRepository.findAll();
        assertThat(topicMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
