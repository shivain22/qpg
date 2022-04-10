package com.qpg.web.rest;

import com.qpg.domain.TestEntity;
import com.qpg.service.TestEntityService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.TestEntityCriteria;
import com.qpg.service.TestEntityQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qpg.domain.TestEntity}.
 */
@RestController
@RequestMapping("/api")
public class TestEntityResource {

    private final Logger log = LoggerFactory.getLogger(TestEntityResource.class);

    private static final String ENTITY_NAME = "testEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestEntityService testEntityService;

    private final TestEntityQueryService testEntityQueryService;

    public TestEntityResource(TestEntityService testEntityService, TestEntityQueryService testEntityQueryService) {
        this.testEntityService = testEntityService;
        this.testEntityQueryService = testEntityQueryService;
    }

    /**
     * {@code POST  /test-entities} : Create a new testEntity.
     *
     * @param testEntity the testEntity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testEntity, or with status {@code 400 (Bad Request)} if the testEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/test-entities")
    public ResponseEntity<TestEntity> createTestEntity(@Valid @RequestBody TestEntity testEntity) throws URISyntaxException {
        log.debug("REST request to save TestEntity : {}", testEntity);
        if (testEntity.getId() != null) {
            throw new BadRequestAlertException("A new testEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestEntity result = testEntityService.save(testEntity);
        return ResponseEntity.created(new URI("/api/test-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /test-entities} : Updates an existing testEntity.
     *
     * @param testEntity the testEntity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testEntity,
     * or with status {@code 400 (Bad Request)} if the testEntity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testEntity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/test-entities")
    public ResponseEntity<TestEntity> updateTestEntity(@Valid @RequestBody TestEntity testEntity) throws URISyntaxException {
        log.debug("REST request to update TestEntity : {}", testEntity);
        if (testEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestEntity result = testEntityService.save(testEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testEntity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /test-entities} : get all the testEntities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testEntities in body.
     */
    @GetMapping("/test-entities")
    public ResponseEntity<List<TestEntity>> getAllTestEntities(TestEntityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TestEntities by criteria: {}", criteria);
        Page<TestEntity> page = testEntityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-entities/count} : count all the testEntities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/test-entities/count")
    public ResponseEntity<Long> countTestEntities(TestEntityCriteria criteria) {
        log.debug("REST request to count TestEntities by criteria: {}", criteria);
        return ResponseEntity.ok().body(testEntityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /test-entities/:id} : get the "id" testEntity.
     *
     * @param id the id of the testEntity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testEntity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/test-entities/{id}")
    public ResponseEntity<TestEntity> getTestEntity(@PathVariable Long id) {
        log.debug("REST request to get TestEntity : {}", id);
        Optional<TestEntity> testEntity = testEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testEntity);
    }

    /**
     * {@code DELETE  /test-entities/:id} : delete the "id" testEntity.
     *
     * @param id the id of the testEntity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/test-entities/{id}")
    public ResponseEntity<Void> deleteTestEntity(@PathVariable Long id) {
        log.debug("REST request to delete TestEntity : {}", id);
        testEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
