package com.qpg.web.rest;

import com.qpg.QpgApp;
import com.qpg.domain.QpBankUploadMaster;
import com.qpg.domain.SubTopicMaster;
import com.qpg.repository.QpBankUploadMasterRepository;
import com.qpg.service.QpBankUploadMasterService;
import com.qpg.service.dto.QpBankUploadMasterCriteria;
import com.qpg.service.QpBankUploadMasterQueryService;

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
 * Integration tests for the {@link QpBankUploadMasterResource} REST controller.
 */
@SpringBootTest(classes = QpgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QpBankUploadMasterResourceIT {

    private static final byte[] DEFAULT_QP_BANK_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_QP_BANK_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_QP_BANK_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_QP_BANK_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private QpBankUploadMasterRepository qpBankUploadMasterRepository;

    @Autowired
    private QpBankUploadMasterService qpBankUploadMasterService;

    @Autowired
    private QpBankUploadMasterQueryService qpBankUploadMasterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQpBankUploadMasterMockMvc;

    private QpBankUploadMaster qpBankUploadMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QpBankUploadMaster createEntity(EntityManager em) {
        QpBankUploadMaster qpBankUploadMaster = new QpBankUploadMaster()
            .qpBankFile(DEFAULT_QP_BANK_FILE)
            .qpBankFileContentType(DEFAULT_QP_BANK_FILE_CONTENT_TYPE)
            .name(DEFAULT_NAME);
        // Add required entity
        SubTopicMaster subTopicMaster;
        if (TestUtil.findAll(em, SubTopicMaster.class).isEmpty()) {
            subTopicMaster = SubTopicMasterResourceIT.createEntity(em);
            em.persist(subTopicMaster);
            em.flush();
        } else {
            subTopicMaster = TestUtil.findAll(em, SubTopicMaster.class).get(0);
        }
        qpBankUploadMaster.setSubTopicMaster(subTopicMaster);
        return qpBankUploadMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QpBankUploadMaster createUpdatedEntity(EntityManager em) {
        QpBankUploadMaster qpBankUploadMaster = new QpBankUploadMaster()
            .qpBankFile(UPDATED_QP_BANK_FILE)
            .qpBankFileContentType(UPDATED_QP_BANK_FILE_CONTENT_TYPE)
            .name(UPDATED_NAME);
        // Add required entity
        SubTopicMaster subTopicMaster;
        if (TestUtil.findAll(em, SubTopicMaster.class).isEmpty()) {
            subTopicMaster = SubTopicMasterResourceIT.createUpdatedEntity(em);
            em.persist(subTopicMaster);
            em.flush();
        } else {
            subTopicMaster = TestUtil.findAll(em, SubTopicMaster.class).get(0);
        }
        qpBankUploadMaster.setSubTopicMaster(subTopicMaster);
        return qpBankUploadMaster;
    }

    @BeforeEach
    public void initTest() {
        qpBankUploadMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQpBankUploadMaster() throws Exception {
        int databaseSizeBeforeCreate = qpBankUploadMasterRepository.findAll().size();
        // Create the QpBankUploadMaster
        restQpBankUploadMasterMockMvc.perform(post("/api/qp-bank-upload-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qpBankUploadMaster)))
            .andExpect(status().isCreated());

        // Validate the QpBankUploadMaster in the database
        List<QpBankUploadMaster> qpBankUploadMasterList = qpBankUploadMasterRepository.findAll();
        assertThat(qpBankUploadMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QpBankUploadMaster testQpBankUploadMaster = qpBankUploadMasterList.get(qpBankUploadMasterList.size() - 1);
        assertThat(testQpBankUploadMaster.getQpBankFile()).isEqualTo(DEFAULT_QP_BANK_FILE);
        assertThat(testQpBankUploadMaster.getQpBankFileContentType()).isEqualTo(DEFAULT_QP_BANK_FILE_CONTENT_TYPE);
        assertThat(testQpBankUploadMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createQpBankUploadMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qpBankUploadMasterRepository.findAll().size();

        // Create the QpBankUploadMaster with an existing ID
        qpBankUploadMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQpBankUploadMasterMockMvc.perform(post("/api/qp-bank-upload-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qpBankUploadMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QpBankUploadMaster in the database
        List<QpBankUploadMaster> qpBankUploadMasterList = qpBankUploadMasterRepository.findAll();
        assertThat(qpBankUploadMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = qpBankUploadMasterRepository.findAll().size();
        // set the field null
        qpBankUploadMaster.setName(null);

        // Create the QpBankUploadMaster, which fails.


        restQpBankUploadMasterMockMvc.perform(post("/api/qp-bank-upload-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qpBankUploadMaster)))
            .andExpect(status().isBadRequest());

        List<QpBankUploadMaster> qpBankUploadMasterList = qpBankUploadMasterRepository.findAll();
        assertThat(qpBankUploadMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQpBankUploadMasters() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qpBankUploadMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].qpBankFileContentType").value(hasItem(DEFAULT_QP_BANK_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].qpBankFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_QP_BANK_FILE))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getQpBankUploadMaster() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get the qpBankUploadMaster
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters/{id}", qpBankUploadMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qpBankUploadMaster.getId().intValue()))
            .andExpect(jsonPath("$.qpBankFileContentType").value(DEFAULT_QP_BANK_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.qpBankFile").value(Base64Utils.encodeToString(DEFAULT_QP_BANK_FILE)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getQpBankUploadMastersByIdFiltering() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        Long id = qpBankUploadMaster.getId();

        defaultQpBankUploadMasterShouldBeFound("id.equals=" + id);
        defaultQpBankUploadMasterShouldNotBeFound("id.notEquals=" + id);

        defaultQpBankUploadMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQpBankUploadMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultQpBankUploadMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQpBankUploadMasterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQpBankUploadMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList where name equals to DEFAULT_NAME
        defaultQpBankUploadMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the qpBankUploadMasterList where name equals to UPDATED_NAME
        defaultQpBankUploadMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQpBankUploadMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList where name not equals to DEFAULT_NAME
        defaultQpBankUploadMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the qpBankUploadMasterList where name not equals to UPDATED_NAME
        defaultQpBankUploadMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQpBankUploadMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultQpBankUploadMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the qpBankUploadMasterList where name equals to UPDATED_NAME
        defaultQpBankUploadMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQpBankUploadMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList where name is not null
        defaultQpBankUploadMasterShouldBeFound("name.specified=true");

        // Get all the qpBankUploadMasterList where name is null
        defaultQpBankUploadMasterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllQpBankUploadMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList where name contains DEFAULT_NAME
        defaultQpBankUploadMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the qpBankUploadMasterList where name contains UPDATED_NAME
        defaultQpBankUploadMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllQpBankUploadMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);

        // Get all the qpBankUploadMasterList where name does not contain DEFAULT_NAME
        defaultQpBankUploadMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the qpBankUploadMasterList where name does not contain UPDATED_NAME
        defaultQpBankUploadMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllQpBankUploadMastersBySubTopicMasterIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubTopicMaster subTopicMaster = qpBankUploadMaster.getSubTopicMaster();
        qpBankUploadMasterRepository.saveAndFlush(qpBankUploadMaster);
        Long subTopicMasterId = subTopicMaster.getId();

        // Get all the qpBankUploadMasterList where subTopicMaster equals to subTopicMasterId
        defaultQpBankUploadMasterShouldBeFound("subTopicMasterId.equals=" + subTopicMasterId);

        // Get all the qpBankUploadMasterList where subTopicMaster equals to subTopicMasterId + 1
        defaultQpBankUploadMasterShouldNotBeFound("subTopicMasterId.equals=" + (subTopicMasterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQpBankUploadMasterShouldBeFound(String filter) throws Exception {
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qpBankUploadMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].qpBankFileContentType").value(hasItem(DEFAULT_QP_BANK_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].qpBankFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_QP_BANK_FILE))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQpBankUploadMasterShouldNotBeFound(String filter) throws Exception {
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQpBankUploadMaster() throws Exception {
        // Get the qpBankUploadMaster
        restQpBankUploadMasterMockMvc.perform(get("/api/qp-bank-upload-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQpBankUploadMaster() throws Exception {
        // Initialize the database
        qpBankUploadMasterService.save(qpBankUploadMaster);

        int databaseSizeBeforeUpdate = qpBankUploadMasterRepository.findAll().size();

        // Update the qpBankUploadMaster
        QpBankUploadMaster updatedQpBankUploadMaster = qpBankUploadMasterRepository.findById(qpBankUploadMaster.getId()).get();
        // Disconnect from session so that the updates on updatedQpBankUploadMaster are not directly saved in db
        em.detach(updatedQpBankUploadMaster);
        updatedQpBankUploadMaster
            .qpBankFile(UPDATED_QP_BANK_FILE)
            .qpBankFileContentType(UPDATED_QP_BANK_FILE_CONTENT_TYPE)
            .name(UPDATED_NAME);

        restQpBankUploadMasterMockMvc.perform(put("/api/qp-bank-upload-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQpBankUploadMaster)))
            .andExpect(status().isOk());

        // Validate the QpBankUploadMaster in the database
        List<QpBankUploadMaster> qpBankUploadMasterList = qpBankUploadMasterRepository.findAll();
        assertThat(qpBankUploadMasterList).hasSize(databaseSizeBeforeUpdate);
        QpBankUploadMaster testQpBankUploadMaster = qpBankUploadMasterList.get(qpBankUploadMasterList.size() - 1);
        assertThat(testQpBankUploadMaster.getQpBankFile()).isEqualTo(UPDATED_QP_BANK_FILE);
        assertThat(testQpBankUploadMaster.getQpBankFileContentType()).isEqualTo(UPDATED_QP_BANK_FILE_CONTENT_TYPE);
        assertThat(testQpBankUploadMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingQpBankUploadMaster() throws Exception {
        int databaseSizeBeforeUpdate = qpBankUploadMasterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQpBankUploadMasterMockMvc.perform(put("/api/qp-bank-upload-masters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qpBankUploadMaster)))
            .andExpect(status().isBadRequest());

        // Validate the QpBankUploadMaster in the database
        List<QpBankUploadMaster> qpBankUploadMasterList = qpBankUploadMasterRepository.findAll();
        assertThat(qpBankUploadMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQpBankUploadMaster() throws Exception {
        // Initialize the database
        qpBankUploadMasterService.save(qpBankUploadMaster);

        int databaseSizeBeforeDelete = qpBankUploadMasterRepository.findAll().size();

        // Delete the qpBankUploadMaster
        restQpBankUploadMasterMockMvc.perform(delete("/api/qp-bank-upload-masters/{id}", qpBankUploadMaster.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QpBankUploadMaster> qpBankUploadMasterList = qpBankUploadMasterRepository.findAll();
        assertThat(qpBankUploadMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
