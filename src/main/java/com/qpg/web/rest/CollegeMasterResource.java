package com.qpg.web.rest;

import com.qpg.domain.CollegeMaster;
import com.qpg.service.CollegeMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.CollegeMasterCriteria;
import com.qpg.service.CollegeMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.CollegeMaster}.
 */
@RestController
@RequestMapping("/api")
public class CollegeMasterResource {

    private final Logger log = LoggerFactory.getLogger(CollegeMasterResource.class);

    private static final String ENTITY_NAME = "collegeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollegeMasterService collegeMasterService;

    private final CollegeMasterQueryService collegeMasterQueryService;

    public CollegeMasterResource(CollegeMasterService collegeMasterService, CollegeMasterQueryService collegeMasterQueryService) {
        this.collegeMasterService = collegeMasterService;
        this.collegeMasterQueryService = collegeMasterQueryService;
    }

    /**
     * {@code POST  /college-masters} : Create a new collegeMaster.
     *
     * @param collegeMaster the collegeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collegeMaster, or with status {@code 400 (Bad Request)} if the collegeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/college-masters")
    public ResponseEntity<CollegeMaster> createCollegeMaster(@Valid @RequestBody CollegeMaster collegeMaster) throws URISyntaxException {
        log.debug("REST request to save CollegeMaster : {}", collegeMaster);
        if (collegeMaster.getId() != null) {
            throw new BadRequestAlertException("A new collegeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollegeMaster result = collegeMasterService.save(collegeMaster);
        return ResponseEntity.created(new URI("/api/college-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /college-masters} : Updates an existing collegeMaster.
     *
     * @param collegeMaster the collegeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegeMaster,
     * or with status {@code 400 (Bad Request)} if the collegeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collegeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/college-masters")
    public ResponseEntity<CollegeMaster> updateCollegeMaster(@Valid @RequestBody CollegeMaster collegeMaster) throws URISyntaxException {
        log.debug("REST request to update CollegeMaster : {}", collegeMaster);
        if (collegeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollegeMaster result = collegeMasterService.save(collegeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collegeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /college-masters} : get all the collegeMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collegeMasters in body.
     */
    @GetMapping("/college-masters")
    public ResponseEntity<List<CollegeMaster>> getAllCollegeMasters(CollegeMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CollegeMasters by criteria: {}", criteria);
        Page<CollegeMaster> page = collegeMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /college-masters/count} : count all the collegeMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/college-masters/count")
    public ResponseEntity<Long> countCollegeMasters(CollegeMasterCriteria criteria) {
        log.debug("REST request to count CollegeMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(collegeMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /college-masters/:id} : get the "id" collegeMaster.
     *
     * @param id the id of the collegeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collegeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/college-masters/{id}")
    public ResponseEntity<CollegeMaster> getCollegeMaster(@PathVariable Long id) {
        log.debug("REST request to get CollegeMaster : {}", id);
        Optional<CollegeMaster> collegeMaster = collegeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collegeMaster);
    }

    /**
     * {@code DELETE  /college-masters/:id} : delete the "id" collegeMaster.
     *
     * @param id the id of the collegeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/college-masters/{id}")
    public ResponseEntity<Void> deleteCollegeMaster(@PathVariable Long id) {
        log.debug("REST request to delete CollegeMaster : {}", id);
        collegeMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
