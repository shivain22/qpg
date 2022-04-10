package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QbMaster;
import com.qpg.repository.QbMasterRepository;
import com.qpg.service.QbMasterService;
import com.qpg.service.dto.QbMasterCriteria;
import com.qpg.service.QbMasterQueryService;

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
 * Integration tests for the {@link QbMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QbMasterResourceIT {

    private static final byte[] DEFAULT_QB_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_QB_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_QB_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_QB_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COLLEGE_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_COLLEGE_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_CATEGORY_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_SUBJECT_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_SUB_SUBJECT_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_MASTER = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TOPIC_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TOPIC_MASTER = "BBBBBBBBBB";

    @Autowired
    private QbMasterRepository qbMasterRepository;

    @Autowired
    private QbMasterService qbMasterService;

    @Autowired
    private QbMasterQueryService qbMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQbMasterMockMvc;

    private QbMaster qbMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QbMaster createEntity(EntityManager em) {
        QbMaster qbMaster = new QbMaster()
            .qbFile(DEFAULT_QB_FILE)
            .qbFileContentType(DEFAULT_QB_FILE_CONTENT_TYPE)
           ;
        return qbMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QbMaster createUpdatedEntity(EntityManager em) {
        QbMaster qbMaster = new QbMaster()
            .qbFile(UPDATED_QB_FILE)
            .qbFileContentType(UPDATED_QB_FILE_CONTENT_TYPE)
            ;
        return qbMaster;
    }

    @BeforeEach
    public void initTest() {
        qbMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQbMaster() throws Exception {
        int databaseSizeBeforeCreate = qbMasterRepository.findAll().size();
        // Create the QbMaster
        restQbMasterMockMvc.perform(post("/api/qb-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qbMaster)))
            .andExpect(status().isCreated());

        // Validate the QbMaster in the database
        List<QbMaster> qbMasterList = qbMasterRepository.findAll();
        assertThat(qbMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QbMaster testQbMaster = qbMasterList.get(qbMasterList.size() - 1);
        assertThat(testQbMaster.getQbFile()).isEqualTo(DEFAULT_QB_FILE);
        assertThat(testQbMaster.getQbFileContentType()).isEqualTo(DEFAULT_QB_FILE_CONTENT_TYPE);
        assertThat(testQbMaster.getCollegeMaster()).isEqualTo(DEFAULT_COLLEGE_MASTER);
        assertThat(testQbMaster.getDepartmentMaster()).isEqualTo(DEFAULT_DEPARTMENT_MASTER);
        assertThat(testQbMaster.getCourseMaster()).isEqualTo(DEFAULT_COURSE_MASTER);
        assertThat(testQbMaster.getCategoryMaster()).isEqualTo(DEFAULT_CATEGORY_MASTER);
        assertThat(testQbMaster.getSubCategoryMaster()).isEqualTo(DEFAULT_SUB_CATEGORY_MASTER);
        assertThat(testQbMaster.getSubjectMaster()).isEqualTo(DEFAULT_SUBJECT_MASTER);
        assertThat(testQbMaster.getSubSubjectMaster()).isEqualTo(DEFAULT_SUB_SUBJECT_MASTER);
        assertThat(testQbMaster.getTopicMaster()).isEqualTo(DEFAULT_TOPIC_MASTER);
        assertThat(testQbMaster.getSubTopicMaster()).isEqualTo(DEFAULT_SUB_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void createQbMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qbMasterRepository.findAll().size();

        // Create the QbMaster with an existing ID
        qbMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQbMasterMockMvc.perform(post("/api/qb-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qbMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QbMaster in the database
        List<QbMaster> qbMasterList = qbMasterRepository.findAll();
        assertThat(qbMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQbMasters() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList
        restQbMasterMockMvc.perform(get("/api/qb-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qbMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].qbFileContentType").value(hasItem(DEFAULT_QB_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].qbFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_QB_FILE))))
            .andExpect(jsonPath("$.[*].collegeMaster").value(hasItem(DEFAULT_COLLEGE_MASTER)))
            .andExpect(jsonPath("$.[*].departmentMaster").value(hasItem(DEFAULT_DEPARTMENT_MASTER)))
            .andExpect(jsonPath("$.[*].courseMaster").value(hasItem(DEFAULT_COURSE_MASTER)))
            .andExpect(jsonPath("$.[*].categoryMaster").value(hasItem(DEFAULT_CATEGORY_MASTER)))
            .andExpect(jsonPath("$.[*].subCategoryMaster").value(hasItem(DEFAULT_SUB_CATEGORY_MASTER)))
            .andExpect(jsonPath("$.[*].subjectMaster").value(hasItem(DEFAULT_SUBJECT_MASTER)))
            .andExpect(jsonPath("$.[*].subSubjectMaster").value(hasItem(DEFAULT_SUB_SUBJECT_MASTER)))
            .andExpect(jsonPath("$.[*].topicMaster").value(hasItem(DEFAULT_TOPIC_MASTER)))
            .andExpect(jsonPath("$.[*].subTopicMaster").value(hasItem(DEFAULT_SUB_TOPIC_MASTER)));
    }

    @Test
    @Transactional
    public void getQbMaster() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get the qbMaster
        restQbMasterMockMvc.perform(get("/api/qb-masters/{id}", qbMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qbMaster.getId().intValue()))
            .andExpect(jsonPath("$.qbFileContentType").value(DEFAULT_QB_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.qbFile").value(Base64Utils.encodeToString(DEFAULT_QB_FILE)))
            .andExpect(jsonPath("$.collegeMaster").value(DEFAULT_COLLEGE_MASTER))
            .andExpect(jsonPath("$.departmentMaster").value(DEFAULT_DEPARTMENT_MASTER))
            .andExpect(jsonPath("$.courseMaster").value(DEFAULT_COURSE_MASTER))
            .andExpect(jsonPath("$.categoryMaster").value(DEFAULT_CATEGORY_MASTER))
            .andExpect(jsonPath("$.subCategoryMaster").value(DEFAULT_SUB_CATEGORY_MASTER))
            .andExpect(jsonPath("$.subjectMaster").value(DEFAULT_SUBJECT_MASTER))
            .andExpect(jsonPath("$.subSubjectMaster").value(DEFAULT_SUB_SUBJECT_MASTER))
            .andExpect(jsonPath("$.topicMaster").value(DEFAULT_TOPIC_MASTER))
            .andExpect(jsonPath("$.subTopicMaster").value(DEFAULT_SUB_TOPIC_MASTER));
    }


    @Test
    @Transactional
    public void getQbMastersByIdFiltering() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        Long id = qbMaster.getId();

        defaultQbMasterShouldBeFound("id.equals=" + id);
        defaultQbMasterShouldNotBeFound("id.notEquals=" + id);

        defaultQbMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQbMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultQbMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQbMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQbMastersByCollegeMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where collegeMaster equals to DEFAULT_COLLEGE_MASTER
        defaultQbMasterShouldBeFound("collegeMaster.equals=" + DEFAULT_COLLEGE_MASTER);

        // Get all the qbMasterList where collegeMaster equals to UPDATED_COLLEGE_MASTER
        defaultQbMasterShouldNotBeFound("collegeMaster.equals=" + UPDATED_COLLEGE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCollegeMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where collegeMaster not equals to DEFAULT_COLLEGE_MASTER
        defaultQbMasterShouldNotBeFound("collegeMaster.notEquals=" + DEFAULT_COLLEGE_MASTER);

        // Get all the qbMasterList where collegeMaster not equals to UPDATED_COLLEGE_MASTER
        defaultQbMasterShouldBeFound("collegeMaster.notEquals=" + UPDATED_COLLEGE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCollegeMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where collegeMaster in DEFAULT_COLLEGE_MASTER or UPDATED_COLLEGE_MASTER
        defaultQbMasterShouldBeFound("collegeMaster.in=" + DEFAULT_COLLEGE_MASTER + "," + UPDATED_COLLEGE_MASTER);

        // Get all the qbMasterList where collegeMaster equals to UPDATED_COLLEGE_MASTER
        defaultQbMasterShouldNotBeFound("collegeMaster.in=" + UPDATED_COLLEGE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCollegeMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where collegeMaster is not null
        defaultQbMasterShouldBeFound("collegeMaster.specified=true");

        // Get all the qbMasterList where collegeMaster is null
        defaultQbMasterShouldNotBeFound("collegeMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersByCollegeMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where collegeMaster contains DEFAULT_COLLEGE_MASTER
        defaultQbMasterShouldBeFound("collegeMaster.contains=" + DEFAULT_COLLEGE_MASTER);

        // Get all the qbMasterList where collegeMaster contains UPDATED_COLLEGE_MASTER
        defaultQbMasterShouldNotBeFound("collegeMaster.contains=" + UPDATED_COLLEGE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCollegeMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where collegeMaster does not contain DEFAULT_COLLEGE_MASTER
        defaultQbMasterShouldNotBeFound("collegeMaster.doesNotContain=" + DEFAULT_COLLEGE_MASTER);

        // Get all the qbMasterList where collegeMaster does not contain UPDATED_COLLEGE_MASTER
        defaultQbMasterShouldBeFound("collegeMaster.doesNotContain=" + UPDATED_COLLEGE_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersByDepartmentMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where departmentMaster equals to DEFAULT_DEPARTMENT_MASTER
        defaultQbMasterShouldBeFound("departmentMaster.equals=" + DEFAULT_DEPARTMENT_MASTER);

        // Get all the qbMasterList where departmentMaster equals to UPDATED_DEPARTMENT_MASTER
        defaultQbMasterShouldNotBeFound("departmentMaster.equals=" + UPDATED_DEPARTMENT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByDepartmentMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where departmentMaster not equals to DEFAULT_DEPARTMENT_MASTER
        defaultQbMasterShouldNotBeFound("departmentMaster.notEquals=" + DEFAULT_DEPARTMENT_MASTER);

        // Get all the qbMasterList where departmentMaster not equals to UPDATED_DEPARTMENT_MASTER
        defaultQbMasterShouldBeFound("departmentMaster.notEquals=" + UPDATED_DEPARTMENT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByDepartmentMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where departmentMaster in DEFAULT_DEPARTMENT_MASTER or UPDATED_DEPARTMENT_MASTER
        defaultQbMasterShouldBeFound("departmentMaster.in=" + DEFAULT_DEPARTMENT_MASTER + "," + UPDATED_DEPARTMENT_MASTER);

        // Get all the qbMasterList where departmentMaster equals to UPDATED_DEPARTMENT_MASTER
        defaultQbMasterShouldNotBeFound("departmentMaster.in=" + UPDATED_DEPARTMENT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByDepartmentMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where departmentMaster is not null
        defaultQbMasterShouldBeFound("departmentMaster.specified=true");

        // Get all the qbMasterList where departmentMaster is null
        defaultQbMasterShouldNotBeFound("departmentMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersByDepartmentMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where departmentMaster contains DEFAULT_DEPARTMENT_MASTER
        defaultQbMasterShouldBeFound("departmentMaster.contains=" + DEFAULT_DEPARTMENT_MASTER);

        // Get all the qbMasterList where departmentMaster contains UPDATED_DEPARTMENT_MASTER
        defaultQbMasterShouldNotBeFound("departmentMaster.contains=" + UPDATED_DEPARTMENT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByDepartmentMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where departmentMaster does not contain DEFAULT_DEPARTMENT_MASTER
        defaultQbMasterShouldNotBeFound("departmentMaster.doesNotContain=" + DEFAULT_DEPARTMENT_MASTER);

        // Get all the qbMasterList where departmentMaster does not contain UPDATED_DEPARTMENT_MASTER
        defaultQbMasterShouldBeFound("departmentMaster.doesNotContain=" + UPDATED_DEPARTMENT_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersByCourseMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where courseMaster equals to DEFAULT_COURSE_MASTER
        defaultQbMasterShouldBeFound("courseMaster.equals=" + DEFAULT_COURSE_MASTER);

        // Get all the qbMasterList where courseMaster equals to UPDATED_COURSE_MASTER
        defaultQbMasterShouldNotBeFound("courseMaster.equals=" + UPDATED_COURSE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCourseMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where courseMaster not equals to DEFAULT_COURSE_MASTER
        defaultQbMasterShouldNotBeFound("courseMaster.notEquals=" + DEFAULT_COURSE_MASTER);

        // Get all the qbMasterList where courseMaster not equals to UPDATED_COURSE_MASTER
        defaultQbMasterShouldBeFound("courseMaster.notEquals=" + UPDATED_COURSE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCourseMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where courseMaster in DEFAULT_COURSE_MASTER or UPDATED_COURSE_MASTER
        defaultQbMasterShouldBeFound("courseMaster.in=" + DEFAULT_COURSE_MASTER + "," + UPDATED_COURSE_MASTER);

        // Get all the qbMasterList where courseMaster equals to UPDATED_COURSE_MASTER
        defaultQbMasterShouldNotBeFound("courseMaster.in=" + UPDATED_COURSE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCourseMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where courseMaster is not null
        defaultQbMasterShouldBeFound("courseMaster.specified=true");

        // Get all the qbMasterList where courseMaster is null
        defaultQbMasterShouldNotBeFound("courseMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersByCourseMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where courseMaster contains DEFAULT_COURSE_MASTER
        defaultQbMasterShouldBeFound("courseMaster.contains=" + DEFAULT_COURSE_MASTER);

        // Get all the qbMasterList where courseMaster contains UPDATED_COURSE_MASTER
        defaultQbMasterShouldNotBeFound("courseMaster.contains=" + UPDATED_COURSE_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCourseMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where courseMaster does not contain DEFAULT_COURSE_MASTER
        defaultQbMasterShouldNotBeFound("courseMaster.doesNotContain=" + DEFAULT_COURSE_MASTER);

        // Get all the qbMasterList where courseMaster does not contain UPDATED_COURSE_MASTER
        defaultQbMasterShouldBeFound("courseMaster.doesNotContain=" + UPDATED_COURSE_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersByCategoryMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where categoryMaster equals to DEFAULT_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("categoryMaster.equals=" + DEFAULT_CATEGORY_MASTER);

        // Get all the qbMasterList where categoryMaster equals to UPDATED_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("categoryMaster.equals=" + UPDATED_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCategoryMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where categoryMaster not equals to DEFAULT_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("categoryMaster.notEquals=" + DEFAULT_CATEGORY_MASTER);

        // Get all the qbMasterList where categoryMaster not equals to UPDATED_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("categoryMaster.notEquals=" + UPDATED_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCategoryMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where categoryMaster in DEFAULT_CATEGORY_MASTER or UPDATED_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("categoryMaster.in=" + DEFAULT_CATEGORY_MASTER + "," + UPDATED_CATEGORY_MASTER);

        // Get all the qbMasterList where categoryMaster equals to UPDATED_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("categoryMaster.in=" + UPDATED_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCategoryMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where categoryMaster is not null
        defaultQbMasterShouldBeFound("categoryMaster.specified=true");

        // Get all the qbMasterList where categoryMaster is null
        defaultQbMasterShouldNotBeFound("categoryMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersByCategoryMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where categoryMaster contains DEFAULT_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("categoryMaster.contains=" + DEFAULT_CATEGORY_MASTER);

        // Get all the qbMasterList where categoryMaster contains UPDATED_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("categoryMaster.contains=" + UPDATED_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByCategoryMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where categoryMaster does not contain DEFAULT_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("categoryMaster.doesNotContain=" + DEFAULT_CATEGORY_MASTER);

        // Get all the qbMasterList where categoryMaster does not contain UPDATED_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("categoryMaster.doesNotContain=" + UPDATED_CATEGORY_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersBySubCategoryMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subCategoryMaster equals to DEFAULT_SUB_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("subCategoryMaster.equals=" + DEFAULT_SUB_CATEGORY_MASTER);

        // Get all the qbMasterList where subCategoryMaster equals to UPDATED_SUB_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("subCategoryMaster.equals=" + UPDATED_SUB_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubCategoryMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subCategoryMaster not equals to DEFAULT_SUB_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("subCategoryMaster.notEquals=" + DEFAULT_SUB_CATEGORY_MASTER);

        // Get all the qbMasterList where subCategoryMaster not equals to UPDATED_SUB_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("subCategoryMaster.notEquals=" + UPDATED_SUB_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubCategoryMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subCategoryMaster in DEFAULT_SUB_CATEGORY_MASTER or UPDATED_SUB_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("subCategoryMaster.in=" + DEFAULT_SUB_CATEGORY_MASTER + "," + UPDATED_SUB_CATEGORY_MASTER);

        // Get all the qbMasterList where subCategoryMaster equals to UPDATED_SUB_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("subCategoryMaster.in=" + UPDATED_SUB_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubCategoryMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subCategoryMaster is not null
        defaultQbMasterShouldBeFound("subCategoryMaster.specified=true");

        // Get all the qbMasterList where subCategoryMaster is null
        defaultQbMasterShouldNotBeFound("subCategoryMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersBySubCategoryMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subCategoryMaster contains DEFAULT_SUB_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("subCategoryMaster.contains=" + DEFAULT_SUB_CATEGORY_MASTER);

        // Get all the qbMasterList where subCategoryMaster contains UPDATED_SUB_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("subCategoryMaster.contains=" + UPDATED_SUB_CATEGORY_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubCategoryMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subCategoryMaster does not contain DEFAULT_SUB_CATEGORY_MASTER
        defaultQbMasterShouldNotBeFound("subCategoryMaster.doesNotContain=" + DEFAULT_SUB_CATEGORY_MASTER);

        // Get all the qbMasterList where subCategoryMaster does not contain UPDATED_SUB_CATEGORY_MASTER
        defaultQbMasterShouldBeFound("subCategoryMaster.doesNotContain=" + UPDATED_SUB_CATEGORY_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersBySubjectMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subjectMaster equals to DEFAULT_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subjectMaster.equals=" + DEFAULT_SUBJECT_MASTER);

        // Get all the qbMasterList where subjectMaster equals to UPDATED_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subjectMaster.equals=" + UPDATED_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubjectMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subjectMaster not equals to DEFAULT_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subjectMaster.notEquals=" + DEFAULT_SUBJECT_MASTER);

        // Get all the qbMasterList where subjectMaster not equals to UPDATED_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subjectMaster.notEquals=" + UPDATED_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubjectMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subjectMaster in DEFAULT_SUBJECT_MASTER or UPDATED_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subjectMaster.in=" + DEFAULT_SUBJECT_MASTER + "," + UPDATED_SUBJECT_MASTER);

        // Get all the qbMasterList where subjectMaster equals to UPDATED_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subjectMaster.in=" + UPDATED_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubjectMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subjectMaster is not null
        defaultQbMasterShouldBeFound("subjectMaster.specified=true");

        // Get all the qbMasterList where subjectMaster is null
        defaultQbMasterShouldNotBeFound("subjectMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersBySubjectMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subjectMaster contains DEFAULT_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subjectMaster.contains=" + DEFAULT_SUBJECT_MASTER);

        // Get all the qbMasterList where subjectMaster contains UPDATED_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subjectMaster.contains=" + UPDATED_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubjectMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subjectMaster does not contain DEFAULT_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subjectMaster.doesNotContain=" + DEFAULT_SUBJECT_MASTER);

        // Get all the qbMasterList where subjectMaster does not contain UPDATED_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subjectMaster.doesNotContain=" + UPDATED_SUBJECT_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersBySubSubjectMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subSubjectMaster equals to DEFAULT_SUB_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subSubjectMaster.equals=" + DEFAULT_SUB_SUBJECT_MASTER);

        // Get all the qbMasterList where subSubjectMaster equals to UPDATED_SUB_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subSubjectMaster.equals=" + UPDATED_SUB_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubSubjectMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subSubjectMaster not equals to DEFAULT_SUB_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subSubjectMaster.notEquals=" + DEFAULT_SUB_SUBJECT_MASTER);

        // Get all the qbMasterList where subSubjectMaster not equals to UPDATED_SUB_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subSubjectMaster.notEquals=" + UPDATED_SUB_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubSubjectMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subSubjectMaster in DEFAULT_SUB_SUBJECT_MASTER or UPDATED_SUB_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subSubjectMaster.in=" + DEFAULT_SUB_SUBJECT_MASTER + "," + UPDATED_SUB_SUBJECT_MASTER);

        // Get all the qbMasterList where subSubjectMaster equals to UPDATED_SUB_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subSubjectMaster.in=" + UPDATED_SUB_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubSubjectMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subSubjectMaster is not null
        defaultQbMasterShouldBeFound("subSubjectMaster.specified=true");

        // Get all the qbMasterList where subSubjectMaster is null
        defaultQbMasterShouldNotBeFound("subSubjectMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersBySubSubjectMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subSubjectMaster contains DEFAULT_SUB_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subSubjectMaster.contains=" + DEFAULT_SUB_SUBJECT_MASTER);

        // Get all the qbMasterList where subSubjectMaster contains UPDATED_SUB_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subSubjectMaster.contains=" + UPDATED_SUB_SUBJECT_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubSubjectMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subSubjectMaster does not contain DEFAULT_SUB_SUBJECT_MASTER
        defaultQbMasterShouldNotBeFound("subSubjectMaster.doesNotContain=" + DEFAULT_SUB_SUBJECT_MASTER);

        // Get all the qbMasterList where subSubjectMaster does not contain UPDATED_SUB_SUBJECT_MASTER
        defaultQbMasterShouldBeFound("subSubjectMaster.doesNotContain=" + UPDATED_SUB_SUBJECT_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersByTopicMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where topicMaster equals to DEFAULT_TOPIC_MASTER
        defaultQbMasterShouldBeFound("topicMaster.equals=" + DEFAULT_TOPIC_MASTER);

        // Get all the qbMasterList where topicMaster equals to UPDATED_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("topicMaster.equals=" + UPDATED_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByTopicMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where topicMaster not equals to DEFAULT_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("topicMaster.notEquals=" + DEFAULT_TOPIC_MASTER);

        // Get all the qbMasterList where topicMaster not equals to UPDATED_TOPIC_MASTER
        defaultQbMasterShouldBeFound("topicMaster.notEquals=" + UPDATED_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByTopicMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where topicMaster in DEFAULT_TOPIC_MASTER or UPDATED_TOPIC_MASTER
        defaultQbMasterShouldBeFound("topicMaster.in=" + DEFAULT_TOPIC_MASTER + "," + UPDATED_TOPIC_MASTER);

        // Get all the qbMasterList where topicMaster equals to UPDATED_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("topicMaster.in=" + UPDATED_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByTopicMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where topicMaster is not null
        defaultQbMasterShouldBeFound("topicMaster.specified=true");

        // Get all the qbMasterList where topicMaster is null
        defaultQbMasterShouldNotBeFound("topicMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersByTopicMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where topicMaster contains DEFAULT_TOPIC_MASTER
        defaultQbMasterShouldBeFound("topicMaster.contains=" + DEFAULT_TOPIC_MASTER);

        // Get all the qbMasterList where topicMaster contains UPDATED_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("topicMaster.contains=" + UPDATED_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersByTopicMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where topicMaster does not contain DEFAULT_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("topicMaster.doesNotContain=" + DEFAULT_TOPIC_MASTER);

        // Get all the qbMasterList where topicMaster does not contain UPDATED_TOPIC_MASTER
        defaultQbMasterShouldBeFound("topicMaster.doesNotContain=" + UPDATED_TOPIC_MASTER);
    }


    @Test
    @Transactional
    public void getAllQbMastersBySubTopicMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subTopicMaster equals to DEFAULT_SUB_TOPIC_MASTER
        defaultQbMasterShouldBeFound("subTopicMaster.equals=" + DEFAULT_SUB_TOPIC_MASTER);

        // Get all the qbMasterList where subTopicMaster equals to UPDATED_SUB_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("subTopicMaster.equals=" + UPDATED_SUB_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubTopicMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subTopicMaster not equals to DEFAULT_SUB_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("subTopicMaster.notEquals=" + DEFAULT_SUB_TOPIC_MASTER);

        // Get all the qbMasterList where subTopicMaster not equals to UPDATED_SUB_TOPIC_MASTER
        defaultQbMasterShouldBeFound("subTopicMaster.notEquals=" + UPDATED_SUB_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubTopicMasterIsInShouldWork() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subTopicMaster in DEFAULT_SUB_TOPIC_MASTER or UPDATED_SUB_TOPIC_MASTER
        defaultQbMasterShouldBeFound("subTopicMaster.in=" + DEFAULT_SUB_TOPIC_MASTER + "," + UPDATED_SUB_TOPIC_MASTER);

        // Get all the qbMasterList where subTopicMaster equals to UPDATED_SUB_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("subTopicMaster.in=" + UPDATED_SUB_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubTopicMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subTopicMaster is not null
        defaultQbMasterShouldBeFound("subTopicMaster.specified=true");

        // Get all the qbMasterList where subTopicMaster is null
        defaultQbMasterShouldNotBeFound("subTopicMaster.specified=false");
    }
                @Test
    @Transactional
    public void getAllQbMastersBySubTopicMasterContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subTopicMaster contains DEFAULT_SUB_TOPIC_MASTER
        defaultQbMasterShouldBeFound("subTopicMaster.contains=" + DEFAULT_SUB_TOPIC_MASTER);

        // Get all the qbMasterList where subTopicMaster contains UPDATED_SUB_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("subTopicMaster.contains=" + UPDATED_SUB_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void getAllQbMastersBySubTopicMasterNotContainsSomething() throws Exception {
        // Initialize the database
        qbMasterRepository.saveAndFlush(qbMaster);

        // Get all the qbMasterList where subTopicMaster does not contain DEFAULT_SUB_TOPIC_MASTER
        defaultQbMasterShouldNotBeFound("subTopicMaster.doesNotContain=" + DEFAULT_SUB_TOPIC_MASTER);

        // Get all the qbMasterList where subTopicMaster does not contain UPDATED_SUB_TOPIC_MASTER
        defaultQbMasterShouldBeFound("subTopicMaster.doesNotContain=" + UPDATED_SUB_TOPIC_MASTER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQbMasterShouldBeFound(String filter) throws Exception {
        restQbMasterMockMvc.perform(get("/api/qb-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qbMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].qbFileContentType").value(hasItem(DEFAULT_QB_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].qbFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_QB_FILE))))
            .andExpect(jsonPath("$.[*].collegeMaster").value(hasItem(DEFAULT_COLLEGE_MASTER)))
            .andExpect(jsonPath("$.[*].departmentMaster").value(hasItem(DEFAULT_DEPARTMENT_MASTER)))
            .andExpect(jsonPath("$.[*].courseMaster").value(hasItem(DEFAULT_COURSE_MASTER)))
            .andExpect(jsonPath("$.[*].categoryMaster").value(hasItem(DEFAULT_CATEGORY_MASTER)))
            .andExpect(jsonPath("$.[*].subCategoryMaster").value(hasItem(DEFAULT_SUB_CATEGORY_MASTER)))
            .andExpect(jsonPath("$.[*].subjectMaster").value(hasItem(DEFAULT_SUBJECT_MASTER)))
            .andExpect(jsonPath("$.[*].subSubjectMaster").value(hasItem(DEFAULT_SUB_SUBJECT_MASTER)))
            .andExpect(jsonPath("$.[*].topicMaster").value(hasItem(DEFAULT_TOPIC_MASTER)))
            .andExpect(jsonPath("$.[*].subTopicMaster").value(hasItem(DEFAULT_SUB_TOPIC_MASTER)));

        // Check, that the count call also returns 1
        restQbMasterMockMvc.perform(get("/api/qb-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQbMasterShouldNotBeFound(String filter) throws Exception {
        restQbMasterMockMvc.perform(get("/api/qb-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQbMasterMockMvc.perform(get("/api/qb-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQbMaster() throws Exception {
        // Get the qbMaster
        restQbMasterMockMvc.perform(get("/api/qb-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQbMaster() throws Exception {
        // Initialize the database
        qbMasterService.save(qbMaster);

        int databaseSizeBeforeUpdate = qbMasterRepository.findAll().size();

        // Update the qbMaster
        QbMaster updatedQbMaster = qbMasterRepository.findById(qbMaster.getId()).get();
        // Disconnect from session so that the updates on updatedQbMaster are not directly saved in db
        em.detach(updatedQbMaster);
        updatedQbMaster
            .qbFile(UPDATED_QB_FILE)
            .qbFileContentType(UPDATED_QB_FILE_CONTENT_TYPE)
            ;

        restQbMasterMockMvc.perform(put("/api/qb-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQbMaster)))
            .andExpect(status().isOk());

        // Validate the QbMaster in the database
        List<QbMaster> qbMasterList = qbMasterRepository.findAll();
        assertThat(qbMasterList).hasSize(databaseSizeBeforeUpdate);
        QbMaster testQbMaster = qbMasterList.get(qbMasterList.size() - 1);
        assertThat(testQbMaster.getQbFile()).isEqualTo(UPDATED_QB_FILE);
        assertThat(testQbMaster.getQbFileContentType()).isEqualTo(UPDATED_QB_FILE_CONTENT_TYPE);
        assertThat(testQbMaster.getCollegeMaster()).isEqualTo(UPDATED_COLLEGE_MASTER);
        assertThat(testQbMaster.getDepartmentMaster()).isEqualTo(UPDATED_DEPARTMENT_MASTER);
        assertThat(testQbMaster.getCourseMaster()).isEqualTo(UPDATED_COURSE_MASTER);
        assertThat(testQbMaster.getCategoryMaster()).isEqualTo(UPDATED_CATEGORY_MASTER);
        assertThat(testQbMaster.getSubCategoryMaster()).isEqualTo(UPDATED_SUB_CATEGORY_MASTER);
        assertThat(testQbMaster.getSubjectMaster()).isEqualTo(UPDATED_SUBJECT_MASTER);
        assertThat(testQbMaster.getSubSubjectMaster()).isEqualTo(UPDATED_SUB_SUBJECT_MASTER);
        assertThat(testQbMaster.getTopicMaster()).isEqualTo(UPDATED_TOPIC_MASTER);
        assertThat(testQbMaster.getSubTopicMaster()).isEqualTo(UPDATED_SUB_TOPIC_MASTER);
    }

    @Test
    @Transactional
    public void updateNonExistingQbMaster() throws Exception {
        int databaseSizeBeforeUpdate = qbMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQbMasterMockMvc.perform(put("/api/qb-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qbMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QbMaster in the database
        List<QbMaster> qbMasterList = qbMasterRepository.findAll();
        assertThat(qbMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQbMaster() throws Exception {
        // Initialize the database
        qbMasterService.save(qbMaster);

        int databaseSizeBeforeDelete = qbMasterRepository.findAll().size();

        // Delete the qbMaster
        restQbMasterMockMvc.perform(delete("/api/qb-masters/{id}", qbMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QbMaster> qbMasterList = qbMasterRepository.findAll();
        assertThat(qbMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
