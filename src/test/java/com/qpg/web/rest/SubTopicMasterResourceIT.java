package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.SubTopicMaster;
import com.qpg.domain.TopicMaster;
import com.qpg.domain.QuestionMaster;
import com.qpg.repository.SubTopicMasterRepository;
import com.qpg.service.SubTopicMasterService;
import com.qpg.service.dto.SubTopicMasterCriteria;
import com.qpg.service.SubTopicMasterQueryService;

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
 * Integration tests for the {@link SubTopicMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubTopicMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubTopicMasterRepository subTopicMasterRepository;

    @Autowired
    private SubTopicMasterService subTopicMasterService;

    @Autowired
    private SubTopicMasterQueryService subTopicMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubTopicMasterMockMvc;

    private SubTopicMaster subTopicMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTopicMaster createEntity(EntityManager em) {
        SubTopicMaster subTopicMaster = new SubTopicMaster()
            .name(DEFAULT_NAME);
        // Add required entity
        TopicMaster topicMaster;
        if (TestUtil.findAll(em, TopicMaster.class).isEmpty()) {
            topicMaster = TopicMasterResourceIT.createEntity(em);
            em.persist(topicMaster);
            em.flush();
        } else {
            topicMaster = TestUtil.findAll(em, TopicMaster.class).get(0);
        }
        subTopicMaster.setTopicMaster(topicMaster);
        return subTopicMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTopicMaster createUpdatedEntity(EntityManager em) {
        SubTopicMaster subTopicMaster = new SubTopicMaster()
            .name(UPDATED_NAME);
        // Add required entity
        TopicMaster topicMaster;
        if (TestUtil.findAll(em, TopicMaster.class).isEmpty()) {
            topicMaster = TopicMasterResourceIT.createUpdatedEntity(em);
            em.persist(topicMaster);
            em.flush();
        } else {
            topicMaster = TestUtil.findAll(em, TopicMaster.class).get(0);
        }
        subTopicMaster.setTopicMaster(topicMaster);
        return subTopicMaster;
    }

    @BeforeEach
    public void initTest() {
        subTopicMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubTopicMaster() throws Exception {
        int databaseSizeBeforeCreate = subTopicMasterRepository.findAll().size();
        // Create the SubTopicMaster
        restSubTopicMasterMockMvc.perform(post("/api/sub-topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subTopicMaster)))
            .andExpect(status().isCreated());

        // Validate the SubTopicMaster in the database
        List<SubTopicMaster> subTopicMasterList = subTopicMasterRepository.findAll();
        assertThat(subTopicMasterList).hasSize(databaseSizeBeforeCreate + 1);
        SubTopicMaster testSubTopicMaster = subTopicMasterList.get(subTopicMasterList.size() - 1);
        assertThat(testSubTopicMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubTopicMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subTopicMasterRepository.findAll().size();

        // Create the SubTopicMaster with an existing ID
        subTopicMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubTopicMasterMockMvc.perform(post("/api/sub-topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subTopicMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubTopicMaster in the database
        List<SubTopicMaster> subTopicMasterList = subTopicMasterRepository.findAll();
        assertThat(subTopicMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subTopicMasterRepository.findAll().size();
        // set the field null
        subTopicMaster.setName(null);

        // Create the SubTopicMaster, which fails.


        restSubTopicMasterMockMvc.perform(post("/api/sub-topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subTopicMaster)))
            .andExpect(status().isBadRequest());

        List<SubTopicMaster> subTopicMasterList = subTopicMasterRepository.findAll();
        assertThat(subTopicMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubTopicMasters() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subTopicMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getSubTopicMaster() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get the subTopicMaster
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters/{id}", subTopicMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subTopicMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getSubTopicMastersByIdFiltering() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        Long id = subTopicMaster.getId();

        defaultSubTopicMasterShouldBeFound("id.equals=" + id);
        defaultSubTopicMasterShouldNotBeFound("id.notEquals=" + id);

        defaultSubTopicMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubTopicMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultSubTopicMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubTopicMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubTopicMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList where name equals to DEFAULT_NAME
        defaultSubTopicMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the subTopicMasterList where name equals to UPDATED_NAME
        defaultSubTopicMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubTopicMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList where name not equals to DEFAULT_NAME
        defaultSubTopicMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the subTopicMasterList where name not equals to UPDATED_NAME
        defaultSubTopicMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubTopicMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSubTopicMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the subTopicMasterList where name equals to UPDATED_NAME
        defaultSubTopicMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubTopicMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList where name is not null
        defaultSubTopicMasterShouldBeFound("name.specified=true");

        // Get all the subTopicMasterList where name is null
        defaultSubTopicMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubTopicMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList where name contains DEFAULT_NAME
        defaultSubTopicMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the subTopicMasterList where name contains UPDATED_NAME
        defaultSubTopicMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSubTopicMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);

        // Get all the subTopicMasterList where name does not contain DEFAULT_NAME
        defaultSubTopicMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the subTopicMasterList where name does not contain UPDATED_NAME
        defaultSubTopicMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSubTopicMastersByTopicMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        TopicMaster topicMaster = subTopicMaster.getTopicMaster();
        subTopicMasterRepository.saveAndFlush(subTopicMaster);
        Long topicMasterId = topicMaster.getId();

        // Get all the subTopicMasterList where topicMaster equals to topicMasterId
        defaultSubTopicMasterShouldBeFound("topicMasterId.equals=" + topicMasterId);

        // Get all the subTopicMasterList where topicMaster equals to topicMasterId + 1
        defaultSubTopicMasterShouldNotBeFound("topicMasterId.equals=" + (topicMasterId + 1));
    }


    @Test
    @Transactional
    public void getAllSubTopicMastersByQuestionMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        subTopicMasterRepository.saveAndFlush(subTopicMaster);
        QuestionMaster questionMaster = QuestionMasterResourceIT.createEntity(em);
        em.persist(questionMaster);
        em.flush();
        subTopicMasterRepository.saveAndFlush(subTopicMaster);
        Long questionMasterId = questionMaster.getId();

        // Get all the subTopicMasterList where questionMaster equals to questionMasterId
        defaultSubTopicMasterShouldBeFound("questionMasterId.equals=" + questionMasterId);

        // Get all the subTopicMasterList where questionMaster equals to questionMasterId + 1
        defaultSubTopicMasterShouldNotBeFound("questionMasterId.equals=" + (questionMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubTopicMasterShouldBeFound(String filter) throws Exception {
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subTopicMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubTopicMasterShouldNotBeFound(String filter) throws Exception {
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSubTopicMaster() throws Exception {
        // Get the subTopicMaster
        restSubTopicMasterMockMvc.perform(get("/api/sub-topic-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubTopicMaster() throws Exception {
        // Initialize the database
        subTopicMasterService.save(subTopicMaster);

        int databaseSizeBeforeUpdate = subTopicMasterRepository.findAll().size();

        // Update the subTopicMaster
        SubTopicMaster updatedSubTopicMaster = subTopicMasterRepository.findById(subTopicMaster.getId()).get();
        // Disconnect from session so that the updates on updatedSubTopicMaster are not directly saved in db
        em.detach(updatedSubTopicMaster);
        updatedSubTopicMaster
            .name(UPDATED_NAME);

        restSubTopicMasterMockMvc.perform(put("/api/sub-topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubTopicMaster)))
            .andExpect(status().isOk());

        // Validate the SubTopicMaster in the database
        List<SubTopicMaster> subTopicMasterList = subTopicMasterRepository.findAll();
        assertThat(subTopicMasterList).hasSize(databaseSizeBeforeUpdate);
        SubTopicMaster testSubTopicMaster = subTopicMasterList.get(subTopicMasterList.size() - 1);
        assertThat(testSubTopicMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubTopicMaster() throws Exception {
        int databaseSizeBeforeUpdate = subTopicMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTopicMasterMockMvc.perform(put("/api/sub-topic-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subTopicMaster)))
            .andExpect(status().isBadRequest());

        // Validate the SubTopicMaster in the database
        List<SubTopicMaster> subTopicMasterList = subTopicMasterRepository.findAll();
        assertThat(subTopicMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubTopicMaster() throws Exception {
        // Initialize the database
        subTopicMasterService.save(subTopicMaster);

        int databaseSizeBeforeDelete = subTopicMasterRepository.findAll().size();

        // Delete the subTopicMaster
        restSubTopicMasterMockMvc.perform(delete("/api/sub-topic-masters/{id}", subTopicMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubTopicMaster> subTopicMasterList = subTopicMasterRepository.findAll();
        assertThat(subTopicMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
