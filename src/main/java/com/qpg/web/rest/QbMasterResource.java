package com.qpg.web.rest;

import com.qpg.domain.QbMaster;
import com.qpg.service.QbMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QbMasterCriteria;
import com.qpg.service.QbMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.QbMaster}.
 */
@RestController
@RequestMapping("/api")
public class QbMasterResource {

    private final Logger log = LoggerFactory.getLogger(QbMasterResource.class);

    private static final String ENTITY_NAME = "qbMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QbMasterService qbMasterService;

    private final QbMasterQueryService qbMasterQueryService;

    public QbMasterResource(QbMasterService qbMasterService, QbMasterQueryService qbMasterQueryService) {
        this.qbMasterService = qbMasterService;
        this.qbMasterQueryService = qbMasterQueryService;
    }

    /**
     * {@code POST  /qb-masters} : Create a new qbMaster.
     *
     * @param qbMaster the qbMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qbMaster, or with status {@code 400 (Bad Request)} if the qbMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qb-masters")
    public ResponseEntity<QbMaster> createQbMaster(@Valid @RequestBody QbMaster qbMaster) throws URISyntaxException {
        log.debug("REST request to save QbMaster : {}", qbMaster);
        if (qbMaster.getId() != null) {
            throw new BadRequestAlertException("A new qbMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QbMaster result = qbMasterService.save(qbMaster);
        return ResponseEntity.created(new URI("/api/qb-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qb-masters} : Updates an existing qbMaster.
     *
     * @param qbMaster the qbMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qbMaster,
     * or with status {@code 400 (Bad Request)} if the qbMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qbMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qb-masters")
    public ResponseEntity<QbMaster> updateQbMaster(@Valid @RequestBody QbMaster qbMaster) throws URISyntaxException {
        log.debug("REST request to update QbMaster : {}", qbMaster);
        if (qbMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QbMaster result = qbMasterService.save(qbMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, qbMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /qb-masters} : get all the qbMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qbMasters in body.
     */
    @GetMapping("/qb-masters")
    public ResponseEntity<List<QbMaster>> getAllQbMasters(QbMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QbMasters by criteria: {}", criteria);
        Page<QbMaster> page = qbMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qb-masters/count} : count all the qbMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/qb-masters/count")
    public ResponseEntity<Long> countQbMasters(QbMasterCriteria criteria) {
        log.debug("REST request to count QbMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(qbMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /qb-masters/:id} : get the "id" qbMaster.
     *
     * @param id the id of the qbMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qbMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qb-masters/{id}")
    public ResponseEntity<QbMaster> getQbMaster(@PathVariable Long id) {
        log.debug("REST request to get QbMaster : {}", id);
        Optional<QbMaster> qbMaster = qbMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qbMaster);
    }

    /**
     * {@code DELETE  /qb-masters/:id} : delete the "id" qbMaster.
     *
     * @param id the id of the qbMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qb-masters/{id}")
    public ResponseEntity<Void> deleteQbMaster(@PathVariable Long id) {
        log.debug("REST request to delete QbMaster : {}", id);
        qbMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
