package com.qpg.web.rest;

import com.qpg.domain.QpBankUploadMaster;
import com.qpg.service.QpBankUploadMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QpBankUploadMasterCriteria;
import com.qpg.service.QpBankUploadMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.QpBankUploadMaster}.
 */
@RestController
@RequestMapping("/api")
public class QpBankUploadMasterResource {

    private final Logger log = LoggerFactory.getLogger(QpBankUploadMasterResource.class);

    private static final String ENTITY_NAME = "qpBankUploadMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QpBankUploadMasterService qpBankUploadMasterService;

    private final QpBankUploadMasterQueryService qpBankUploadMasterQueryService;

    public QpBankUploadMasterResource(QpBankUploadMasterService qpBankUploadMasterService, QpBankUploadMasterQueryService qpBankUploadMasterQueryService) {
        this.qpBankUploadMasterService = qpBankUploadMasterService;
        this.qpBankUploadMasterQueryService = qpBankUploadMasterQueryService;
    }

    /**
     * {@code POST  /qp-bank-upload-masters} : Create a new qpBankUploadMaster.
     *
     * @param qpBankUploadMaster the qpBankUploadMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qpBankUploadMaster, or with status {@code 400 (Bad Request)} if the qpBankUploadMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qp-bank-upload-masters")
    public ResponseEntity<QpBankUploadMaster> createQpBankUploadMaster(@Valid @RequestBody QpBankUploadMaster qpBankUploadMaster) throws URISyntaxException {
        log.debug("REST request to save QpBankUploadMaster : {}", qpBankUploadMaster);
        if (qpBankUploadMaster.getId() != null) {
            throw new BadRequestAlertException("A new qpBankUploadMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QpBankUploadMaster result = qpBankUploadMasterService.save(qpBankUploadMaster);
        return ResponseEntity.created(new URI("/api/qp-bank-upload-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qp-bank-upload-masters} : Updates an existing qpBankUploadMaster.
     *
     * @param qpBankUploadMaster the qpBankUploadMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qpBankUploadMaster,
     * or with status {@code 400 (Bad Request)} if the qpBankUploadMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qpBankUploadMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qp-bank-upload-masters")
    public ResponseEntity<QpBankUploadMaster> updateQpBankUploadMaster(@Valid @RequestBody QpBankUploadMaster qpBankUploadMaster) throws URISyntaxException {
        log.debug("REST request to update QpBankUploadMaster : {}", qpBankUploadMaster);
        if (qpBankUploadMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QpBankUploadMaster result = qpBankUploadMasterService.save(qpBankUploadMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, qpBankUploadMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /qp-bank-upload-masters} : get all the qpBankUploadMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qpBankUploadMasters in body.
     */
    @GetMapping("/qp-bank-upload-masters")
    public ResponseEntity<List<QpBankUploadMaster>> getAllQpBankUploadMasters(QpBankUploadMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QpBankUploadMasters by criteria: {}", criteria);
        Page<QpBankUploadMaster> page = qpBankUploadMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qp-bank-upload-masters/count} : count all the qpBankUploadMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/qp-bank-upload-masters/count")
    public ResponseEntity<Long> countQpBankUploadMasters(QpBankUploadMasterCriteria criteria) {
        log.debug("REST request to count QpBankUploadMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(qpBankUploadMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /qp-bank-upload-masters/:id} : get the "id" qpBankUploadMaster.
     *
     * @param id the id of the qpBankUploadMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qpBankUploadMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qp-bank-upload-masters/{id}")
    public ResponseEntity<QpBankUploadMaster> getQpBankUploadMaster(@PathVariable Long id) {
        log.debug("REST request to get QpBankUploadMaster : {}", id);
        Optional<QpBankUploadMaster> qpBankUploadMaster = qpBankUploadMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qpBankUploadMaster);
    }

    /**
     * {@code DELETE  /qp-bank-upload-masters/:id} : delete the "id" qpBankUploadMaster.
     *
     * @param id the id of the qpBankUploadMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qp-bank-upload-masters/{id}")
    public ResponseEntity<Void> deleteQpBankUploadMaster(@PathVariable Long id) {
        log.debug("REST request to delete QpBankUploadMaster : {}", id);
        qpBankUploadMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
