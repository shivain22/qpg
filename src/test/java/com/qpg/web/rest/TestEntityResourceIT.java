package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.TestEntity;
import com.qpg.repository.TestEntityRepository;
import com.qpg.service.TestEntityService;
import com.qpg.service.dto.TestEntityCriteria;
import com.qpg.service.TestEntityQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TestEntityResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TestEntityResourceIT {

    private static final byte[] DEFAULT_TEST_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TEST_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TEST_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEST_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    @Autowired
    private TestEntityRepository testEntityRepository;

    @Autowired
    private TestEntityService testEntityService;

    @Autowired
    private TestEntityQueryService testEntityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestEntityMockMvc;

    private TestEntity testEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestEntity createEntity(EntityManager em) {
        TestEntity testEntity = new TestEntity()
            .testFile(DEFAULT_TEST_FILE)
            .testFileContentType(DEFAULT_TEST_FILE_CONTENT_TYPE)
            .fileName(DEFAULT_FILE_NAME);
        return testEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestEntity createUpdatedEntity(EntityManager em) {
        TestEntity testEntity = new TestEntity()
            .testFile(UPDATED_TEST_FILE)
            .testFileContentType(UPDATED_TEST_FILE_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME);
        return testEntity;
    }

    @BeforeEach
    public void initTest() {
        testEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestEntity() throws Exception {
        int databaseSizeBeforeCreate = testEntityRepository.findAll().size();
        // Create the TestEntity
        restTestEntityMockMvc.perform(post("/api/test-entities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testEntity)))
            .andExpect(status().isCreated());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeCreate + 1);
        TestEntity testTestEntity = testEntityList.get(testEntityList.size() - 1);
        assertThat(testTestEntity.getTestFile()).isEqualTo(DEFAULT_TEST_FILE);
        assertThat(testTestEntity.getTestFileContentType()).isEqualTo(DEFAULT_TEST_FILE_CONTENT_TYPE);
        assertThat(testTestEntity.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    public void createTestEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testEntityRepository.findAll().size();

        // Create the TestEntity with an existing ID
        testEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestEntityMockMvc.perform(post("/api/test-entities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testEntity)))
            .andExpect(status().isBadRequest());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = testEntityRepository.findAll().size();
        // set the field null
        testEntity.setFileName(null);

        // Create the TestEntity, which fails.


        restTestEntityMockMvc.perform(post("/api/test-entities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testEntity)))
            .andExpect(status().isBadRequest());

        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestEntities() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList
        restTestEntityMockMvc.perform(get("/api/test-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].testFileContentType").value(hasItem(DEFAULT_TEST_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].testFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEST_FILE))))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)));
    }
    
    @Test
    @Transactional
    public void getTestEntity() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get the testEntity
        restTestEntityMockMvc.perform(get("/api/test-entities/{id}", testEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testEntity.getId().intValue()))
            .andExpect(jsonPath("$.testFileContentType").value(DEFAULT_TEST_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.testFile").value(Base64Utils.encodeToString(DEFAULT_TEST_FILE)))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME));
    }


    @Test
    @Transactional
    public void getTestEntitiesByIdFiltering() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        Long id = testEntity.getId();

        defaultTestEntityShouldBeFound("id.equals=" + id);
        defaultTestEntityShouldNotBeFound("id.notEquals=" + id);

        defaultTestEntityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTestEntityShouldNotBeFound("id.greaterThan=" + id);

        defaultTestEntityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTestEntityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTestEntitiesByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList where fileName equals to DEFAULT_FILE_NAME
        defaultTestEntityShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the testEntityList where fileName equals to UPDATED_FILE_NAME
        defaultTestEntityShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllTestEntitiesByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList where fileName not equals to DEFAULT_FILE_NAME
        defaultTestEntityShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the testEntityList where fileName not equals to UPDATED_FILE_NAME
        defaultTestEntityShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllTestEntitiesByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultTestEntityShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the testEntityList where fileName equals to UPDATED_FILE_NAME
        defaultTestEntityShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllTestEntitiesByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList where fileName is not null
        defaultTestEntityShouldBeFound("fileName.specified=true");

        // Get all the testEntityList where fileName is null
        defaultTestEntityShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTestEntitiesByFileNameContainsSomething() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList where fileName contains DEFAULT_FILE_NAME
        defaultTestEntityShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the testEntityList where fileName contains UPDATED_FILE_NAME
        defaultTestEntityShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllTestEntitiesByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        testEntityRepository.saveAndFlush(testEntity);

        // Get all the testEntityList where fileName does not contain DEFAULT_FILE_NAME
        defaultTestEntityShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the testEntityList where fileName does not contain UPDATED_FILE_NAME
        defaultTestEntityShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTestEntityShouldBeFound(String filter) throws Exception {
        restTestEntityMockMvc.perform(get("/api/test-entities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].testFileContentType").value(hasItem(DEFAULT_TEST_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].testFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEST_FILE))))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)));

        // Check, that the count call also returns 1
        restTestEntityMockMvc.perform(get("/api/test-entities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTestEntityShouldNotBeFound(String filter) throws Exception {
        restTestEntityMockMvc.perform(get("/api/test-entities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTestEntityMockMvc.perform(get("/api/test-entities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTestEntity() throws Exception {
        // Get the testEntity
        restTestEntityMockMvc.perform(get("/api/test-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestEntity() throws Exception {
        // Initialize the database
        testEntityService.save(testEntity);

        int databaseSizeBeforeUpdate = testEntityRepository.findAll().size();

        // Update the testEntity
        TestEntity updatedTestEntity = testEntityRepository.findById(testEntity.getId()).get();
        // Disconnect from session so that the updates on updatedTestEntity are not directly saved in db
        em.detach(updatedTestEntity);
        updatedTestEntity
            .testFile(UPDATED_TEST_FILE)
            .testFileContentType(UPDATED_TEST_FILE_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME);

        restTestEntityMockMvc.perform(put("/api/test-entities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestEntity)))
            .andExpect(status().isOk());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeUpdate);
        TestEntity testTestEntity = testEntityList.get(testEntityList.size() - 1);
        assertThat(testTestEntity.getTestFile()).isEqualTo(UPDATED_TEST_FILE);
        assertThat(testTestEntity.getTestFileContentType()).isEqualTo(UPDATED_TEST_FILE_CONTENT_TYPE);
        assertThat(testTestEntity.getFileName()).isEqualTo(UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTestEntity() throws Exception {
        int databaseSizeBeforeUpdate = testEntityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestEntityMockMvc.perform(put("/api/test-entities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testEntity)))
            .andExpect(status().isBadRequest());

        // Validate the TestEntity in the database
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTestEntity() throws Exception {
        // Initialize the database
        testEntityService.save(testEntity);

        int databaseSizeBeforeDelete = testEntityRepository.findAll().size();

        // Delete the testEntity
        restTestEntityMockMvc.perform(delete("/api/test-entities/{id}", testEntity.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestEntity> testEntityList = testEntityRepository.findAll();
        assertThat(testEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
