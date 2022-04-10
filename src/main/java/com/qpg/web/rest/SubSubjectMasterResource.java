package com.qpg.web.rest;

import com.qpg.domain.SubSubjectMaster;
import com.qpg.service.SubSubjectMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.SubSubjectMasterCriteria;
import com.qpg.service.SubSubjectMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.SubSubjectMaster}.
 */
@RestController
@RequestMapping("/api")
public class SubSubjectMasterResource {

    private final Logger log = LoggerFactory.getLogger(SubSubjectMasterResource.class);

    private static final String ENTITY_NAME = "subSubjectMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubSubjectMasterService subSubjectMasterService;

    private final SubSubjectMasterQueryService subSubjectMasterQueryService;

    public SubSubjectMasterResource(SubSubjectMasterService subSubjectMasterService, SubSubjectMasterQueryService subSubjectMasterQueryService) {
        this.subSubjectMasterService = subSubjectMasterService;
        this.subSubjectMasterQueryService = subSubjectMasterQueryService;
    }

    /**
     * {@code POST  /sub-subject-masters} : Create a new subSubjectMaster.
     *
     * @param subSubjectMaster the subSubjectMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subSubjectMaster, or with status {@code 400 (Bad Request)} if the subSubjectMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-subject-masters")
    public ResponseEntity<SubSubjectMaster> createSubSubjectMaster(@Valid @RequestBody SubSubjectMaster subSubjectMaster) throws URISyntaxException {
        log.debug("REST request to save SubSubjectMaster : {}", subSubjectMaster);
        if (subSubjectMaster.getId() != null) {
            throw new BadRequestAlertException("A new subSubjectMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubSubjectMaster result = subSubjectMasterService.save(subSubjectMaster);
        return ResponseEntity.created(new URI("/api/sub-subject-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-subject-masters} : Updates an existing subSubjectMaster.
     *
     * @param subSubjectMaster the subSubjectMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subSubjectMaster,
     * or with status {@code 400 (Bad Request)} if the subSubjectMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subSubjectMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-subject-masters")
    public ResponseEntity<SubSubjectMaster> updateSubSubjectMaster(@Valid @RequestBody SubSubjectMaster subSubjectMaster) throws URISyntaxException {
        log.debug("REST request to update SubSubjectMaster : {}", subSubjectMaster);
        if (subSubjectMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubSubjectMaster result = subSubjectMasterService.save(subSubjectMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subSubjectMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-subject-masters} : get all the subSubjectMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subSubjectMasters in body.
     */
    @GetMapping("/sub-subject-masters")
    public ResponseEntity<List<SubSubjectMaster>> getAllSubSubjectMasters(SubSubjectMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SubSubjectMasters by criteria: {}", criteria);
        Page<SubSubjectMaster> page = subSubjectMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-subject-masters/count} : count all the subSubjectMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sub-subject-masters/count")
    public ResponseEntity<Long> countSubSubjectMasters(SubSubjectMasterCriteria criteria) {
        log.debug("REST request to count SubSubjectMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(subSubjectMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sub-subject-masters/:id} : get the "id" subSubjectMaster.
     *
     * @param id the id of the subSubjectMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subSubjectMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-subject-masters/{id}")
    public ResponseEntity<SubSubjectMaster> getSubSubjectMaster(@PathVariable Long id) {
        log.debug("REST request to get SubSubjectMaster : {}", id);
        Optional<SubSubjectMaster> subSubjectMaster = subSubjectMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subSubjectMaster);
    }

    /**
     * {@code DELETE  /sub-subject-masters/:id} : delete the "id" subSubjectMaster.
     *
     * @param id the id of the subSubjectMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-subject-masters/{id}")
    public ResponseEntity<Void> deleteSubSubjectMaster(@PathVariable Long id) {
        log.debug("REST request to delete SubSubjectMaster : {}", id);
        subSubjectMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
