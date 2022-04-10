package com.qpg.web.rest;

import com.qpg.domain.DifficultyTypeMaster;
import com.qpg.service.DifficultyTypeMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.DifficultyTypeMasterCriteria;
import com.qpg.service.DifficultyTypeMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.DifficultyTypeMaster}.
 */
@RestController
@RequestMapping("/api")
public class DifficultyTypeMasterResource {

    private final Logger log = LoggerFactory.getLogger(DifficultyTypeMasterResource.class);

    private static final String ENTITY_NAME = "difficultyTypeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DifficultyTypeMasterService difficultyTypeMasterService;

    private final DifficultyTypeMasterQueryService difficultyTypeMasterQueryService;

    public DifficultyTypeMasterResource(DifficultyTypeMasterService difficultyTypeMasterService, DifficultyTypeMasterQueryService difficultyTypeMasterQueryService) {
        this.difficultyTypeMasterService = difficultyTypeMasterService;
        this.difficultyTypeMasterQueryService = difficultyTypeMasterQueryService;
    }

    /**
     * {@code POST  /difficulty-type-masters} : Create a new difficultyTypeMaster.
     *
     * @param difficultyTypeMaster the difficultyTypeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new difficultyTypeMaster, or with status {@code 400 (Bad Request)} if the difficultyTypeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/difficulty-type-masters")
    public ResponseEntity<DifficultyTypeMaster> createDifficultyTypeMaster(@Valid @RequestBody DifficultyTypeMaster difficultyTypeMaster) throws URISyntaxException {
        log.debug("REST request to save DifficultyTypeMaster : {}", difficultyTypeMaster);
        if (difficultyTypeMaster.getId() != null) {
            throw new BadRequestAlertException("A new difficultyTypeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DifficultyTypeMaster result = difficultyTypeMasterService.save(difficultyTypeMaster);
        return ResponseEntity.created(new URI("/api/difficulty-type-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /difficulty-type-masters} : Updates an existing difficultyTypeMaster.
     *
     * @param difficultyTypeMaster the difficultyTypeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated difficultyTypeMaster,
     * or with status {@code 400 (Bad Request)} if the difficultyTypeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the difficultyTypeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/difficulty-type-masters")
    public ResponseEntity<DifficultyTypeMaster> updateDifficultyTypeMaster(@Valid @RequestBody DifficultyTypeMaster difficultyTypeMaster) throws URISyntaxException {
        log.debug("REST request to update DifficultyTypeMaster : {}", difficultyTypeMaster);
        if (difficultyTypeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DifficultyTypeMaster result = difficultyTypeMasterService.save(difficultyTypeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, difficultyTypeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /difficulty-type-masters} : get all the difficultyTypeMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of difficultyTypeMasters in body.
     */
    @GetMapping("/difficulty-type-masters")
    public ResponseEntity<List<DifficultyTypeMaster>> getAllDifficultyTypeMasters(DifficultyTypeMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DifficultyTypeMasters by criteria: {}", criteria);
        Page<DifficultyTypeMaster> page = difficultyTypeMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /difficulty-type-masters/count} : count all the difficultyTypeMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/difficulty-type-masters/count")
    public ResponseEntity<Long> countDifficultyTypeMasters(DifficultyTypeMasterCriteria criteria) {
        log.debug("REST request to count DifficultyTypeMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(difficultyTypeMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /difficulty-type-masters/:id} : get the "id" difficultyTypeMaster.
     *
     * @param id the id of the difficultyTypeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the difficultyTypeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/difficulty-type-masters/{id}")
    public ResponseEntity<DifficultyTypeMaster> getDifficultyTypeMaster(@PathVariable Long id) {
        log.debug("REST request to get DifficultyTypeMaster : {}", id);
        Optional<DifficultyTypeMaster> difficultyTypeMaster = difficultyTypeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(difficultyTypeMaster);
    }

    /**
     * {@code DELETE  /difficulty-type-masters/:id} : delete the "id" difficultyTypeMaster.
     *
     * @param id the id of the difficultyTypeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/difficulty-type-masters/{id}")
    public ResponseEntity<Void> deleteDifficultyTypeMaster(@PathVariable Long id) {
        log.debug("REST request to delete DifficultyTypeMaster : {}", id);
        difficultyTypeMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
