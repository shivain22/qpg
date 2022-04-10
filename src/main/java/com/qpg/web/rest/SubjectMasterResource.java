package com.qpg.web.rest;

import com.qpg.domain.SubjectMaster;
import com.qpg.service.SubjectMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.SubjectMasterCriteria;
import com.qpg.service.SubjectMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.SubjectMaster}.
 */
@RestController
@RequestMapping("/api")
public class SubjectMasterResource {

    private final Logger log = LoggerFactory.getLogger(SubjectMasterResource.class);

    private static final String ENTITY_NAME = "subjectMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubjectMasterService subjectMasterService;

    private final SubjectMasterQueryService subjectMasterQueryService;

    public SubjectMasterResource(SubjectMasterService subjectMasterService, SubjectMasterQueryService subjectMasterQueryService) {
        this.subjectMasterService = subjectMasterService;
        this.subjectMasterQueryService = subjectMasterQueryService;
    }

    /**
     * {@code POST  /subject-masters} : Create a new subjectMaster.
     *
     * @param subjectMaster the subjectMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subjectMaster, or with status {@code 400 (Bad Request)} if the subjectMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subject-masters")
    public ResponseEntity<SubjectMaster> createSubjectMaster(@Valid @RequestBody SubjectMaster subjectMaster) throws URISyntaxException {
        log.debug("REST request to save SubjectMaster : {}", subjectMaster);
        if (subjectMaster.getId() != null) {
            throw new BadRequestAlertException("A new subjectMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubjectMaster result = subjectMasterService.save(subjectMaster);
        return ResponseEntity.created(new URI("/api/subject-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subject-masters} : Updates an existing subjectMaster.
     *
     * @param subjectMaster the subjectMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectMaster,
     * or with status {@code 400 (Bad Request)} if the subjectMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subjectMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subject-masters")
    public ResponseEntity<SubjectMaster> updateSubjectMaster(@Valid @RequestBody SubjectMaster subjectMaster) throws URISyntaxException {
        log.debug("REST request to update SubjectMaster : {}", subjectMaster);
        if (subjectMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubjectMaster result = subjectMasterService.save(subjectMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subjectMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subject-masters} : get all the subjectMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subjectMasters in body.
     */
    @GetMapping("/subject-masters")
    public ResponseEntity<List<SubjectMaster>> getAllSubjectMasters(SubjectMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SubjectMasters by criteria: {}", criteria);
        Page<SubjectMaster> page = subjectMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subject-masters/count} : count all the subjectMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/subject-masters/count")
    public ResponseEntity<Long> countSubjectMasters(SubjectMasterCriteria criteria) {
        log.debug("REST request to count SubjectMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(subjectMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /subject-masters/:id} : get the "id" subjectMaster.
     *
     * @param id the id of the subjectMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subject-masters/{id}")
    public ResponseEntity<SubjectMaster> getSubjectMaster(@PathVariable Long id) {
        log.debug("REST request to get SubjectMaster : {}", id);
        Optional<SubjectMaster> subjectMaster = subjectMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subjectMaster);
    }

    /**
     * {@code DELETE  /subject-masters/:id} : delete the "id" subjectMaster.
     *
     * @param id the id of the subjectMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subject-masters/{id}")
    public ResponseEntity<Void> deleteSubjectMaster(@PathVariable Long id) {
        log.debug("REST request to delete SubjectMaster : {}", id);
        subjectMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
